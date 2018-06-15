package com.android.STSDemoDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.android.STSDemoModel.MasterInfo;
import com.android.STSDemoModel.SensorInfo;
import com.android.STSDemoModel.TaskInfo;


public interface TaskMapper {
    int deleteByPrimaryKey(String id);

    int addTaskInfo(@Param("taskinfo") TaskInfo record);

    TaskInfo selectByPrimaryKey(@Param("taskinfo") TaskInfo record);

    int updateTaskInfo(@Param("taskinfo") TaskInfo record);
    
    List<TaskInfo> getTaskList(@Param("taskinfo") TaskInfo record);
    
    int addTaskHistoryInfo(@Param("taskinfo") TaskInfo record);
    
    List<TaskInfo> getTaskHistoryList(@Param("taskinfo") TaskInfo record);
    
    String getDeviceName(String blueToothMac);
    
    List<SensorInfo> getSensorList(@Param("blueToothMac")String blueToothMac,@Param("time")String time);
    
    int addSensorInfo(@Param("sensorInfo") SensorInfo record);
    
    List<MasterInfo> getMasterList();
}