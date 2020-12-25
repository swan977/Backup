package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.StudentRepo;
import com.example.demo.model.Student;

@Controller
public class HomeController {

	@Autowired
	StudentRepo sturepo;
	
	
	@RequestMapping("/home")
	public ModelAndView home(Student p)
	{
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("home");
		mv.addObject("obj", p);
		
		return mv;
	}
	
	@RequestMapping(value="/getAllStudents", method = RequestMethod.GET)
	public ModelAndView getAllStudents()
	{
		
		
		ModelAndView mv = new ModelAndView();
		List<Student> list1 = sturepo.findAll();
		mv.setViewName("showAllStudents");
		mv.addObject("obj", list1);
		
		return mv;
	}
	
	
	
	@RequestMapping(value="/getStudent", method = RequestMethod.GET)
	public ModelAndView getStudent(@RequestParam String id)
	{
		
		
		ModelAndView mv = new ModelAndView();
		Student s = sturepo.findById(id).orElse(new Student());
		
		
		mv.setViewName("showStudent");
		mv.addObject("obj", s);
		
		return mv;
	}
	
	@RequestMapping(value="/addStudent", method = RequestMethod.POST)
	public String addStudent(Student s)
	{
		
		
		sturepo.save(s);
		
		return "home";
		
		
		
	}
	
	@RequestMapping(value="/getStudentByProg", method = RequestMethod.GET)
	public ModelAndView getStudentByProg(@RequestParam String prog)
	{
		
		
		ModelAndView mv = new ModelAndView();
		List<Student> list1 = sturepo.findByProg(prog);
		
		
		mv.setViewName("showAllStudents");
		mv.addObject("obj", list1);
		
		return mv;
	}
	
	
	@RequestMapping(value="/getStudentByProgSorted", method = RequestMethod.GET)
	public ModelAndView getStudentByProgSorted(@RequestParam String prog)
	{
		
		
		ModelAndView mv = new ModelAndView();
		List<Student> list1 = sturepo.findByProgSortedByName(prog);
		
		
		mv.setViewName("showAllStudents");
		mv.addObject("obj", list1);
		
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
}
