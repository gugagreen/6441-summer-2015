<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<html>
<head>
	<link href="../css/game.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../js/game.js"></script>
</head>

<body>
	<h2>Game!</h2>

	<c:if test="${not empty game}">
		<table>
			<tr>
				<td><h:lanterns cards="${game.cards}" /></td>
			</tr>
			<tr>
				<td>
					<table width="100%">
						<tr>
							<td>
								<div id="tiles" class="tiles">
									<div class="centertext"><p>${fn:length(game.tiles)}</p>
									</div>
								</div>
							</td>
							<td>
								<div id="favors" class="favors">
									<div class="centertext"><p>${game.favors}</p>
									</div>
								</div>
							</td>
							<td>
								<c:set var="gameDedications" value="${game.dedications}" scope="request" />
								TODO - dedications
							</td>
						</tr>	
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%">
						<c:set var="gamePlayers" value="${game.players}" scope="request" />
						<tr>
							<td>&nbsp;</td>
							<td><h:players player="${game.players[3]}" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><h:players player="${game.players[2]}" /></td>
							<td><h:lake lake="${game.lake}"/></td>
							<td><h:players player="${game.players[0]}" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><h:players player="${game.players[1]}" /></td>
							<td>&nbsp;</td>
						</tr>
					</table>
			
					
				</td>
			</tr>
			<tr>
				<td>
					Area for user
				</td>
			</tr>
		</table>

		

	</c:if>
</body>
</html>
