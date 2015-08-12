<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="owner" required="true" type="java.lang.String" %>
<%@ attribute name="tiles" required="true" type="java.util.List" %>

<table style="margin-left:auto; margin-right:auto;">
	<c:forEach begin="0" end="${fn:length(tiles) -1}" varStatus="ii">
		<tr>
			<c:set var="tile" value="${tiles[ii.index]}" />
			<td>
				<div id="playerTile_${owner}_${tile.id}">
					<input id="dir_${tile.id}" type="hidden"
							value="${tile.direction}" />
							<img id="img_${tile.id}"
							src="../img/tiles/tile_${tile.id}.jpg" width="50" height="50"
							onclick="rotate('${tile.id}');" onload="setRotation('${tile.id}',0);"/>
				</div>
			</td>
		</tr>
	</c:forEach>
</table>
