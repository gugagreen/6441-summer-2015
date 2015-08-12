<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="owner" required="true" type="java.lang.String" %>

<table style="margin-left:auto; margin-right:auto;">
	<tr>
		<td  align="center">
			<div id="exchange_div_${owner}" style="pointer-events: none; opacity: 0.5">
				Exchange lantern?
				<input type="button" value="Yes" onclick="exchange('${owner}');" />
				<input type="button" value="No" onclick="prepareAction('dedication', '${owner}');" />
			</div>
			<div id="dedication_div_${owner}" style="pointer-events: none; opacity: 0.5">
				Make dedication?
				<input type="button" value="Yes" onclick="dedication('${owner}');" />
				<input type="button" value="No" onclick="prepareAction('place', '${owner}');" />
			</div>
			<div id="place_div_${owner}" style="pointer-events: none; opacity: 0.5">
				Please place a tile.
				<input type="button" value="Place Tile" onclick="place('${owner}');" />
			</div>
		</td>
	</tr>
</table>
