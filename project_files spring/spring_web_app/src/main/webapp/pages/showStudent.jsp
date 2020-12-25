<%@page import="com.example.demo.model.Student"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
Student s = (Student)request.getAttribute("obj");

	
	out.println(s.getId());
	out.println("<br>");
	out.println(s.getName());
	out.println("<br>");
	out.println(s.getAge());
	out.println("<br>");
	out.println(s.getProg());
	out.println("<br><br>");


%>
</body>
</html>