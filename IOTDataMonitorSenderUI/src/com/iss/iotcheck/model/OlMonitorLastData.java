package com.iss.iotcheck.model;


public class OlMonitorLastData extends  OlMonitorMinData {
     
	private int overTime;
	private boolean statue;
	private boolean deviceState;
	private String pointLatitude;
	private String pointLongitude;
	private String pointClassificat;
	
	public int getOverTime() {
		return overTime;
	}
	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}
	public boolean isStatue() {
		return statue;
	}
	public void setStatue(boolean statue) {
		this.statue = statue;
	}
	public String getPointLatitude() {
		return pointLatitude;
	}
	public void setPointLatitude(String pointLatitude) {
		this.pointLatitude = pointLatitude;
	}
	public String getPointLongitude() {
		return pointLongitude;
	}
	public void setPointLongitude(String pointLongitude) {
		this.pointLongitude = pointLongitude;
	}
	public String getPointClassificat() {
		return pointClassificat;
	}
	public void setPointClassificat(String pointClassificat) {
		this.pointClassificat = pointClassificat;
	}
	public boolean isDeviceState() {
		return deviceState;
	}
	public void setDeviceState(boolean deviceState) {
		this.deviceState = deviceState;
	}
	 
	 
	
	
}