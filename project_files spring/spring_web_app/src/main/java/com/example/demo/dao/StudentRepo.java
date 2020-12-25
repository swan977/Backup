package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Student;




public interface StudentRepo extends CrudRepository<Student,String>{

	@Override
	List<Student> findAll();
	
	List<Student> findByProg(String prog);
	
	@Query("select s from Student s where prog=?1 order by name")
	List<Student> findByProgSortedByName(String prog);

	
	
	
}
