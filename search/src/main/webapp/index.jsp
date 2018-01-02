<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.oss.NLPProcesses" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSS Topic Search</title>     
        <link href="index.css" rel="stylesheet" type="text/css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
		
    </head>

	<body>

	<div class="jumbotron" style="background-image:url('background.png');">
		<h1 style="text-align:center">OSS Topic Searching</h1>
		<p style="text-align:center">This system supports two websites for now.</p>
	</div>

	<div class="container" id="search">
		<form action="GetParameters" method="post">

			<div class="form-group">
				<label for="repos">Select Website</label>
				<select class="form-control" id="repos" name="repo">
					<option value="github" onclick="showstuff('pls');">GitHub</option>
					<option value="sourceforge" onclick="hidestuff('pls');">SourceForge</option>
				</select>
			</div>

			<div class="form-group">
				<label for="exampleFormControlTextarea1">Features</label>
				<textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="topic"></textarea>
			</div>


			<div class="form-group" id="pls">
				<label for="exampleFormControlTextarea1">Select Language: </label>
				<%
					NLPProcesses a = new NLPProcesses();
					ArrayList<String> pls = a.getLangs();
					Collections.sort(pls, String.CASE_INSENSITIVE_ORDER);
					String pl;
				%>
				<select class ="form-control" name="pl">
				<% 
					for (int i= 0 ; i<pls.size(); i++) {
					    pl = pls.get(i);
				%>

				<option value='<%=pl%>'><%=pl%></option>
				<% } %> 	</select>
			</div>
			
			<button type="submit"  onclick="showloader();" class="btn btn-primary btn-md" style="margin-left:40%;"><span class="glyphicon glyphicon-search"></span>&nbsp Search</button>
			
		</form>
	</div>

	<table>
		<b style="margin-bottom:3%; text-align: center;"><c:out value="${err} "></c:out></b>
	</table>
	
	
	<div class="table-responsive" id="ress"  style="width:75%; margin-left:10%; margin-bottom:5%;">
	<table class="table">
		
			<c:forEach items="${results}" var="result" varStatus="stat">
				<c:if test="${stat.first}">
					<thead>
						<tr>
							<th style="width:80%"><c:out value="${result[0]}"></c:out></th>
							<th ><c:out value="${result[1]} "></c:out></th>
						</tr>
					</thead>
    			</c:if>
    			<c:if test="${!stat.first}">
    				<tbody>
    					<tr>
							<td  style="width:80%"><a href='<c:out value="${result[0]}"></c:out>'><c:out value="${result[0]}"></c:out></a></td>
							<td><c:out value="${result[1]} "></c:out></td>
						</tr>
					</tbody>
    			</c:if>
			</c:forEach>
	</table>
	</div>

	<div id="loader" style="position:absolute; margin-left:42%; display:none;">
		<div class="loader"></div>Searching Please Wait

		<footer>
			<p>©2017<a style="color:#0a93a6; text-decoration:none;" href="#"> HacettepeUniversity</a>. All rights reserved.</p>
		</footer>

	</body>

	<script>
		window.onload = function(){ document.getElementById("ress").style.display = "block" }   
        function showloader() {
            var x = document.getElementById("loader");
            x.style.display ="block";
            var y = document.getElementById("search");
            y.style.display= "none";
            var z = document.getElementById("ress");
            z.style.display= "none";
        }
        function showstuff(boxid){
            console.log("girdi")
			document.getElementById(boxid).style.visibility="visible";
		}
			 
		function hidestuff(boxid){
			document.getElementById(boxid).style.visibility="hidden";
		}
	</script>

	
	
</html>
