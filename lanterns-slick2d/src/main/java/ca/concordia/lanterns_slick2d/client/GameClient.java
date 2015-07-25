package ca.concordia.lanterns_slick2d.client;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;


// FIXME - add interface
public class GameClient {
	
	public Game getGame() {
		// FIXME - connect to server and get real game. By now, just creating a stub.
		Game game = new Game();
		Player p1 = new Player();
		p1.init("p1", 0);
		Player p2 = new Player();
		p2.init("p2", 1);
		Player p3 = new Player();
		p3.init("p3", 0);
		Player p4 = new Player();
		p4.init("p4", 1);
		Player[] players = new Player[] {p1, p2, p3, p4};
		String[] playerNames = {p1.getName(), p2.getName(), p3.getName(), p4.getName()};

		game.init(playerNames);
		game.setPlayers(players);

		return game;
	}

	// FIXME - just adding this client now to test. Need to break it into classes/interfaces.
	public static void main(String[] args) {
		try {
			System.out.println("Before anything, run 'mvn jetty:run' from 'lanterns' project to start the server." );

			Client client = Client.create();

			WebResource webResource = client.resource("http://localhost:8080/rest/game");
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
			queryParams.add("p1", "one");
			queryParams.add("p2", "two");
			queryParams.add("p3", "three");
			queryParams.add("p4", "four");

			// String input = "{\"singer\":\"Metallica\",\"title\":\"Fade To Black\"}";
			// ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
			ClientResponse response = webResource.queryParams(queryParams).post(ClientResponse.class);

			if (response.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			Game output = response.getEntity(Game.class);
			System.out.println(output);
			if (output != null) {
				Player[] players = output.getPlayers();
				System.out.println(players);
				if (players != null) {
					for (Player player : players) {
						System.out.println(player.getName());
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
