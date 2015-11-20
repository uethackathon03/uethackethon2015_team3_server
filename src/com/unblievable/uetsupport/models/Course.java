package com.unblievable.uetsupport.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "course")
public class Course {	// Khóa học
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long courseId;
	public String name;
	public String description;
	
	@Transient
	public List<Student> students;
	
	@Transient
	public List<ClassRoom> classRooms;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss 'GMT'Z")
	public Date createdTime;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss 'GMT'Z")
	public Date modifiedTime;
}
