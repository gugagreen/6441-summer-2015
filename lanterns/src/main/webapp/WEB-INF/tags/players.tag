<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@ attribute name="player" required="true" type="ca.concordia.lanternsentities.Player" %>
<%@ attribute name="vertical" required="true" type="java.lang.Boolean" %>

<c:if test="${not empty player}">
	<div id="player_${player.id}" style="pointer-events: none; background-color: #FFFFFF">
		Player ${player.id}: ${player.name}<p/>
		<table><h:status owner="${player}" cards="${player.cards}" tiles="${player.tiles}" favors="${player.favors}" vertical="${vertical}"/></table>
		<h:playerTiles owner="${player.id}" tiles="${player.tiles}" />
		<h:playerAction owner="${player.id}" />
	</div>
</c:if>
