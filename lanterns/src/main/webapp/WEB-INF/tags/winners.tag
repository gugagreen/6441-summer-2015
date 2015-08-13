<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="winners" required="true" type="ca.concordia.lanternsentities.Player[]" %>

<div id="winners_div" class="modalDialog">
	<div>
		<c:if test="${not empty winners}">
			<c:choose>
				<c:when test="fn:length(winners) > 1">
					<b>Game was a TIE!!!<br/>
					Winners were:</b>
					<ul>
					<c:forEach var="winner" items="winners">
						<li>${winner.name}</li>
					</c:forEach>
					</ul>
				</c:when>
				<c:otherwise>
					<b>Congratulations ${winners[0].name}!</b>
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
</div>
