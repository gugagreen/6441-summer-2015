<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<p><b>Lake:</b></p>


<div STYLE="height: 300px; width: 100px; font-size: 12px; overflow: auto;">
	<table border="1">
		<c:forEach begin="0" end="${fn:length(gameLake) -1}" varStatus="ii">
			<tr>
				<c:forEach begin="0" end="${fn:length(gameLake[ii.index]) -1}"
					varStatus="jj">
					<c:set var="tile" value="${gameLake[ii.index][jj.index]}" />
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
