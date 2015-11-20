package com.unblievable.uetsupport.ws.controller;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unblievable.uetsupport.common.CommonUtils;
import com.unblievable.uetsupport.common.Constant;
import com.unblievable.uetsupport.dao.StudentDao;
import com.unblievable.uetsupport.models.ClassRoom;
import com.unblievable.uetsupport.models.Course;
import com.unblievable.uetsupport.models.Student;
import com.unblievable.uetsupport.respsec.TokenInfo;
import com.unblievable.uetsupport.respsec.TokenManager;
import com.unblievable.uetsupport.ws.response.ResponseDataLogin;
import com.unblievable.uetsupport.ws.response.ResponseObjectDetail;

@RestController
@Transactional
@RequestMapping("/api/student")
public class ApiStudentController extends BaseController {
	
	@Autowired
	TokenManager tokenManager;
	
	@Autowired
	StudentDao studentDao;
	
	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public @ResponseBody ResponseObjectDetail<Object> hello() {
		return new ResponseObjectDetail<Object>(true, "Successfully", null);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody ResponseObjectDetail<Object> register(HttpSession httpSession, 
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {
		Student student = new Student();
		student.username = username;
		student.password = password;
		student.createdTime = student.modifiedTime = new Date(System.currentTimeMillis());
		Long autoId = studentDao.save(student);
		if (autoId > 0) {
			return new ResponseObjectDetail<Object>(true, getMessage(Constant.msgSuccess, httpSession), null);
		}
		return new ResponseObjectDetail<Object>(false, getMessage(Constant.msgCannotInsertDB, httpSession), null);
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseObjectDetail<Object> login(HttpSession httpSession,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "deviceId", required = true) String deviceId) {
		if (!CommonUtils.stringIsValid(username)) {
			return new ResponseObjectDetail<Object>(false, getMessage(Constant.msgUsernameCondition, httpSession), null);
		} else if (!CommonUtils.stringIsValid(password)) {
			return new ResponseObjectDetail<Object>(false, getMessage(Constant.msgPasswordCondition, httpSession), null);
		}
		Student student = studentDao.findStudentByUsername(username);
		if (student == null) {
			return new ResponseObjectDetail<Object>(false, getMessage(Constant.msgStudentNotFound, httpSession), null);
		}
		TokenInfo tokenInfo = tokenManager.createNewToken(student.studentId, deviceId);
		ResponseDataLogin dataLogin = new ResponseDataLogin(tokenInfo.getToken(), student);
		return new ResponseObjectDetail<Object>(true, getMessage(Constant.msgStudentLoginSuccess, httpSession), dataLogin);
	}
	
	@RequestMapping(value = "/update-profile", method = RequestMethod.POST)
	public @ResponseBody ResponseObjectDetail<Object> updateProfile(HttpSession httpSession,
			@RequestParam(value = "fullname", required = false, defaultValue = "") String fullname,
			@RequestParam(value = "gender", required = false, defaultValue = "") String gender,
			@RequestParam(value = "email", required = false, defaultValue = "") String email,
			@RequestParam(value = "otherEmail", required = false, defaultValue = "") String otherEmail,
			@RequestParam(value = "avatar", required = false) MultipartFile avatar,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "birthday", required = false, defaultValue = "") String birthday,
			@RequestParam(value = "description", required = false, defaultValue = "") String description,
			@RequestParam(value = "address", required = false, defaultValue = "") String address,
			@RequestParam(value = "placeOfBirth", required = false, defaultValue = "") String placeOfBirth,
			@RequestParam(value = "ethnic", required = false, defaultValue = "") String ethnic,
			@RequestParam(value = "religion", required = false, defaultValue = "") String religion,
			@RequestParam(value = "country", required = false, defaultValue = "") String country,
			@RequestParam(value = "nationality", required = false, defaultValue = "") String nationality,
			@RequestParam(value = "indentityCard", required = false, defaultValue = "") String indentityCard,
			@RequestParam(value = "daysForIdentityCards", required = false, defaultValue = "") String daysForIdentityCards,
			@RequestParam(value = "placeForIdentityCards", required = false, defaultValue = "") String placeForIdentityCards,
			@RequestParam(value = "courseId", required = false) Long courseId,
			@RequestParam(value = "classId", required = false) Long classId) {
		if (!checkToken(httpSession)) {
			return new ResponseObjectDetail<Object>(false, getMessage(Constant.msgInvalidToken, httpSession), null);
		}
		Student student = studentDao.findStudentById(token.userId);
		if (CommonUtils.stringIsValid(fullname)) student.fullname = fullname;
		if (CommonUtils.stringIsValid(gender)) student.gender = gender;
		if (CommonUtils.stringIsValid(email)) student.email = email;
		if (CommonUtils.stringIsValid(otherEmail)) student.otherEmail = otherEmail;
		if (CommonUtils.stringIsValid(phone)) student.phone = phone;
		if (CommonUtils.stringIsValid(birthday)) student.birthday = birthday;
		if (CommonUtils.stringIsValid(description)) student.description = description;
		if (CommonUtils.stringIsValid(address)) student.address = address;
		if (CommonUtils.stringIsValid(placeOfBirth)) student.placeOfBirth = placeOfBirth;
		if (CommonUtils.stringIsValid(ethnic)) student.ethnic = ethnic;
		if (CommonUtils.stringIsValid(religion)) student.religion = religion;
		if (CommonUtils.stringIsValid(country)) student.country = country;
		if (CommonUtils.stringIsValid(nationality)) student.nationality = nationality;
		if (CommonUtils.stringIsValid(indentityCard)) student.indentityCard = indentityCard;
		if (CommonUtils.stringIsValid(daysForIdentityCards)) student.daysForIdentityCards = daysForIdentityCards;
		if (CommonUtils.stringIsValid(placeForIdentityCards)) student.placeForIdentityCards = placeForIdentityCards;
		if (courseId != null) {
			Course course = (Course) getSession()
					.createCriteria(Course.class)
					.add(Restrictions.eqOrIsNull("courseId", student.courseId))
					.uniqueResult();
			if (course == null) {
				return new ResponseObjectDetail<Object>(false, getMessage(Constant.msgCourseNotFound, httpSession), null);
			}
			student.courseId = courseId;
			student.course = course;
		}
		
		if (classId != null) {
			ClassRoom classRoom = (ClassRoom) getSession()
					.createCriteria(ClassRoom.class)
					.add(Restrictions.eqOrIsNull("classId", student.classId))
					.uniqueResult();
			if (classRoom == null) {
				return new ResponseObjectDetail<Object>(false, getMessage(Constant.msgClassRoomNotFound, httpSession), null);
			}
			student.classId = classId;
			student.classRoom = classRoom;
		}
		studentDao.update(student);
		return new ResponseObjectDetail<Object>(true, getMessage(Constant.msgSuccess, httpSession), student);
	}
	
	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public @ResponseBody ResponseObjectDetail<Object> forgotPassword(HttpSession httpSession,
			@RequestParam(value = "email", required = true) String email) {
		Criteria criteria = getSession().createCriteria(Student.class);
		Student student = (Student) criteria.add(Restrictions
				.or(Restrictions.eq("email", email), Restrictions.eq("otherEmail", email)))
				.uniqueResult();
		if (student == null) {
			return new ResponseObjectDetail<Object>(false, getMessage(Constant.msgStudentNotFound, httpSession), null);
		}
		student.password = RandomStringUtils.randomAlphanumeric(6);
		studentDao.update(student);
		try {
			sendMail("UETSupport@gmail.com", email, "[UETSupport] Forgot password", "Username: " + student.username + "\nReset Password: " + student.password);
		} catch (Exception e) {
			return new ResponseObjectDetail<Object>(false, e.getMessage(), null);
		}
		return new ResponseObjectDetail<Object>(true, getMessage(Constant.msgForgotPassword, httpSession), null);
	}
	
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public @ResponseBody ResponseObjectDetail<Object> profile(HttpSession httpSession) {
		if (!checkToken(httpSession)) {
			return new ResponseObjectDetail<Object>(false, getMessage(Constant.msgInvalidToken, httpSession), null);
		}
		Student student = studentDao.findStudentById(token.userId);
		Course course = (Course) getSession()
				.createCriteria(Course.class)
				.add(Restrictions.eqOrIsNull("courseId", student.courseId))
				.uniqueResult();
		if (course != null) {
			student.course = course;
		}
		ClassRoom classRoom = (ClassRoom) getSession()
				.createCriteria(ClassRoom.class)
				.add(Restrictions.eqOrIsNull("classId", student.classId))
				.uniqueResult();
		if (classRoom != null) {
			student.classRoom = classRoom;
		}
		return new ResponseObjectDetail<Object>(true, getMessage(Constant.msgSuccess, httpSession), student);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody ResponseObjectDetail<Object> logout(HttpSession httpSession) {
		if (!checkToken(httpSession)) {
			return new ResponseObjectDetail<Object>(false, getMessage(Constant.msgInvalidToken, httpSession), null);
		}
		getSession().delete(token);
		return new ResponseObjectDetail<Object>(true, getMessage(Constant.msgSuccess, httpSession), null);
	}
	
	public void sendMail(String from, String to, String subject, String msg) throws Exception {
		final String username = "ducanh.uet@gmail.com";
		final String password = "euelfpiljjlcyziz";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		 
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
	
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(to));
		message.setSubject(subject);
		message.setText(msg);
		Transport.send(message);
	}
	
}
