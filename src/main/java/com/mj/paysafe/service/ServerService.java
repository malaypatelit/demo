package com.mj.paysafe.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mj.paysafe.model.Server;

@Service
public class ServerService {

	ServerMonitoring sm = new ServerMonitoring();
	Server server = new Server();
	Thread t1 = new Thread(sm);
	Map<Integer, Server> serverMonitoring = new HashMap<>();
	public Integer key = 1;

	public ServerService() {
	}

	public void startMonitoring(long interval) {
		sm.setSleepInterval(interval);
		sm.setExecuteThread(true);
		t1.start();

	}

	public void stopMonitoring() {
		sm.setExecuteThread(false);
		t1.stop();
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

	protected class ServerMonitoring implements Runnable {
		long sleepInterval = 0;
		boolean executeThread = false;
		private String serverUrl = "PaySafe API Server";

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
		public void run() {

			while (executeThread) {
				try {
					// Request to get status.
					this.getServerStatus();

					Thread.sleep(sleepInterval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		protected void getServerStatus() {
			final String uri = "https://api.test.paysafe.com/accountmanagement/monitor";

			RestTemplate restTemplate = new RestTemplate();
			Server result = restTemplate.getForObject(uri, Server.class);

			String serverStatus = result.getStatus();

			System.out.println("Server Status: " + serverStatus);
			if (serverStatus.equals("READY")) {
				if (key == 1) {
					Server request = new Server(serverUrl, serverStatus, new Date(), null, sleepInterval);
					serverMonitoring.put(key, request);
					key++;
				} else if (key > 1) {
					Server previousRequest = serverMonitoring.get(key - 1);

					// Server Status other than 'READY'
					if (previousRequest.getStopTime() != null) {
						Server request = new Server(serverUrl, serverStatus, new Date(), null, sleepInterval);
						serverMonitoring.put(key, request);
						key++;

					}
				}
			} else {
				Server previousRequest = serverMonitoring.get(key - 1);
				previousRequest.setStopTime(new Date());
			}
		}
	}

	public Map<Integer, Server> getMonitoringResults() {
		return serverMonitoring;
	}

}
