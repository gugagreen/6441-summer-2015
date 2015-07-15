<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<p><b>Lantern Cards:</b></p>
<table>
	<thead>
		<tr>
			<th>ORANGE</th>
			<th>GREEN</th>
			<th>PURPLE</th>
			<th>GRAY</th>
			<th>BLUE</th>
			<th>RED</th>
			<th>BLACK</th>
	</thead>
	
	<tbody>
	<tr>
		<c:forEach begin="1" end="${fn:length(gameCards)}" varStatus="loop">
			<td><c:out value="${fn:length(gameCards[loop.index-1].stack)}"/></td>
		</c:forEach>
	</tr>
	</tbody>
</table>

