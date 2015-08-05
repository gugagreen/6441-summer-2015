<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<body>
	<h2>Game!</h2>

	<c:if test="${not empty game}">

		<c:set var="gamePlayers" value="${game.players}" scope="request" />
		<jsp:include page="players.jsp" />

		<c:set var="gameCards" value="${game.cards}" scope="request" />
		<jsp:include page="lanterns.jsp" />

		<c:set var="gameTiles" value="${game.tiles}" scope="request" />

		<c:set var="gameDedications" value="${game.dedications}"
			scope="request" />

		<p>
			<b>Favors:</b>
		</p>
		<c:out value="${game.favors}" />

		<c:set var="gameLake" value="${game.lake}" scope="request" />

		<div STYLE="height: 100px; width: 100px; font-size: 12px; overflow: auto;">
			<table border="1">
				<tr>
					<td>lalarica banana frita</td>
					<td>T12</td>
				</tr>
				<tr>
					<td>T21</td>
					<td>T22</td>
				</tr>
				<tr>
					<td></td>
					<td>nothing here</td>
				</tr>
				<tr>
					<td><img alt="initial" src="/img/tiles_5-4.jpg" width="50" height="50"/></td>
					<td>neither here</td>
				</tr>
			</table>
		</div>

	</c:if>
</body>
</html>
