package com.mj.paysafe.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mj.paysafe.model.Server;

/**
 * @author Malay Patel
 *
 */
@Service
public class ServerService {

    private static Logger logger = LoggerFactory.getLogger(ServerService.class);
	
	ServerMonitoring sm = new ServerMonitoring();
	Server server = new Server();
	Thread t1 = null;
	Map<Integer, Server> serverMonitoring = new HashMap<>();
	public Integer key = 1;

	public ServerService() {
	}

	/**
	 * Start Server monitoring
	 * 
	 * @param url
	 * @param interval
	 */
	public void startMonitoring(String url, long interval) 
	{
		if(sm.getServerUrl() != null && sm.getServerUrl().equals(url))
		{
			stopMonitoring();
		}
		else
		{
			sm.setServerUrl(url);
			sm.setSleepInterval(interval);
			sm.setExecuteThread(true);
			t1 = new Thread(sm);
			t1.start();
		}
	}

	/**
	 * Stop Server Monitoring
	 *  
	 */
	public void stopMonitoring() {
		sm.setExecuteThread(false);
		t1.stop();
		sm.setServerUrl(null);
		sm.setSleepInterval(0);
	}

	/**
	 * Validate Parameters 
	 * 
	 * @param url
	 */
	public void validateParams(String url) 
	{
		if(url == null || url.isEmpty())
		{
			logger.error("url is required");
			throw new URLNotFoundException("url is required");
		}

		if(!isValidURL(url))
		{
			logger.error("Invalid url value");
			throw new URLNotFoundException("Invalid url value");
		}
		
		if(!isExists(url))
		{
			logger.error("url does not exist");
			throw new URLNotFoundException("url does not exist");
		}
	}

	/**
	 * Check whether URL exists or not.
	 * 
	 * @param url
	 * @return
	 */
	private boolean isExists(String url) {
		return true;
	}

	/**
	 * Verify if URL is valid or not
	 * 
	 * @param url
	 * @return
	 */
	private boolean isValidURL(String url) {
		// TODO Auto-generated method stub
		String regex = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
		
		return url.matches(regex);
	}

	public String getServerStatus() {
		return server.getStatus();
	}

	public void setServerStartTime() {
		server.setStartTime(new Date());
	}

	public Date getServerStartTime() {
		return server.getStartTime();
	}

	public void setServerStopTime() {
		server.setStopTime(new Date());
	}

	public Date getServerStopTime() {
		return server.getStopTime();
	}

	/**
	 * @author Malay Patel
	 *
	 */
	protected class ServerMonitoring implements Runnable {
		public static final String READY = "READY";
		long sleepInterval = 0;
		boolean executeThread = false;
		private String serverUrl = null;

		public String getServerUrl() {
			return serverUrl;
		}

		public void setServerUrl(String serverUrl) {
			this.serverUrl = serverUrl;
		}

		public boolean isExecuteThread() {
			return executeThread;
		}

		public void setExecuteThread(boolean executeThread) {
			this.executeThread = executeThread;
		}

		public long getSleepInterval() {
			return sleepInterval;
		}

		public void setSleepInterval(long sleepInterval) {
			this.sleepInterval = sleepInterval;
		}

		@Override
		public void run() 
		{

			while (executeThread) 
			{
				try {
					// Request to get status.
					String serverStatus = this.getServerStatus();
					this.collectServerStatus(serverStatus);
					
					Thread.sleep(sleepInterval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		protected String getServerStatus() 
		{
			// final String uri = "https://api.test.paysafe.com/accountmanagement/monitor";

			RestTemplate restTemplate = new RestTemplate();
			Server result = restTemplate.getForObject(serverUrl, Server.class);

			String serverStatus = result.getStatus();

			System.out.println("Server Status: " + serverStatus);
			return serverStatus;
		}
		
		protected void collectServerStatus(String serverStatus)
		{
			if (serverStatus.equals(READY)) 
			{
				if (key == 1)
				{
					Server request = new Server(serverUrl, serverStatus, new Date(), null, sleepInterval);
					serverMonitoring.put(key, request);
					key++;
				} 
				else if (key > 1) 
				{
					Server previousRequest = serverMonitoring.get(key - 1);

					// Server Status other than 'READY'
					if (!previousRequest.getStatus().equals(READY) 
							&& previousRequest.getStopTime() != null) 
					{
						Server request = new Server(serverUrl, serverStatus, new Date(), null, sleepInterval);
						serverMonitoring.put(key, request);
						key++;
					}
				}
			} 
			else 
			{
				if(key == 1)
				{
					Server request = new Server(serverUrl, serverStatus, null, new Date(), sleepInterval);
					serverMonitoring.put(key, request);
					key++;
				}
				else if(key > 1)
				{
					Server previousRequest = serverMonitoring.get(key - 1);
					previousRequest.setStopTime(new Date());
				}
			}
		}
	}

	public Map<Integer, Server> getMonitoringResults() {
		return serverMonitoring;
	}

}
