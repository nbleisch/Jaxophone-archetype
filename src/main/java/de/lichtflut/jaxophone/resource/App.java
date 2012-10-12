package de.lichtflut.jaxophone.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Hello world!
 */
@Path("app")
public class App 
{
	
	@GET
	@Path("welcome")
	@Produces({MediaType.TEXT_PLAIN})
	public String getWelcomeMessage(){
		return "Hello world!";
	}
	
}
