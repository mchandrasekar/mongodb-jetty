package mongodb.Resources;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.mongodb.DB;

import mongodb.Models.Contact;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;

@Path("contact")
public class ContactResource {
	@Context ServletContext context;
	
	private DB getDb() {
		return (DB) context.getAttribute("mongodbObj");
	}
	
	private JacksonDBCollection<Contact, String> getJacksonDBCollection() {
        return JacksonDBCollection.wrap(getDb().getCollection(Contact.class.getSimpleName().toLowerCase()), Contact.class, String.class);
    }
	
	@Path("contacts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contact> listContacts() {
        return getJacksonDBCollection().find().toArray();
    }
	
	@Path("contact")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Contact addBar(Contact Contact) {
        WriteResult<Contact, String> result = getJacksonDBCollection().insert(Contact);
        return result.getSavedObject();
    }
}
