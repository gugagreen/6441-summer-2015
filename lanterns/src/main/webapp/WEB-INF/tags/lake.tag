<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="lake" required="true" type="ca.concordia.lanternsentities.LakeTile[][]" %>


<div STYLE="height: 300px; width: 100px; font-size: 12px; overflow: auto;">
	<table border="1">
		<c:forEach begin="0" end="${fn:length(lake) -1}" varStatus="ii">
			<tr>
				<c:forEach begin="0" end="${fn:length(lake[ii.index]) -1}"
					varStatus="jj">
					<c:set var="tile" value="${lake[ii.index][jj.index]}" />
					<td><input id="dir_${tile.id}" type="hidden"
								value="${tile.direction}" />
								<img id="img_${tile.id}"
								src="../img/tiles/tile_${tile.id}.jpg" width="50" height="50"
								onclick="rotate('${tile.id}');" onload="rotate('${tile.id}');"/></td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
</div>
