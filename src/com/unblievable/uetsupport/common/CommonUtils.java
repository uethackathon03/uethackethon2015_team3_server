package com.unblievable.uetsupport.common;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class CommonUtils {
	
	public static boolean stringIsValid(String s) {
		if (s != null && s.length() > 0) {
			return true;
		}
		return false;
	}

	public static boolean checkImage(String image) {
		Pattern pattern;
		Matcher matcher;
		String image_pattern = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

		pattern = Pattern.compile(image_pattern);
		matcher = pattern.matcher(image);

		return matcher.matches();
	}
	
	public static String convertStringToMD5(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getBaseUrl(HttpServletRequest request) {
		String baseUrl = String.format("%s://%s:%d%s", request.getScheme(),
				request.getServerName(), request.getServerPort(),
				request.getContextPath());
		return baseUrl;
	}
}
