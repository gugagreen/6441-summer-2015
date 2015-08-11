<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File List</title>
</head>
<body>
	<h2>List of files in: ${dir}</h2>
	<c:forEach items="${files}" var="file">
		<c:out value="${file.name}"/><br/>
	</c:forEach>

</body>
</html>