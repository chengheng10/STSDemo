package com.android.androidWearService;

import java.util.List;

import com.android.androidWearModel.MasterInfo;
import com.android.androidWearModel.SensorInfo;
import com.android.androidWearModel.TaskInfo;


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
