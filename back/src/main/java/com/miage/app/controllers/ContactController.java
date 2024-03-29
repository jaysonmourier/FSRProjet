package com.miage.app.controllers;

import com.miage.app.dtos.ContactDTO;
import com.miage.app.entities.Contact;
import com.miage.app.services.ContactService;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactController {

    private ContactService contactService = new ContactService();

    @GET
    public List<ContactDTO> getAll() {
        return contactService.getAllContacts();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        ContactDTO contact = contactService.getContact(id);
        if (contact != null) {
            return Response.ok(contact).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response add(Contact contact) {
        System.out.println("[ADD] " + contact);
        Contact newContact = contactService.addContact(contact);
        return Response.status(Response.Status.CREATED).entity(newContact).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ContactDTO contact) {
        ContactDTO updatedContact = contactService.updateContact(id, contact);
        if (updatedContact != null) {
            return Response.ok(updatedContact).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        contactService.deleteContact(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("{id}/phone/{pid}")
    public Response deletePhone(@PathParam("id") Long id, @PathParam("pid") Long pid){
        contactService.deletePhone(id, pid);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
