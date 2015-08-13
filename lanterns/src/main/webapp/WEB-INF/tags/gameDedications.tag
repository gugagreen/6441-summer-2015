<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="dedicationWrappers" required="true" type="ca.concordia.lanternsentities.DedicationTokenWrapper[]" %>

<div id="gameDedications_div">
	<table>
		<tr>
			<c:forEach var="wrapper" items="${dedicationWrappers}">
				<c:set var="sum" value="${0}"/>  
				<c:forEach var="dedication" items="${wrapper.stack}">
					<c:set var="sum" value="${sum + dedication.tokenValue}"/>
				</c:forEach>
				<td id="gameDedications_${wrapper.stack[0].tokenType}" width="80" height="30">
					<c:out value="${wrapper.stack[0].tokenType}: ${sum}"/>
				</td>
			</c:forEach>		
		</tr>
	</table>
</div>
