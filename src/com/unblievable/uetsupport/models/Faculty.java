package com.unblievable.uetsupport.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "faculty")
public class Faculty {	// Khoa
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long facultyId;
	@Column(length = 40)
	public String name;
	@Column(columnDefinition = "text")
	public String description;
	public String foundedYear;
	
	@Transient
	public List<Teacher> teacher;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss 'GMT'Z")
	public Date createdTime;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss 'GMT'Z")
	public Date modifiedTime;
}
