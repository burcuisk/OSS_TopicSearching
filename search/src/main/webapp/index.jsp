<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSS Topic Search</title>     
        <link href="index.css" rel="stylesheet" type="text/css">
    </head>
    
    <body>
    	<h1>Open Source Software Products</h1>
    	<h2>Topic Search</h2>
		<form id="topics" action="GetParameters" method="post">
			<div id="topic">
				Enter Topics: <input type="text" name="topic"><br>
			</div>
			<br>
  			<div id="repos">
             	<label>OSS repository:</label>
             	<select name="repo">
               	<option value = "GitHub">GitHub</option>
               	<option value = "SourceForge">SourceForge</option>
            	 </select>
   			</div>
   			<br>
   			<div id="PL">
  				<label>Programming Language:</label>
          		<br>
    			<input id="pl1" type = "checkbox" name = "pl" value="C" /> C        
    			<input id="pl2" type = "checkbox" name = "pl" value="C++" /> C++	      
    			<input id="pl" type = "checkbox" name = "pl" value="Java" /> Java	      
    			<input id="pl" type = "checkbox" name = "pl" value="UnixShell" /> UnixShell	 
        		<br>
    			<input id="pl1" type = "checkbox" name = "pl" value="Python" /> Python	
    			<input id="pl2" type = "checkbox" name = "pl" value="C#" /> C#	
    			<input id="pl" type = "checkbox" name = "pl" value="Perl" /> Perl	
    			<input id="pl" type = "checkbox" name = "pl" value="Matlab" /> Matlab	
        		<br>
    			<input id="pl1" type = "checkbox" name = "pl" value="VisualBasic" /> VisualBasic	
    			<input id="pl2" type = "checkbox" name = "pl" value="Ada" /> Ada	
    			<input id="pl" type = "checkbox" name = "pl" value="JavaScript" /> JavaScript	
    			<input id="pl" type = "checkbox" name = "pl" value="PL/SQL" /> PL/SQL	
        		<br>
    			<input id="pl1" type = "checkbox" name = "pl" value="AppleScript" /> AppleScript	
    			<input id="pl2" type = "checkbox" name = "pl" value="PHP" /> PHP	
    			<input id="pl" type = "checkbox" name = "pl" value="Ruby" /> Ruby	
    			<input id="pl" type = "checkbox" name = "pl" value="Delphi/Kylix" /> Delphi/Kylix	
        		<br>
        	</div>
        	<div id="category">
        	  	<label>Categories:</label>
        	  	<br>
        		<input id="cat1" type = "checkbox" name = "cat" value="Modeling" /> Modeling
        		<input id="cat" type = "checkbox" name = "cat" value="WindowManagers" /> Window Managers
        		<input id="cat" type = "checkbox" name = "cat" value="Scheduling" /> Scheduling
        		<input id="cat" type = "checkbox" name = "cat" value="Software" /> Software
        		<br>
        		<input id="cat1" type = "checkbox" name = "cat" value="Visualization" /> Visualization
        		<input id="cat" type = "checkbox" name = "cat" value="Web Services" /> Web Services
        		<input id="cat" type = "checkbox" name = "cat" value="Systems Administration" /> Systems Administration
        		<input id="cat" type = "checkbox" name = "cat" value="Simulation" /> Simulation
        		<br>
        		<input id="cat1" type = "checkbox" name = "cat" value="Workflow" /> Workflow
        		<input id="cat" type = "checkbox" name = "cat" value="Video" /> Video
        		<input id="cat" type = "checkbox" name = "cat" value="Storage" /> Storage
        		<input id="cat" type = "checkbox" name = "cat" value="Scientific/Engineering" /> Scientific/Engineering
        		<br>
        		<input id="cat1" type = "checkbox" name = "cat" value="Text" /> Text
        		<input id="cat" type = "checkbox" name = "cat" value="UserInterface" /> User Interface
        		<input id="cat" type = "checkbox" name = "cat" value="Sound/Audio" /> Sound/Audio
        		<input id="cat" type = "checkbox" name = "cat" value="ObjectOriented" /> Object Oriented
        		<br>
        	</div>
        	<input id="submit1" type = "submit" value = "Search" />
		</form> 
	
		<table>
    		<c:forEach items="${results}" var="result">
        		<c:out value="${result}"></c:out>
        		<br>
    		</c:forEach>
		</table>   
	  
	
	</body>
	
	
</html>


