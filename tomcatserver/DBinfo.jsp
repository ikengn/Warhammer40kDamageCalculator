<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "backend.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

  <%
  	Webhelper webh =  new Webhelper ();
    ArrayList<Integer> tester = new ArrayList<>(Arrays.asList(3,5,3));
  	
  %>
  
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Unitinfo</title>

</head>
<body>
  <br />
  unit selected:
  <br />
  <%= request.getParameter("unit")%>
  <br />
  Status:
  <br />
  <%=webh.getUnitStat(request.getParameter("unit"))%>


  <br />
  <br />
  <br />

  
 
  
  
</body>
</html>