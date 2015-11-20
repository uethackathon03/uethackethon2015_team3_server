package com.unblievable.uetsupport.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.unblievable.uetsupport.dao.AbstractDao;
import com.unblievable.uetsupport.dao.StudentDao;
import com.unblievable.uetsupport.models.Student;

public class StudentDaoImpl extends AbstractDao implements StudentDao {

	public StudentDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Long save(Student student) {
		Long autoId = (Long) getSession().save(student);
		return autoId;
	}
	
	@Override
	public Long update(Student student) {
		getSession().update(student);
		return student.studentId;
	}

	@Override
	public Student findStudentById(Long studentId) {
		Student student = (Student) getSession().get(Student.class, studentId);
		if (student != null) {
			return student;
		}
		return null;
	}

	@Override
	public Student findStudentByUsername(String username) {
		Student student = (Student) getSession()
				.createCriteria(Student.class)
				.add(Restrictions.eq("username", username))
				.uniqueResult();
		if (student != null) {
			return student;
		}
		return null;
	}

	@Override
	public Student findStudentByEmail(String email) {
		Student student = (Student) getSession()
				.createCriteria(Student.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
		if (student != null) {
			return student;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> listAllStudent() {
		Criteria criteria = getSession().createCriteria(Student.class);
		return criteria.list();
	}

	@Override
	public boolean deleteStudent(Long studentId) {
		Student student = (Student) getSession().get(Student.class, studentId);
		if (student != null) {
			getSession().delete(student);
			return true;
		}
		return false;
		
	}

}
