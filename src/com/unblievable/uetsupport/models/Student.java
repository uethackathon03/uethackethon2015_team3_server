package com.unblievable.uetsupport.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "student")
public class Student  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long studentId;
	@Column(unique = true)
	public String username;
	public String password;
	public String fullname;
	public String gender;
	public String email;
	public String otherEmail;
	public String avatar;
	public String phone;
	@Column(columnDefinition = "DATE")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss 'GMT'Z")
	public String birthday;
	@Column(columnDefinition = "text")
	public String description;
	
	public String address;
	public String placeOfBirth;
	public String role;
	public String ethnic;			// Dan toc
	public String religion;			// Ton giao
	public String country;			// Quoc gia
	public String nationality;		// 	Quoc tich
	public String indentityCard;	//	So CMT
	@Column(columnDefinition = "DATE")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss 'GMT'Z")
	public String daysForIdentityCards; // Ngay cap CMT
	public String placeForIdentityCards; // Noi cap CMT
	
	@JsonIgnore
	public Long courseId;
	@JsonIgnore
	public Long classId;
	
	@Transient
	public Course course;
	
	@Transient
	public ClassRoom classRoom;
	
	@Column(name = "createdTime")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss 'GMT'Z")
	public Date createdTime;
	@Column(name = "modifiedTime")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss 'GMT'Z")
	public Date modifiedTime;
	
}
