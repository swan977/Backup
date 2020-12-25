package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Academicadviser {

	
	private String AA_id;
	private String AA_dept;
	
	
	
	@Override
	public String toString() {
		return "Academicadviser [AA_id=" + AA_id + ", AA_dept=" + AA_dept + "]";
	}
	public Academicadviser(String aA_id, String aA_dept) {
		super();
		AA_id = aA_id;
		AA_dept = aA_dept;
	}
	public Academicadviser() {
		super();
		System.out.println("AA object created");
		this.AA_id = "1234";
		this.AA_dept = "Default";
		
		// TODO Auto-generated constructor stub
	}
	public String getAA_id() {
		return AA_id;
	}
	public void setAA_id(String aA_id) {
		AA_id = aA_id;
	}
	public String getAA_dept() {
		return AA_dept;
	}
	public void setAA_dept(String aA_dept) {
		AA_dept = aA_dept;
	}
	
	
}
