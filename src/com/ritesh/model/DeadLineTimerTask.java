package com.ritesh.model;

import java.util.TimerTask;

public class DeadLineTimerTask extends TimerTask {

	private long requestId;
	
	public DeadLineTimerTask(long requestId) {
		this.requestId = requestId;
	}
	
	@Override
	public void run() {
		System.out.println(" executed successfully task id : " + getRequestId());		
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

}
