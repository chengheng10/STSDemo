package com.android.androidWearModel;

public class SensorInfo {
	// センサー種別
	private String type;

	// タスクID情報
	private String id;

	// 情報のタイミング
	private String time;

	// センサー情報X
	private String x;

	// センサー情報X
	private String y;

	// センサー情報X
	private String z;

	// BlueToothMac
	private String blueToothMac;

	// シリアル番号
	private String sensor_id;

	public SensorInfo() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getZ() {
		return z;
	}

	public void setZ(String z) {
		this.z = z;
	}

	public String getBlueToothMac() {
		return blueToothMac;
	}

	public void setBlueToothMac(String blueToothMac) {
		this.blueToothMac = blueToothMac;
	}

	public String getSensor_id() {
		return sensor_id;
	}

	public void setSensor_id(String sensor_id) {
		this.sensor_id = sensor_id;
	}

}