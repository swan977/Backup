package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Student {

	@Id
	private String id;
	private String name;
	private int age;
	private String prog;
	
	
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", prog=" + prog + "]";
	}
	
	
	
	public Student() {
		super();
		System.out.println("Student object created");
		this.id = "1723456";
		this.name = "Default";
		this.age = 21;
		this.prog = "Not defined";
		
			
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getProg() {
		return prog;
	}
	public void setProg(String prog) {
		this.prog = prog;
	}
	
	
	
	
	
	
}
