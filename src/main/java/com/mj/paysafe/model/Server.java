package com.mj.paysafe.model;

import java.util.Date;

public class Server {

	String serverUrl;
	String status;
	Date startTime;
	Date stopTime;
	long intervalField;

	public Server() {
		super();
	}

	public Server(String serverUrl, String status, Date startTime, Date stopTime, long intervalField) {
		super();
		this.serverUrl = serverUrl;
		this.status = status;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.intervalField = intervalField;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public long getIntervalField() {
		return intervalField;
	}

	public void setIntervalField(long intervalField) {
		this.intervalField = intervalField;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

}
