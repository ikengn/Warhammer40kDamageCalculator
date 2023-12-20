<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"      import = "backend.*"  %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>



<%
	//everything inside < % % > is jsp 
	

	//placeholder for sending data to html/js from jsp
	ArrayList<String> names = new ArrayList<String>();
	String PassingData="";
	
			
	Webhelper webh1 =  new Webhelper ();
	names = webh1.getAllUnitslist();
	
	
%>









<!DOCTYPE html>
	<html>
		
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>DATABASE</title>
		
	
			<script>
			
			
		
			var units  = [];
			
			<%for(int i=0;i<names.size();i++){%> 
			    units.push("<%= names.get(i)%>"); 
			<%}%> 
			
			
			
			
			//based on index.js 
			function populate(item){
				
				
			    var unit1Drop = document.getElementById(item);
			    var i = 0
			    var length = unit1Drop.options.length
			    while(length>1){ //removes all , but the --please ... -- option
			        length = length-1
			        unit1Drop.remove(length);
			    }
			    while(i<units.length){
			        var opt = document.createElement("option");
			        opt.value = units[i]
			        opt.innerHTML = units[i]
			        unit1Drop.appendChild(opt)
			        i = i+1
			    }

			}
			
			function initialize(){
				populate("unitlist");
			}
	
			document.addEventListener("DOMContentLoaded",initialize);

			
		
			
			
			</script>
			
			<link rel="stylesheet" href="style.css">
		</head>
		
		<%@ include file="navBar.html" %>

		<body>
		
	
            <img src="images/warhammer.png" alt="Warhammer 40k Logo" style="margin-top:50px; display: block; margin-left: auto; margin-right: auto; width: 50%;">
			
			<br />
			<form action="DBinfo.jsp" method="POST" target="_blank">
				search unit: <input type="text" name="unit" />
				<input type="submit" value="search" />
			</form>
			
			
			<form action="DBinfo.jsp" method="POST" target="_blank">
			<b> Search the unit  </b>
			<select id = unitlist  name = "unit" onchange = "favTutorial()" >
			<option> ---Choose unit--- </option>
			
			</select>
		
		
			
			
			
			<input type="submit" value="search" />
			</form>
			
			
			
		
		</body>
	
	</html>












