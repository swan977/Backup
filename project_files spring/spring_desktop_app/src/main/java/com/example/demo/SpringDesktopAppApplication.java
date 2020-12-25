package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringDesktopAppApplication {

	public static void main(String[] args) {
   ConfigurableApplicationContext context = SpringApplication.run(SpringDesktopAppApplication.class, args);
	
		//Dependency Injection
		
       Student s1 = context.getBean(Student.class);
   
		//Student s1 = new Student();
		
		
		System.out.println(s1.showStu_AA());
		
		//Singleton pattern
		
		// Student s2 = context.getBean(Student.class);
		   
			//System.out.println(s2.toString());
		
		
		//Academicadviser AA = context.getBean(Academicadviser.class);
		//System.out.println(AA.toString());
		
		
		
		System.out.println("Hello Spring Boot");
	
	}

}
