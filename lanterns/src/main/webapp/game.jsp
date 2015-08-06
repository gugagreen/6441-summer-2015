<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<style type="text/css">
.rotate0 {
	-webkit-transform: rotate(0deg);
	transform: rotate(0deg);
}

.rotate90 {
	-webkit-transform: rotate(90deg);
	transform: rotate(90deg);
}

.rotate180 {
	-webkit-transform: rotate(180deg);
	transform: rotate(180deg);
}

.rotate270 {
	-webkit-transform: rotate(270deg);
	transform: rotate(270deg);
}
</style>

<script type="text/javascript">
	function rotate(id) {
		var img = document.getElementById("img_" + id);
		var direction = document.getElementById("dir_" + id);
		if (direction.value == 0) {
			img.setAttribute("class", "rotate90");
			direction.value = 1;
		} else if (direction.value == 1) {
			img.setAttribute("class", "rotate180");
			direction.value = 2;
		} else if (direction.value == 2) {
			img.setAttribute("class", "rotate270");
			direction.value = 3;
		} else if (direction.value == 3) {
			img.setAttribute("class", "rotate0");
			direction.value = 0;
		}
	}
</script>
</head>

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

		<p />
		<!-- FIXME - dummy for testing matrix -->
		<%!public class Tile {
		String id;
		String path;
		int direction;

		public Tile(String id, String path, int direction) {
			this.id = id;
			this.path = path;
			this.direction = direction;
		}

		public String getId() {
			return this.id;
		}

		public String getPath() {
			return this.path;
		}

		public int getDirection() {
			return this.direction;
		}
	}%>
		<%
			Tile t51 = new Tile("T51", "/img/tiles_5-1.jpg", 2);
				Tile t52 = new Tile("T52", "/img/tiles_5-2.jpg", 3);
				Tile t53 = new Tile("T53", "/img/tiles_5-3.jpg", 0);
				Tile t54 = new Tile("T54", "/img/tiles_5-4.jpg", 2);
				Tile[][] tiles = new Tile[][] { { t51, t52 }, { t53, t54 } };
				pageContext.setAttribute("matrix", tiles, pageContext.SESSION_SCOPE);
		%>

		<div
			STYLE="height: 300px; width: 100px; font-size: 12px; overflow: auto;">
			<table border="1">
				<c:forEach begin="0" end="${fn:length(matrix) -1}" varStatus="ii">
					<tr>
						<c:forEach begin="0" end="${fn:length(matrix[ii.index]) -1}"
							varStatus="jj">
							<c:set var="tile" value="${matrix[ii.index][jj.index]}" />
							<td><input id="dir_${tile.id}" type="hidden"
								value="${tile.direction}" />
								<img id="img_${tile.id}"
								src="${tile.path}" width="50" height="50"
								onclick="rotate('${tile.id}');" onload="rotate('${tile.id}');"/></td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</div>

	</c:if>
</body>
</html>
