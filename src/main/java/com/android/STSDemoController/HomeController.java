package com.android.STSDemoController;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.android.STSDemoModel.Add;
import com.android.STSDemoModel.HttpResult;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "/WEB-INF/views/home";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@RequestMapping(value = "/testJson", method = RequestMethod.GET)
	public void testJson(HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {

		Map<String, Add> map = new HashMap<String, Add>();
		Add add1 = new Add();
		add1.setId("001");
		add1.setTname("Add1 Name");
		add1.setTpwd("Add1 password");

		Add add2 = new Add();
		add2.setId("002");
		add2.setTname("Add2 Name");
		add2.setTpwd("Add2 password");

		map.put("001", add1);
		map.put("002", add2);

		ObjectMapper objectMapper = new ObjectMapper();
		String result = objectMapper.writeValueAsString(map);
		logger.debug(result);
		response.getWriter().print(result);
	}

	@RequestMapping(value = "/testHttp", method = RequestMethod.GET)
	public HttpEntity<String> testHttpEntity(HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		HttpResult httpResult = new HttpResult();
		Map<String, Add> map = new HashMap<String, Add>();
		Add add1 = new Add();
		add1.setId("001");
		add1.setTname("Add1 Name");
		add1.setTpwd("Add1 password");

		Add add2 = new Add();
		add2.setId("002");
		add2.setTname("Add2 Name");
		add2.setTpwd("Add2 password");

		map.put("001", add1);
		map.put("002", add2);

		httpResult.setStatus("200");
		httpResult.setResult((Map) map);
		ObjectMapper objectMapper = new ObjectMapper();
		String result = objectMapper.writeValueAsString(httpResult);
		logger.debug(result);
		return new HttpEntity<String>(result);
	}



}
