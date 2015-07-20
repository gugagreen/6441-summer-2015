package ca.concordia.lanterns.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import ca.concordia.lanterns.entities.Game;
import ca.concordia.lanterns.services.SetupService;
import ca.concordia.lanterns.services.impl.DefaultSetupService;

@Path("/game")
public class GameResource {

	private SetupService setupService = DefaultSetupService.getInstance();

	@GET
	public Response getGame(@QueryParam("p1") final String p1, @QueryParam("p2") final String p2,
			@QueryParam("p3") final String p3, @QueryParam("p4") final String p4) {
		// FIXME - make it a POST instead of a GET, as it is creating a game
		String[] playerNames = new String[] { p1, p2, p3, p4 };
		Game game = setupService.createGame(playerNames);

		return Response.status(200).entity(game).build();
	}
}