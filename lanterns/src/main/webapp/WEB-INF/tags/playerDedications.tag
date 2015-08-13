<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="dedications" required="true" type="java.util.List" %>
<%@ attribute name="id" required="true" type="java.lang.Integer" %>

<div id="playerDedications_div_${id}">
	<table>
		<tr>
			<c:set var="sum" value="${0}"/>  
			<c:forEach var="dedication" items="${dedications}">
				<c:set var="sum" value="${sum + dedication.tokenValue}"/>
			</c:forEach>
			<td id="playerDedications_${id}" width="80" height="30">
				<c:out value="dedications: ${sum}"/>
			</td>
		</tr>
	</table>
</div>
