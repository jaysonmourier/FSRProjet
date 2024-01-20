package com.miage.app.controllers;

import com.miage.app.dtos.ContactDTO;
import com.miage.app.entities.Contact;
import com.miage.app.services.ContactService;

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
public class ContactController {

    private final ContactService contactService;

    public ContactController() {
        this.contactService = new ContactService();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok(contactService.getAllContacts()).build();
    }

    @GET @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@PathParam("id") Long id) {
        ContactDTO contact = contactService.getContact(id);
        if (contact == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Contact not found").build();
        }
        return Response.ok(contact).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response add(Contact contactEntity) {
        return Response.status(this.contactService.addContact(contactEntity) ? Response.Status.CREATED.getStatusCode() : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }

    @PUT @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, Contact contactEntity) {
        return Response.status(this.contactService.updateContact(id, contactEntity) ? Response.Status.OK.getStatusCode() : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }

    @DELETE @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) {
        return Response.status(this.contactService.deleteContact(id) ? Response.Status.OK.getStatusCode() : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }
}
