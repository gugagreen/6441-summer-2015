<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<p><b>Players:</b></p>
<c:forEach begin="1" end="${fn:length(gamePlayers)}" varStatus="loop">
	<c:set var="player" value="${gamePlayers[loop.index-1]}"/>
	Player ${loop.index}: ${player.name}<br/>
	<p/>
</c:forEach>
