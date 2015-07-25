package ca.concordia.lanterns.Controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception mapper to return nicely handled errors to clients.
 */
@Provider
public class LanternsExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
	}

}
