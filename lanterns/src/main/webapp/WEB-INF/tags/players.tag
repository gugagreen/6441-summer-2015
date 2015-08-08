<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="player" required="true" type="ca.concordia.lanternsentities.Player" %>

<c:if test="${not empty player}">
Player ${player.id}: ${player.name}
</c:if>
