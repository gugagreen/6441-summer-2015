package ca.concordia.lanterns.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.services.SetupService;
import ca.concordia.lanterns.services.impl.DefaultSetupService;

@Path("/game")
public class GameResource {

	private SetupService setupService = DefaultSetupService.getInstance();

	@POST
	public Response createGame(@QueryParam("p1") final String p1, @QueryParam("p2") final String p2,
			@QueryParam("p3") final String p3, @QueryParam("p4") final String p4) {
		String[] playerNames = new String[] { p1, p2, p3, p4 };
		Game game = setupService.createGame(playerNames);

		return Response.status(200).entity(game).build();
	}
}
