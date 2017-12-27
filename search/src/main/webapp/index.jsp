<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSS Topic Search</title>     
        <link href="index.css" rel="stylesheet" type="text/css">
		<script src="jquery-3.2.1.min.js"></script>
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
		<p style="text-align:center">This system support two websites for now.</p>
	</div>

	<div class="container" id="search">
		<form action="GetParameters" method="post">

			<div class="form-group">
				<label for="repos">Select Website</label>
				<select class="form-control" id="repos" name="repo">
					<option value="github">GitHub</option>
					<option value="sourceforge">SourceForge</option>
				</select>
			</div>

			<div class="form-group">
				<label for="exampleFormControlTextarea1">Features</label>
				<textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="topic"></textarea>
			</div>

			<div class="form-group">
				<label for="exampleFormControlTextarea1">Select Language: </label>
				<label class="radio-inline"><input type="radio" name="pl" value="C">C</label>
				<label class="radio-inline"><input type="radio" name="pl" value="java">Java</label>
				<label class="radio-inline"><input type="radio" name="pl" value="PHP">PHP</label>

			</div>

			<button type="submit"  onclick="showloader();" class="btn btn-primary btn-md" style="margin-left:40%;"><span class="glyphicon glyphicon-search"></span>&nbsp Search</button>

		</form>
	</div>

	<table>
		<c:forEach items="${results}" var="result">
			<c:out value="${result[0]} "></c:out>
			<c:out value="${result[1]} "></c:out>
			<br>
			<c:out value="${result[2]}"></c:out>
			<br><br>
		</c:forEach>
	</table>

	 <div id="loader" style="position:absolute; margin-left:42%; display:none;">
		<div class="loader"></div>Searching Please Wait

		<footer>
			<p>©2017<a style="color:#0a93a6; text-decoration:none;" href="#">  HacettepeUniversity</a>.All rights reserved.</p>
		</footer>

	</body>

	<script>
        function showloader() {
            var x = document.getElementById("loader");
            x.style.display ="block";
            var y = document.getElementById("search");
            y.style.display= "none";
        }
	</script>
	
	
</html>


