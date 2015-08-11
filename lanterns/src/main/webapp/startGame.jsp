<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<title>Lanterns - Team A</title>
</head>
<body>

	<select name='ai_cbx'>
		<option selected disabled>Choose one AI:</option>
		<c:forEach items="${ais}" var="ai">
			<option value="${ai}">${ai}</option>
		</c:forEach>
	</select>
	
	<h2>Hello World!</h2>
	<form action="startGame" method="post">
		<c:forEach begin="0" end="3" var="i">
			Player ${i + 1}: <input id="p${i + 1}" name="p${i + 1}" type="text">
			<select name='ai_cbx_${i + 1}'>
				<option selected disabled>Choose one AI:</option>
				<c:forEach items="${ais}" var="ai">
					<option id="option_${ai}_${i + 1}" value="${ai}">${ai}</option>
				</c:forEach>
			</select>
			<br/>
		</c:forEach>
		<input type="submit" value="Start">
	</form>
</body>
</html>
