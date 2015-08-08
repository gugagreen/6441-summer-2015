<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ attribute name="cards" required="true" type="ca.concordia.lanternsentities.LanternCardWrapper[]" %>

<p><b>Lantern Cards:</b></p>
<table>
	<tbody>
		<tr>
			<c:forEach begin="1" end="${fn:length(cards)}" varStatus="loop">
				<c:set var="card" value="${cards[loop.index-1]}" />
				<c:set var="colour" value="${fn:toLowerCase(card.colour)}" />
				<th>
					<div id="${colour}" class="lantern ${colour}">
						<div class="centertext"><p>${card.quantity}</p>
						</div>
					</div>
				</th>
			</c:forEach>
		</tr>
	</tbody>
</table>


