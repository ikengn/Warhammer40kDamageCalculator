<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import = "backend.*" import = "DB.*" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<%

	//placeholder for sending data to html/js from jsp
	ArrayList<String> names = new ArrayList<String>();
	String PassingData="Captain, Captain on Bike, Imperial Space Marine, Space Marine";
	String u1="test";
	String u2="please";
	String u3="work";
	ArrayList<Boolean> hits = new ArrayList<Boolean>();
	ArrayList<Integer> rolls = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
	//PassingData = String.join(", ", names);
%>



<!DOCTYPE html>
	<html>
		<script type="text/javascript">
			var data = [];
			<%@ include file="damageCalculator.js" %>
			
			</script>
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Damage Calculator</title>
			
			
		</head>
		<%@ include file="damageCalculator.html" %>

		<body>
	
			<!--
				I will need to use an post method form to send the dice/units/etc. over to a roll.jsp file
				Then, within roll.jsp, I can actually perform the calculation, before sending the information back here?
				I think I can make this work, it will just be kind of weird
			-->
			<div id="hitPhaseTest" style = "display:none;">
				<a id="numberOfHits" class="whiteText"></a>
				<br>
				<a id="holder" class="whiteText"></a>
			</div>
		
		
		</body>

	</html>












