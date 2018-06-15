package com.android.androidWearController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.android.androidUtil.Constant;
import com.android.androidUtil.FCM;
import com.android.androidWearModel.MasterInfo;
import com.android.androidWearModel.SensorInfo;
import com.android.androidWearModel.TaskInfo;
import com.android.androidWearService.TaskService;

@Controller
public class TaskController {
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	private TaskService taskService;

	public TaskService getTaskService() {
		return taskService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value = "/start")
	@ResponseStatus(HttpStatus.OK)
	public String getMasterList(HttpServletRequest request) {
		try {
			List<MasterInfo> list = taskService.getMasterList();
			request.setAttribute("masterList", list);
			return "/WEB-INF/views/home";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "Home" + "\n" + e.getMessage());
			return "/WEB-INF/views/result";
		}
	}

	/**
	 * �^�X�N�ꗗ�̏����擾����
	 * 
	 * @param response
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getTaskList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String getTaskInfoAll(String blueToothMac, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<TaskInfo> list = taskService.getTaskList(blueToothMac);

		ObjectMapper objectMapper = new ObjectMapper();
		String result = objectMapper.writeValueAsString(list);
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		return result;
	}

	@RequestMapping(value = "/getTaskPage", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String getTaskPage(String blueToothMac, HttpServletRequest request) {
		try {
			List<TaskInfo> list = taskService.getTaskList(blueToothMac);
			String userName = taskService.getDeviceName(blueToothMac);
			request.setAttribute("taskLists", list);
			request.setAttribute("userName", userName);
			return "/WEB-INF/views/taskList";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "getTaskPage" + "\n" + e.getMessage());
			return "/WEB-INF/views/result";
		}

	}

	/**
	 * �^�X�N���e��ǉ�����iWeb����ǉ�����j
	 * 
	 * @param taskInfo
	 * @param response
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/addTask", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String addTaskInfo(TaskInfo taskInfo, HttpServletRequest request)
			throws JsonGenerationException, JsonMappingException, IOException {
		String taskString = new ObjectMapper().writeValueAsString(taskInfo);
		logger.debug(taskString);
		int result = taskService.addTaskInfo(taskInfo);
		if (result > 0) {
			request.setAttribute("InfoMessage", "Add Success.");
		} else {
			request.setAttribute("InfoMessage", "Add Fail.");
		}
		return "/WEB-INF/views/result";
	}

	/**
	 * �^�X�N�ǉ��y�[�W��URL
	 * 
	 * @param taskInfo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taskPage", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String addTaskInfoPage(String blueToothMac, HttpServletRequest request) {
		request.setAttribute("blueToothMac", blueToothMac);
		return "/WEB-INF/views/addTaskInfo";
	}

	@RequestMapping(value = "/updateTask")
	@ResponseStatus(HttpStatus.OK)
	public void updateTask(TaskInfo taskInfo, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		String taskString = new ObjectMapper().writeValueAsString(taskInfo);
		logger.debug(taskString);
		int result = taskService.updateTask(taskInfo);
		// return new HttpEntity<String>(String.valueOf(result));
	}

	@RequestMapping(value = "/getTaskHistory")
	@ResponseStatus(HttpStatus.OK)
	public String getHistoryAll(String blueToothMac, HttpServletRequest request) {
		try {
			List<TaskInfo> list = taskService.getTaskHistoryList(blueToothMac);
			String userName = taskService.getDeviceName(blueToothMac);
			logger.debug(new ObjectMapper().writeValueAsString(list));
			request.setAttribute("historyLists", list);
			request.setAttribute("userName", userName);
			return "/WEB-INF/views/taskHistoryList";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "getTaskHistory" + "\n" + e.getMessage());
			return "/WEB-INF/views/result";
		}
	}

	@RequestMapping(value = "/getTaskHistoryJson", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public String getHistoryJson(String blueToothMac, HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<TaskInfo> list = taskService.getTaskHistoryList(blueToothMac);
		ObjectMapper objectMapper = new ObjectMapper();
		String result = objectMapper.writeValueAsString(list);
		logger.debug(result);
		// return new HttpEntity<String>(result);
		// response.setHeader("Content-Type", "application/json;charset=utf-8");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
//		response.setContentType("application/json;charset=utf-8");
//		response.setCharacterEncoding("utf-8");
		return result;
	}

	@RequestMapping(value = "/data")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String addSensorInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			InputStream in = request.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			String str;
			String content = "";
			while ((str = bf.readLine()) != null) {
				logger.debug(str);
				if (str.contains("{")) {
					content = str;
				}
			}
			JSONObject jsonObject = new JSONObject(content);
			// String id = jsonObject.getString("tid").toString();
			String bluetoothmac = jsonObject.getString("bluetoothmac").toString();
			// String bluetoothmac = "08:00:74:6D:79:53";
			for (String name : jsonObject.getNames(jsonObject)) {
				if (!(name.equals("taskId") || name.equals("tid") || name.equals("bluetoothmac"))) {
					JSONArray jsonArray = jsonObject.getJSONArray(name);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject tempJson = jsonArray.getJSONObject(i);
						ObjectMapper objectMapper = new ObjectMapper();
						SensorInfo sensorInfo = objectMapper.readValue(tempJson.toString(), SensorInfo.class);
						sensorInfo.setType(name);
						sensorInfo.setBlueToothMac(bluetoothmac);
						taskService.addSensorInfo(sensorInfo);
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("test");
		return "";
	}

	@RequestMapping(value = "/getSensorHistory")
	@ResponseStatus(HttpStatus.OK)
	public String getSensorHistory(String blueToothMac, HttpServletRequest request) {
		try {
			List<SensorInfo> list = taskService.getSensorInfoList(blueToothMac);
			String userName = taskService.getDeviceName(blueToothMac);
			logger.debug(new ObjectMapper().writeValueAsString(list));
			request.setAttribute("historyLists", list);
			request.setAttribute("userName", userName);
			return "/WEB-INF/views/sensorHistoryList";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "getSensorHistory" + "\n" + e.getMessage());
			return "/WEB-INF/views/result";
		}
	}
	
	@RequestMapping(value = "/pushNotification", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String pushNotification(String message, String typeinfo,HttpServletRequest request, HttpServletResponse response) {
		logger.debug(message + ":" + typeinfo);
		typeinfo = typeinfo.replaceAll("\"", "");
		String title = "";

		if (typeinfo.equals(Constant.Sleep_Detection)) {
			title = "Sleep Notification";
		} else if (typeinfo.equals(Constant.Task_Change)) {
			title = "Task Notification";
		} else {
			title = "Normal Notification";
		}

		String notificationResult = FCM.send_FCM_Notification(Constant.tokenID, Constant.server_key, message, typeinfo,
				title);
		if (notificationResult.contains("messageID")) {
			logger.debug(notificationResult);
			notificationResult="Success";
		} else {
			logger.error(notificationResult);
			notificationResult = "Failure";
		}
		request.setAttribute("InfoMessage", "pushNotification" + "\n" + notificationResult);
		return "/WEB-INF/views/result";
		// return new HttpEntity<String>(notificationResult);
	}
	
	
	
	
	
}
