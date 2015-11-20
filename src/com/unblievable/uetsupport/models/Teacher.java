package com.unblievable.uetsupport.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "teacher")
public class Teacher {
	@Id
	public Long teacherId;
	
	public String fullname;
	public String email;
	public String phone;
	public String address;
	public String description;
	
	@Transient
	public Faculty faculty;
	
	@Column(name = "createdTime", nullable = false)
	@JsonIgnore
	public Date createdTime;
	@Column(name = "modifiedTime", nullable = false)
	@JsonIgnore
	public Date modifiedTime;
}
