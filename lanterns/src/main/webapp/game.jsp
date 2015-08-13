<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<html>
<head>
	<title>Lanterns - Team A</title>
	<link href="../css/game.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../js/game.js"></script>
</head>

<body>
	<c:if test="${not empty game}">
		<form id="gameForm" action="game" method="post">
			<input type="hidden" id="playerAction" name="playerAction" value="none">
			<input type="hidden" id="currentPlayer" name="currentPlayer" value="${game.currentTurnPlayer}">
			<input type="hidden" id="currentPlayerAIType" name="currentPlayerAIType" value="${game.aiPlayers[game.currentTurnPlayer].type}">
			<input type="hidden" id="nextAction" name="nextAction" value="${requestScope.nextAction}">
			<div id="gameBoard" style="background-color: #772222;" align="center">
				<h:status owner="${game}" cards="${game.cards}" tiles="${game.tiles}" favors="${game.favors}" vertical="false"/>
			</div>
			<table>
				<tr>
					<td>
						<table style="width: 100%;">
							<tr>
								<td>&nbsp;</td>
								<td><h:players player="${game.players[3]}" vertical="false" /></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td><h:players player="${game.players[2]}" vertical="true" /></td>
								<td><h:lake lake="${game.lake}"/></td>
								<td><h:players player="${game.players[0]}" vertical="true" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><h:players player="${game.players[1]}" vertical="false" /></td>
								<td>&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		
		<c:if test="${not empty winners}">
			<h:winners winners="${winners}"/>
		</c:if>
		

	</c:if>
</body>
</html>
