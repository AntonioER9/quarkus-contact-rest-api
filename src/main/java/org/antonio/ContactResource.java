package org.antonio;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.List;

@Path("/api/contacts")
@Produces("application/json")
@Consumes
public class ContactResource {

    @Inject //Dependency Injection
    EntityManager entityManager;

    // Implement CRUD operations here (Create, Read, Update, Delete)
    @GET
    public List<Contact> getAllContacts() {
        return entityManager.createQuery("from Contact", Contact.class).getResultList();
    }
}
