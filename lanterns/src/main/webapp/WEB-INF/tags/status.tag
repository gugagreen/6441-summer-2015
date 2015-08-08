<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@ attribute name="cards" required="true" type="ca.concordia.lanternsentities.LanternCardWrapper[]" %>
<%@ attribute name="tiles" required="true" type="java.util.Collection" %>
<%@ attribute name="favors" required="true" type="java.lang.Integer" %>
<%@ attribute name="vertical" required="true" type="java.lang.Boolean" %>

<%-- this uses tr, so needs to be inside a table --%>
<c:choose>
	<c:when test="${vertical == false}">
		<tr>
			<td align="center"><h:lanterns cards="${cards}" vertical="${vertical}"/></td>
		</tr>
		<tr>
			<td align="center" style="width: 640px;">
				<table style="width: 100%;">
					<tr>
						<td>
							<div id="tiles" class="tiles">
								<div class="centertext"><p>${fn:length(tiles)}</p>
								</div>
							</div>
						</td>
						<td>
							<div id="favors" class="favors">
								<div class="centertext"><p>${favors}</p>
								</div>
							</div>
						</td>
						<td>
							<c:set var="dedications" value="${dedications}" scope="request" />
							TODO - dedications
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</c:when>
	<c:otherwise>
		<tr>
			<td><h:lanterns cards="${cards}" vertical="${vertical}"/></td>
			<td style="height: 480px;">
				<table style="height: 100%;">
					<tr>
						<td>
							<div id="tiles" class="tiles">
								<div class="centertext"><p>${fn:length(tiles)}</p>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div id="favors" class="favors">
								<div class="centertext"><p>${favors}</p>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<c:set var="dedications" value="${dedications}" scope="request" />
							TODO - dedications
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</c:otherwise>
</c:choose>
