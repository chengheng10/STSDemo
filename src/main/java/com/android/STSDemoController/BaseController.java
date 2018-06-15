package com.android.STSDemoController;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.android.STSDemoModel.Add;
import com.android.STSDemoService.BaseService;


@Controller
public class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	private BaseService baseService;
	
	public BaseService getBaseService() {
		return baseService;
	}
	@Autowired
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("addInfo")
	public String add(Add add,HttpServletRequest request){
		try {			
			add.setId(UUID.randomUUID().toString());
			logger.debug(add.getId() + ":::::" + add.getTname() + ":::::" + add.getTpwd());
			String str = baseService.addInfo(add);
			logger.debug(str);
			
			request.setAttribute("InfoMessage", str);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "addInfo" + e.getMessage());
		} finally {			
			return "/WEB-INF/views/result";
		}
	}
	
	@RequestMapping("getAll")
	public String getAddInfoAll(HttpServletRequest request){
		try {			
			List<Add> list = baseService.getAll();
			logger.debug(new ObjectMapper().writeValueAsString(list));
			request.setAttribute("addLists", list);
			return "/WEB-INF/views/listAll";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "getAll" + e.getMessage());
			return "/WEB-INF/views/result";
		}
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("del")
	public String del(String tid,HttpServletRequest request){
		try {			
			String str = baseService.delete(tid);
			request.setAttribute("InfoMessage", str);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "del" + e.getMessage());
		} finally {			
			return "/WEB-INF/views/result";
		}
	}
	@RequestMapping("modify")
	public String modify(String tid,HttpServletRequest request){
		try {			
			Add add = baseService.findById(tid);
			request.setAttribute("add", add);
			return "/WEB-INF/views/modify";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "modify" + e.getMessage());
			return "/WEB-INF/views/result";
		}
	}
	@SuppressWarnings("finally")
	@RequestMapping("update")
	public String update(Add add,HttpServletRequest request){
		try {			
			String str = baseService.update(add);
			request.setAttribute("InfoMessage", str);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "update" + e.getMessage());
		} finally {			
			return "/WEB-INF/views/result";
		}
	}

}
