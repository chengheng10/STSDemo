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
			// 閿熸枻鎷烽敓鏂ゆ嫹涓�閿熸枻鎷烽�氶敓鐭殑澶氶儴閿熻鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(sc);
			// 閿熷彨璁规嫹 request 閿熻鍑ゆ嫹閿熸枻鎷烽敓渚ョ》鎷烽敓杈冭揪鎷�?,閿熸枻鎷烽敓娲侀儴閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�?
			if (multipartResolver.isMultipart(request)) {
				// 杞敓鏂ゆ嫹閿熺即澶氶儴閿熸枻鎷穜equest
				MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
				// 鍙栭敓鏂ゆ嫹request閿熷彨纰夋嫹閿熸枻鎷烽敓鏂ゆ嫹閿熶茎纭锋嫹閿熸枻鎷�
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 鍙栭敓鏂ゆ嫹閿熻緝杈炬嫹閿熶茎纭锋嫹
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						// 鍙栭敓鐭鎷峰墠閿熻緝杈炬嫹閿熶茎纭锋嫹閿熸枻鎷烽敓渚ョ�?�鎷烽敓鏂ゆ嫹閿熸枻鎷�
						String myFileName = file.getOriginalFilename();
						// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋拠閿熻娇顏庢嫹閿熸枻鎷烽敓锟�?,璇撮敓鏂ゆ嫹閿熸枻鎷烽敓渚ョ》鎷烽敓鏂ゆ嫹閿熻妭锝忔嫹閿熸枻鎷烽敓鏂ゆ嫹璇撮敓鏂ゆ嫹閿熸枻鎷烽敓渚ョ》鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
						if (myFileName.trim() != "") {
							// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓杈冭揪鎷烽敓鏂ゆ嫹閿熸枻鎷峰嫙閿熸枻鎷烽敓锟�
							String fileName = file.getOriginalFilename();
							// 閿熸枻鎷烽敓鏂ゆ嫹閿熻緝杈炬嫹璺敓鏂ゆ�?
							String dirPath = sc.getRealPath("/WEB-INF/files/");
							// String dirPath = request.getServletContext().getRealPath("/WEB-INF/files/");
							File dir = new File(dirPath);
							if (!dir.exists()) {
								dir.mkdirs();
							}
							File localFile = new File(dir, fileName);
							file.transferTo(localFile);
							// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰嫙閿熼摪鍑ゆ嫹閿燂拷
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
			// 閿熸枻鎷烽敓鏂ゆ嫹response
			response.reset();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			// 閿熸枻鎷烽敓鏂ゆ嫹http澶撮敓鏂ゆ嫹鎭敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ�?
			// response.addHeader("Content-Disposition",
			// "attachment;filename=\""+fileName+"\"");
			// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹鍕熼敓鏂ゆ嫹閿熸枻鎷烽敓缁炴拝鎷烽敓鏂ゆ嫹閿燂拷
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			// 閿熸枻鎷烽敓鏂ゆ嫹閿熶茎纭锋嫹閿熸枻鎷烽敓鏂ゆ嫹
			int fileLength = (int) file.length();
			response.setContentLength(fileLength);

			if (fileLength != 0) {
				InputStream inStream = new FileInputStream(file);
				byte[] buf = new byte[4096];

				// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿燂�?
				ServletOutputStream servletOS = response.getOutputStream();
				int readLength;

				// 閿熸枻鎷峰彇閿熶茎纭锋嫹閿熸枻鎷烽敓鎹疯鎷峰啓閿熻鍒皉esponse閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
				while ((readLength = inStream.read(buf)) != -1) {
					servletOS.write(buf, 0, readLength);
				}
				// 閿熸埅鎲嬫嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
				inStream.close();

				// 鍒烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿燂�?
				servletOS.flush();

				// 閿熸埅鎲嬫嫹閿熸枻鎷烽敓鏂ゆ嫹閿燂拷
				servletOS.close();
			}
		} else {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("閿熶茎纭锋嫹\"" + fileName + "\"閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�");
		}
	}

}
