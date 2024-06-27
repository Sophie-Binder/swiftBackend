package org.example.person;

import org.eclipse.microprofile.jwt.JsonWebToken;
import com.google.firebase.auth.FirebaseToken;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/persons")
public class PersonResource {
    @Inject
    PersonRepository personRepository;

    @Inject
    JsonWebToken jwt;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Person getPersonById(@PathParam("id") int id){
        return personRepository.getById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Person getPersonByEmail(@QueryParam("email") String email){
        return personRepository.getByEmail(email);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public List<Person> getAllPersons(){
        return personRepository.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createPerson() {
        //FirebaseToken firebaseUser = (FirebaseToken) requestContext.getProperty("firebaseUser");

           personRepository.addPerson( jwt.getSubject(), jwt.getName(), jwt.getClaim("email"));

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/token")
    public Person getPersonByToken(@Context ContainerRequestContext requestContext){
        FirebaseToken firebaseUser = (FirebaseToken) requestContext.getProperty("firebaseUser");
        if (firebaseUser != null) {
            return personRepository.getByUuid(firebaseUser.getUid());
        }
        return null;
    }


}
