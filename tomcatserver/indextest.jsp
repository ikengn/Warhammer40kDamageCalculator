<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"      import = "DB.*"  %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<%
	//everything inside < % % > is jsp 
	

	//placeholder for sending data to html/js from jsp
	ArrayList<String> names = new ArrayList<String>();
	String PassingData="";
	
	try{
	
		//Get the database connection
		WarhammerDB db = new WarhammerDB();	
		Connection con = db.getConnection();		

		//Create a SQL statement
		Statement stmt = con.createStatement();
		
	
		//Make a query
		String query1 = "" ;
	
		//find the list of units
	  	query1 = " select Name from units";
	  	
		//excute the query 
		PreparedStatement ps1 = con.prepareStatement(query1);
		ResultSet result1 = ps1.executeQuery();
		
		//process the resultset
		while(result1.next()){
	names.add(result1.getString("Name"));
		}
		result1.close();
		
 	  	// don't forget close db connection 
     	con.close();
		
 	  	
	}
	catch(SQLException e)
	{
		out.println(e);
	}
%>









<!DOCTYPE html>
	<html>
		
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Homepage</title>
		
			<script>
			var data = [<%=PassingData%>];
			
			
			
			</script>
			
			
		</head>


		<body>
		
	
			
			
			<br />
			number of dice:<input type="text" name="url" />
			<input type="submit" value="roll" />
			</form>
			
			<form action="battletemplate.jsp" method="POST" target="_blank">
			<b> Select the unit for your team </b>
			<select name =funit onchange = "favTutorial()" >
			<option> ---Choose units--- </option>
				<option><%=names.get(0) %> </option>
				<option><%=names.get(1) %> </option>
				<option> <%=names.get(2) %> </option>
				<option> <%=names.get(3) %> </option>
			</select>
		
		<b> Select the unit for your enemy </b>
			<select name = ounit onchange = "favTutorial()" >
			<option> ---Choose units--- </option>
				<option><%=names.get(0) %> </option>
				<option><%=names.get(1) %> </option>
				<option> <%=names.get(2) %> </option>
				<option> <%=names.get(3) %> </option>
			</select>
			
			<input type="checkbox" name="checkb1" checked="checked" /> randomdice
			<input type="submit" value="battle" />
			</form>
			
			
			
		
		</body>
	
	</html>












