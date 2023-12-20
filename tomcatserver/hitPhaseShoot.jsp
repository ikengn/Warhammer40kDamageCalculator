<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import = "backend.*" import = "DB.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="java.util.zip.DataFormatException"%>
<%

	//placeholder for sending data to html/js from jsp
	ArrayList<String> names = new ArrayList<String>();
	String PassingData="[";
	ArrayList<Boolean> hits = new ArrayList<Boolean>();
	String rollsString = request.getParameter("rolls");
	String[] rollsStringList = rollsString.split(",");
	List<Integer> rolls = new ArrayList<Integer>(rollsStringList.length) ;
		for (String myInt : rollsStringList) 
		{ 
			rolls.add(Integer.valueOf(myInt)); 
		}
		double damage = 0;
		for(Integer d : rolls)
		damage += d;
		out.println(damage);
%>

