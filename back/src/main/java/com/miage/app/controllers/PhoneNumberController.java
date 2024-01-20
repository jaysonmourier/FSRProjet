package com.miage.app.controllers;

import com.miage.app.dtos.PhoneNumberDTO;
import com.miage.app.entities.Contact;
import com.miage.app.entities.PhoneNumber;
import com.miage.app.services.ContactService;
import com.miage.app.services.PhoneNumberService;

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

@Path("/phone")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController() {
        this.phoneNumberService = new PhoneNumberService();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok(phoneNumberService.getAllPhone()).build();
    }

    @GET @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@PathParam("id") Long id) {
        PhoneNumberDTO phone = phoneNumberService.getPhone(id);
        if (phone == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Phone not found").build();
        }
        return Response.ok(phone).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response add(PhoneNumber phoneNumberEntity) {
        return Response.status(this.phoneNumberService.addPhoneNumber(phoneNumberEntity) ? Response.Status.CREATED.getStatusCode() : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }

    @PUT @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, PhoneNumber phoneNumber) {
        return Response.status(this.phoneNumberService.updatePhone(id, phoneNumber) ? Response.Status.OK.getStatusCode() : Response.Status.NOT_FOUND.getStatusCode()).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) {
        return Response.status(this.phoneNumberService.deletePhone(id) ? Response.Status.OK.getStatusCode() : Response.Status.NOT_FOUND.getStatusCode()).build();
    }
}
