package com.mj.paysafe.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mj.paysafe.model.Server;
import com.mj.paysafe.service.ServerService;

@RestController
public class ServerController {

	@Autowired
	private ServerService serverService;


	// Start or Stop Monitoring Server
	@RequestMapping(path="/server/status", method=RequestMethod.POST)
	public void startOrStopMonitoringServer(@RequestParam long interval, @RequestParam String url)
	{

		if(url.equals("start"))
		{
			serverService.startMonitoring(interval);
		}
		else if(url.equals("stop"))
		{
			serverService.stopMonitoring();
		}
	}

	// Get Server Monitoring Result
	@RequestMapping(path="/server/result", method=RequestMethod.GET )
	public Map<Integer, Server> getMonitoringResult()
	{
		Map<Integer, Server> results = new HashMap<>();
		results = serverService.getMonitoringResults();
		return results;
	}
}
