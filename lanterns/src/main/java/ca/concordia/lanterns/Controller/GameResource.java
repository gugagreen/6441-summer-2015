package ca.concordia.lanterns.Controller;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanterns.services.SetupService;
import ca.concordia.lanterns.services.impl.ActivePlayerService;
import ca.concordia.lanterns.services.impl.DefaultSetupService;
import ca.concordia.lanternsentities.Game;

@Path("/game")
public class GameResource {
	// FIXME - Use GamePoolService

	private SetupService setupService = DefaultSetupService.getInstance();
	private PlayerService playerService = ActivePlayerService.getInstance();

	/**
	 * POST endpoint to allow creating a game based on a list of users. Query parameters "p1" and "p2" are mandatory.
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @return	The newly created game.
	 */
	@POST
	public Response createGame(@QueryParam("p1") final String p1, @QueryParam("p2") final String p2,
			@QueryParam("p3") final String p3, @QueryParam("p4") final String p4) {
		validateParamNotNull(new String[]{"p1", "p2"}, new Object[]{p1, p2});
		String[] playerNames = buildPlayerNames(p1, p2, p3, p4);
		Game game = setupService.createGame(playerNames);

		return Response.status(Response.Status.OK.getStatusCode()).entity(game).build();
	}
	
	private String[] buildPlayerNames(final String... params) {
		ArrayList<String> names =  new ArrayList<String>();
		for (String param : params) {
			if (param != null) {
				names.add(param);
			}
		}
		return names.toArray(new String[0]);
	}

	
	/**
	 * PUT endpoint to handle event of exchangeLanternCard.
	 * @param gameId	The game in which the event is happening.
	 * @param playerId	The current player.
	 * @param giveCard	The card the player is providing.
	 * @param receiveCard	The card the player desires to receive.
	 * @return
	 */
	@PUT
	@Path("{gameId}")
	public Response exchangeLanternCard(@PathParam("gameId") final String gameId, @QueryParam("playerId") final Integer playerId,
			@QueryParam("giveCard") final String giveCard, @QueryParam("receiveCard") final String receiveCard) {
		validateParamNotNull(new String[]{"playerId", "giveCard", "receiveCard"}, new Object[]{playerId, giveCard, receiveCard});
		// exchangeLanternCard(Game game, int id, Colour giveCard, Colour receiveCard)
		// TODO playerService.exchangeLanternCard(game, id, giveCard, receiveCard);
		return Response.status(Response.Status.OK.getStatusCode()).build(); // FIXME
	}
	
	private void validateParamNotNull(String[] paramNames, Object[] params) {
		for (int i = 0; i < params.length; i++) {
			if (params[i] == null) {
				throw new IllegalArgumentException("Mandatory parameter [" + paramNames[i] + "] cannot be null.");
			}
		}
	}
}
