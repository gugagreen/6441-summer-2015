<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="cards" required="true" type="ca.concordia.lanternsentities.LanternCardWrapper[]" %>
<%@ attribute name="vertical" required="true" type="java.lang.Boolean" %>

<table>
	<tbody>
		<c:choose>
			<c:when test="${vertical == false}">
				<tr>
					<c:forEach begin="1" end="${fn:length(cards)}" varStatus="loop">
						<c:set var="card" value="${cards[loop.index-1]}" />
						<c:set var="colour" value="${fn:toLowerCase(card.colour)}" />
						<td>
							<div id="${colour}" class="lantern ${colour}">
								<div class="centertext"><p>${card.quantity}</p>
								</div>
							</div>
						</td>
					</c:forEach>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach begin="1" end="${fn:length(cards)}" varStatus="loop">
					<tr><td>
						<c:set var="card" value="${cards[loop.index-1]}" />
						<c:set var="colour" value="${fn:toLowerCase(card.colour)}" />
							<div id="${colour}" class="lantern ${colour}">
								<div class="centertext"><p>${card.quantity}</p>
								</div>
							</div>
					</td></tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>


