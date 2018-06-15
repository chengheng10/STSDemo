package com.android.androidWearServiceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.android.androidUtil.Constant;
import com.android.androidUtil.FCM;
import com.android.androidWearDao.TaskMapper;
import com.android.androidWearModel.MasterInfo;
import com.android.androidWearModel.SensorInfo;
import com.android.androidWearModel.TaskInfo;
import com.android.androidWearService.TaskService;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
	private TaskMapper taskMapper;
	private Date date = new Date();
	private SimpleDateFormat sf;

	public TaskMapper getTaskMapper() {
		return taskMapper;
	}

	@Autowired
	public void setTaskMapper(TaskMapper taskMapper) {
		this.taskMapper = taskMapper;
	}

	@Override
	public int addTaskInfo(TaskInfo taskInfo) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			String message = new ObjectMapper().writeValueAsString(taskInfo);
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			taskInfo.setDate(sf.format(date));
			result = taskMapper.addTaskInfo(taskInfo);
			FCM.send_FCM_Notification(Constant.tokenID, Constant.server_key, message, Constant.Task_Change,
					"Task Added");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public List<TaskInfo> getTaskList(String blueToothMac) {
		// TODO Auto-generated method stub
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setBlueToothMac(blueToothMac);
		sf = new SimpleDateFormat("yyyyMMdd");
		taskInfo.setDate(sf.format(date));
		logger.debug(sf.format(date));
		return taskMapper.getTaskList(taskInfo);
	}

	@Override
	public String delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskInfo findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateTask(TaskInfo taskInfo) {
		// TODO Auto-generated method stub
		int result = 0;
		TaskInfo taskDetail = new TaskInfo();
		try {
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String dateString = sf.format(date);
//			taskInfo.setDate(dateString);
			BeanUtils.copyProperties(taskInfo, taskDetail);
			taskDetail.setState(Constant.Task_Completed);
			if (taskInfo.getId().toString().equals("0")) {
				taskDetail.setDate(dateString);
				taskDetail.setDetail("鍑哄嫟");
				taskDetail.setTid("鍑哄嫟");
			} else if (taskInfo.getId().toString().equals("99")) {
				taskDetail.setDate(dateString);
				taskDetail.setDetail("閫�鍕�");
				taskDetail.setTid("閫�鍕�");
			} else {
				sf = new SimpleDateFormat("yyyyMMdd");
				taskInfo.setDate(sf.format(date));
				result = taskMapper.updateTaskInfo(taskInfo);
				taskDetail = taskMapper.selectByPrimaryKey(taskInfo);
				taskDetail.setDate(dateString);
			}
			taskMapper.addTaskHistoryInfo(taskDetail);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

	@Override
	public List<TaskInfo> getTaskHistoryList(String blueToothMac) {
		// TODO Auto-generated method stub
		List<TaskInfo> resultList = null;
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setBlueToothMac(blueToothMac);
		sf = new SimpleDateFormat("yyyyMMdd000000000");
		taskInfo.setDate(sf.format(date));
		resultList = taskMapper.getTaskHistoryList(taskInfo);
		sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat sf_new = new SimpleDateFormat("yyyy骞碝M鏈坉d鏃� HH鏅俶m鍒唖s绉�");
		for (TaskInfo tempInfo : resultList) {
			String stateString = "";
			if (tempInfo.getState().equals(Constant.Task_Completed)) {
				stateString = "瀹屼簡";
			} else if (tempInfo.getState().equals(Constant.Task_start)) {
				stateString = "闁嬪";
			} else if (tempInfo.getState().equals(Constant.Task_Restart)) {
				stateString = "鍐嶉枊";
			} else if (tempInfo.getState().equals(Constant.Task_ReCompleted)) {
				stateString = "鍐嶉枊瀹屼簡";
			} else {
				stateString = "涓嶆槑";
			}
			try {
				Date date = sf.parse(tempInfo.getDate());
				tempInfo.setDate(sf_new.format(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempInfo.setState(stateString);
		}

		return resultList;
	}

	@Override
	public String getDeviceName(String blueToothMac) {
		// TODO Auto-generated method stub
		return taskMapper.getDeviceName(blueToothMac);
	}

	/**
	 * 銈汇兂銈点兗鎯呭牨銈掔櫥閷层仚銈�
	 */
	@Override
	public String addSensorInfo(SensorInfo sensorInfo) {
		// TODO Auto-generated method stub
		int result = 0;
		result = taskMapper.addSensorInfo(sensorInfo);

		return String.valueOf(result);
	}

	/**
	 * 銈汇兂銈点兗鎯呭牨銉偣銉堛倰鍙栧緱銇欍倠
	 */
	@Override
	public List<SensorInfo> getSensorInfoList(String blueToothMac) {
		// TODO Auto-generated method stub
		sf = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000");
		String time = sf.format(date);
		List<SensorInfo> result = taskMapper.getSensorList(blueToothMac, time);

		return result;
	}

	@Override
	public List<MasterInfo> getMasterList() {
		// TODO Auto-generated method stub
		return taskMapper.getMasterList();
	}

}
