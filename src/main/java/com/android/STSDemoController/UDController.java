package com.android.STSDemoController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
public class UDController {

	@RequestMapping(value = "/upLoadFile", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String addTaskInfoPage(String blueToothMac, HttpServletRequest request) {
		return "/WEB-INF/views/uploadFile";
	}

	@RequestMapping(value = "/upLoad", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		// JsonResult result = new JsonResult();
		ServletContext sc = request.getSession().getServletContext();
		System.out.println(sc.getRealPath("/WEB-INF/files/"));
		try {
			// request.setCharacterEncoding("utf-8");
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(sc);
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String myFileName = file.getOriginalFilename();
						if (myFileName.trim() != "") {
							String fileName = file.getOriginalFilename();
							String dirPath = sc.getRealPath("/WEB-INF/files/");
							// String dirPath = request.getServletContext().getRealPath("/WEB-INF/files/");
							File dir = new File(dirPath);
							if (!dir.exists()) {
								dir.mkdirs();
							}
							File localFile = new File(dir, fileName);
							file.transferTo(localFile);
							// result.setStatus(0);
							// result.setData(localFile.getName());
							// result.setMsg("閿熻緝杈炬嫹閿熺即鐧告嫹");
							result.put("status", 0);
							result.put("data", localFile.getName());
							result.put("msg", "Upload successed!");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		// result.setStatus(1);
		result.put("status", 1);
		return result.toString();
	}

	@RequestMapping(value = "/downLoad")
	@ResponseBody
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName ="adbdriver.zip";
		System.out.println(fileName);
		String filePath = "/WEB-INF/files/" + fileName;
		ServletContext sc = request.getSession().getServletContext();
		String fileFullPath = sc.getRealPath(filePath);
		File file = new File(fileFullPath);
		if (file.exists()) {
			response.reset();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			// response.addHeader("Content-Disposition",
			// "attachment;filename=\""+fileName+"\"");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			int fileLength = (int) file.length();
			response.setContentLength(fileLength);

			if (fileLength != 0) {
				InputStream inStream = new FileInputStream(file);
				byte[] buf = new byte[4096];

				ServletOutputStream servletOS = response.getOutputStream();
				int readLength;

				while ((readLength = inStream.read(buf)) != -1) {
					servletOS.write(buf, 0, readLength);
				}
				inStream.close();

				servletOS.flush();

				servletOS.close();
			}
		} else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("閿熶茎纭锋嫹\"" + fileName + "\"閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�");
		}
	}

}
