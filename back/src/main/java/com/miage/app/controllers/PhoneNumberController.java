package com.miage.app.controllers;

import com.miage.app.entities.Contact;
import com.miage.app.entities.PhoneNumber;
import com.miage.app.services.ContactService;
import com.miage.app.services.PhoneNumberService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/phone")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController() {
        this.phoneNumberService = new PhoneNumberService();
    }

    // @GET
    // @Produces({MediaType.APPLICATION_JSON})
    // public Response getAll() {
    //     return Response.ok(phoneNumberService.getAllPhone()).build();
    // }

    // @GET @Path("/{id}")
    // @Produces({MediaType.APPLICATION_JSON})
    // public Response get(@PathParam("id") Long id) {
    //     Contact contact = phoneNumberService.getContact(id);
    //     if (contact == null) {
    //         return Response.status(Response.Status.NOT_FOUND).entity("Contact not found").build();
    //     }
    //     return Response.ok(contact).build();
    // }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response add(PhoneNumber phoneNumberEntity) {
        return Response.status(this.phoneNumberService.addPhoneNumber(phoneNumberEntity) ? Response.Status.CREATED.getStatusCode() : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }
}
