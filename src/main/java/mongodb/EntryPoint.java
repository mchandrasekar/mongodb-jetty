package mongodb;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("entry-point")
public class EntryPoint {
	@Context ServletContext context;

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test() {
    	System.out.println(context.getAttribute("test"));
        return "Test";
    }
}