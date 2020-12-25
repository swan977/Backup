package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="prototype")
public class Student {

	
	
private String stu_id;
private String stu_name;
private int stu_age;

@Autowired
private Academicadviser AA;


public Academicadviser getAA() {
	return AA;
}

public void setAA(Academicadviser aA) {
	AA = aA;
}



public Student() {
	super();
	// TODO Auto-generated constructor stub
	System.out.println("Student object created");
	this.stu_id = "17ACB23456";
	this.stu_name = "Default";
	this.stu_age = 21;
	
}



public Student(String stu_id, String stu_name, int stu_age) {
	super();
	this.stu_id = stu_id;
	this.stu_name = stu_name;
	this.stu_age = stu_age;
}



public String getStu_id() {
	return stu_id;
}
public void setStu_id(String stu_id) {
	this.stu_id = stu_id;
}
public String getStu_name() {
	return stu_name;
}
public void setStu_name(String stu_name) {
	this.stu_name = stu_name;
}
public int getStu_age() {
	return stu_age;
}
public void setStu_age(int stu_age) {
	this.stu_age = stu_age;
}


@Override
public String toString() {
	return "Student [stu_id=" + stu_id + ", stu_name=" + stu_name + ", stu_age=" + stu_age + "]";
}



public String showStu_AA() {
	
	
	return this.toString() +  " " + AA.toString();
	
	
}

}
