package com.unblievable.uetsupport.dao;

import java.util.List;

import com.unblievable.uetsupport.models.Student;

public interface StudentDao {
	
	public Long save(Student student);
	
	public Long update(Student student);
	
	public Student findStudentById(Long studentId);
	
	public Student findStudentByUsername(String username);
	
	public Student findStudentByEmail(String email);
	
	public List<Student> listAllStudent();
	
	public boolean deleteStudent(Long studentId);
	
}
