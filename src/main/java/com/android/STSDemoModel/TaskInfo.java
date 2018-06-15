package com.android.STSDemoModel;

public class TaskInfo {
	// タスク時間
	private String date;

	// タスク詳細内容
	private String detail;

	// タスクID
	private Integer id;

	// タスクのステータス
	private String state;

	// タスクのID
	private String tid;

	private String blueToothMac;
	
	public String getBlueToothMac() {
		return blueToothMac;
	}

	public void setBlueToothMac(String blueToothMac) {
		this.blueToothMac = blueToothMac;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

}