package ca.concordia.lanterns_slick2d.client;

import java.io.InputStream;
import java.util.Properties;

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
	private Properties configProps;
	
	private static final Client client = Client.create();

    public Properties getConfigProps() {
        return configProps;
    }

    public void setConfigProps() {

        configProps = new Properties();
        InputStream input = null;
        // FIXME - bad path - does not work always - also really bad to have it in another project
//        String configFilePath = System.getProperty("user.dir") + "\\..\\lanternsentities\\src\\main\\properties\\config.properties";
//
//        try {
//            input = new FileInputStream(configFilePath);
//            // load a properties file
//            configProps.load(input);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } finally {
//            if (input != null) {
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    private static class SingletonHolder {
		static final GameClient INSTANCE = new GameClient();
	}
	
	public static GameClient getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public Game createGame(final String[] playerNames) {
        setConfigProps();
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		for (int i = 0; i < playerNames.length; i++) {
			queryParams.add("p" + (i+1), playerNames[i]);
		}
		
		// FIXME - use that later, when configProps path is fixed (instead of hardcoded path)
		// ClientResponse response = doPost(configProps.getProperty("gameURL"), queryParams, null);
		ClientResponse response = doPost("http://localhost:8080/rest/game", queryParams, null);
		
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
