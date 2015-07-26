package ca.concordia.lanterns_slick2d.client;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import ca.concordia.lanternsentities.Game;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

// FIXME - add interface
public class GameClient {

	// TODO - move all endpoint related constants to a common place (probably a properties file in entities project)
	public static final String HOST = "localhost";
	public static final String PORT = "8080";
	public static final String REST_URL = "http://" + HOST + ":" + PORT + "/rest";
	public static final String GAME_URL = REST_URL + "/game";
	
	private static final Client client = Client.create();
	
	private static class SingletonHolder {
		static final GameClient INSTANCE = new GameClient();
	}
	
	public static GameClient getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public Game createGame() {
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("p1", "one");
		queryParams.add("p2", "two");
		queryParams.add("p3", "three");
		queryParams.add("p4", "four");
		
		ClientResponse response = doPost(GAME_URL, queryParams, null);
		
		Game output = response.getEntity(Game.class);
		
		return output;
	}
	
	private ClientResponse doPost(String resource, MultivaluedMap<String, String> queryParams, Object requestEntity) {
		ClientResponse response = null;
		try {
			WebResource webResource = client.resource(resource);
			// check if there is a body to send
			if (requestEntity == null) {
				response = webResource.queryParams(queryParams).post(ClientResponse.class);
			} else {
				response = webResource.queryParams(queryParams).post(ClientResponse.class, requestEntity);
			}
	
			if (response.getStatus() != Response.Status.OK.getStatusCode()) {
				// FIXME - notify on UI
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (ClientHandlerException e) {
			// FIXME - notify on UI
			System.out.println("Unnable to connect to [" + resource + "]");
		}
		
		return response;
	}

}
