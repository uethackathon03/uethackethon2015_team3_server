package com.unblievable.uetsupport.ws.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.unblievable.uetsupport.common.Constant;

@RestController
@RequestMapping(value = "/upload/photo")
public class PhotoContextPath {
	@RequestMapping(value = "/avatar/{photo:.+}", method = RequestMethod.GET, produces = {"image/jpg", "image/jpeg", "image/png", "image/gif"})
	public @ResponseBody byte[] returnAvatar(HttpSession httpSession, HttpServletRequest request,
			@PathVariable("photo") String photo) {
		BufferedInputStream fileInputStream = null;
		File fPhoto = new File(Constant.AVATAR_FOLDER, photo);
		byte[] bytes = new byte[(int) fPhoto.length()];
		try {
			fileInputStream = new BufferedInputStream(new FileInputStream(fPhoto));
			fileInputStream.read(bytes);
			fileInputStream.close();
			return bytes;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Fail".getBytes();
	}
}
