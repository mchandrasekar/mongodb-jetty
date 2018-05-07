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

import mongodb.Models.Person;
import net.vz.mongodb.jackson.JacksonDBCollection;
import net.vz.mongodb.jackson.WriteResult;

@Path("person")
public class PersonResource {
	@Context ServletContext context;
	
	private DB getDb() {
		return (DB) context.getAttribute("mongodbObj");
	}
	
	private JacksonDBCollection<Person, String> getJacksonDBCollection() {
        return JacksonDBCollection.wrap(getDb().getCollection(Person.class.getSimpleName().toLowerCase()), Person.class, String.class);
    }
	
	@Path("persons")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> listPersons() {
        return getJacksonDBCollection().find().toArray();
    }
	
	@Path("person")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Person addPerson(Person person) {
        WriteResult<Person, String> result = getJacksonDBCollection().insert(person);
        return result.getSavedObject();
    }
}
