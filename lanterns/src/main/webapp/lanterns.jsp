<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<p><b>Lantern Cards:</b></p>
<table>
	<thead>
		<tr>
			<c:forEach begin="1" end="${fn:length(gameCards)}" varStatus="loop">
				<th><c:out value="${gameCards[loop.index-1].colour}"/></th>
			</c:forEach>
		</tr>
	</thead>
	<tbody>
		<tr>
			<c:forEach begin="1" end="${fn:length(gameCards)}" varStatus="loop">
				<td><c:out value="${gameCards[loop.index-1].quantity}"/></td>
			</c:forEach>
		</tr>
	</tbody>
</table>

