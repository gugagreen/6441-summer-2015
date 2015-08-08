<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="lake" required="true" type="ca.concordia.lanternsentities.LakeTile[][]" %>

<div style="height: 480px; width: 640px; font-size: 12px; overflow: auto; background-color: #5599CC; display:table-cell;vertical-align:middle;">
	<table style="margin-left:auto;margin-right:auto;">
		<c:forEach begin="0" end="${fn:length(lake) -1}" varStatus="ii">
			<tr>
				<c:forEach begin="0" end="${fn:length(lake[ii.index]) -1}"
					varStatus="jj">
					<c:set var="tile" value="${lake[ii.index][jj.index]}" />
					<td><input id="dir_${tile.id}" type="hidden"
								value="${tile.direction}" />
								<img id="img_${tile.id}"
								src="../img/tiles/tile_${tile.id}.jpg" width="50" height="50"
								onclick="rotate('${tile.id}');" onload="setRotation('${tile.id}',${tile.direction});"/></td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
</div>
