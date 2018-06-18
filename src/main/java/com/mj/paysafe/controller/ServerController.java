package com.mj.paysafe.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mj.paysafe.model.Server;
import com.mj.paysafe.service.ServerService;
import com.mj.paysafe.service.URLNotFoundException;

/**
 * @author Malay Patel
 *
 */
@RestController
public class ServerController {

	@Autowired
	private ServerService serverService;
	
    private static Logger logger = LoggerFactory.getLogger(ServerController.class);


	/**
	 * Start or Stop Monitoring Server
	 * 
	 * @param url server url for which the monitoring needs to be done 
	 * @param interval set interval for the request to the url
	 */
	@RequestMapping(path="/server/status", method=RequestMethod.POST)
	@ResponseBody
	public String startOrStopMonitoringServer(@Valid @RequestParam String url,
											@Valid @RequestParam long interval)
	{
		serverService.validateParams(url);
		
		logger.info("Start/Stop Monitoring the server on given URL");
		return serverService.startMonitoring(url, interval);
	}

	/**
	 * Get Server Monitoring Result
	 * 
	 * @return Server Up and Down Time Results as JSON objects array
	 */
	@RequestMapping(path="/server/result", method=RequestMethod.GET )
	@ResponseBody
	public Map<Integer, Server> getMonitoringResult()
	{
		Map<Integer, Server> results = new HashMap<>();
		
		logger.info("Get Monitoring Results");
		
		results = serverService.getMonitoringResults();
		return results;
	}
	
}
