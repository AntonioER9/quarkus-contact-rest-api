package org.antonio;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/contact")
@Produces("application/json")
@Consumes
public class ContactResource {

    @Inject //Dependency Injection
    EntityManager entityManager;

    @POST
    @Transactional
    public Response createContact(Contact contact) {
        entityManager.persist(contact);
        return Response.status(Response.Status.CREATED).entity(contact).build();
    }

    @GET
    public List<Contact> getAllContacts() {
        return entityManager.createQuery("from Contact", Contact.class).getResultList();
    }

    @GET
    @Path("/{id}")
    public Response getContactById(@PathParam("id") Long id) {
        Contact contact = entityManager.find(Contact.class, id);
        if (contact == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(contact).build();
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateContact(@PathParam("id") Long id, Contact updatedContact) {
        Contact contact = entityManager.find(Contact.class, id);
        if (contact == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        contact.setName(updatedContact.getName());
        contact.setEmail(updatedContact.getEmail());
        entityManager.merge(contact);
        return Response.ok(contact).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteContact(@PathParam("id") Long id) {
        Contact contact = entityManager.find(Contact.class, id);
        if (contact == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entityManager.remove(contact);
        return Response.noContent().build();
    }

    @PUT
    @Path("/email/{email}")
    @Transactional
    public Response updateContactByEmail(@PathParam("email") String email, Contact updatedContact) {
        List<Contact> contacts = entityManager.createQuery("from Contact where email = :email", Contact.class)
                .setParameter("email", email)
                .getResultList();
        if (contacts.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Contact contact = contacts.get(0);
        contact.setName(updatedContact.getName());
        contact.setEmail(updatedContact.getEmail());
        entityManager.merge(contact);
        return Response.ok(contact).build();
    }

    @GET
    @Path("/email/{email}")
    public Response getContactByEmail(@PathParam("email") String email) {
        Contact contact = entityManager.createQuery("from Contact where email = :email", Contact.class)
                .setParameter("email", email)
                .getSingleResult();
        if (contact == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(contact).build();
    }

}
