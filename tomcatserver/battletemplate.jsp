<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "DB.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	randomdice:
  <%= request.getParameter("checkb1")%>
  <br />
  your unit:
  <br />
  <%= request.getParameter("funit")%>
  <br />
  rival's unit:
  <br />
  <%= request.getParameter("ounit")%>
  
  "Dbmanger connected(only for testing):"
 
  <%
  DBManager dbm = new DBManager(); 
  ArrayList<String> names = new ArrayList<String>();
  String PassingData="";
  
	//System.out.println(dbm.getAllUnitslist().get(0));	
	//System.out.println(dbm.getUnitStat("Captain"));
	//System.out.println(dbm.getAllWeaponlist());	
	//System.out.println(dbm.getWeaponStat("Xenophase blade"));	
  
  %>
  <br>
  <%= dbm.getAllUnitslist().get(0) %>
  <br>
  <%= dbm.getUnitStat("Captain") %>
   <br>
  <%= dbm.getAllWeaponlist() %>
 <br>
  <%= dbm.getWeaponStat("Xenophase blade") %>
 
  
  
</body>
</html>