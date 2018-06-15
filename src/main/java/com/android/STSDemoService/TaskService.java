package com.android.STSDemoService;

import java.util.List;

import com.android.STSDemoModel.MasterInfo;
import com.android.STSDemoModel.SensorInfo;
import com.android.STSDemoModel.TaskInfo;


public interface TaskService {
	
	int addTaskInfo(TaskInfo taskInfo);
	
	List<TaskInfo> getTaskList(String blueToothMac);
	
	String delete(String id);
	
	TaskInfo findById(String id);
	
	int updateTask(TaskInfo taskInfo);
	
	List<TaskInfo> getTaskHistoryList(String blueToothMac);
	
	String getDeviceName(String blueToothMac);
	
	String addSensorInfo(SensorInfo sensorInfo);
	
	List<SensorInfo> getSensorInfoList(String blueToothMac);
	
	List<MasterInfo> getMasterList();
}
