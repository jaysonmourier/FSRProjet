package com.miage.app.controllers;

import com.miage.app.dtos.PhoneNumberDTO;
import com.miage.app.services.PhoneNumberService;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/phoneNumbers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PhoneNumberController {
    private PhoneNumberService phoneNumberService = new PhoneNumberService();

    @GET
    public Response getAllPhoneNumbers() {
        return Response.ok(phoneNumberService.getAllPhoneNumbers()).build();
    }

    @GET
    @Path("/{id}")
    public Response getPhoneNumber(@PathParam("id") Long id) {
        PhoneNumberDTO phoneNumber = phoneNumberService.getPhoneNumber(id);
        if (phoneNumber != null) {
            return Response.ok(phoneNumber).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addPhoneNumber(PhoneNumberDTO phoneNumber) {
        PhoneNumberDTO newPhoneNumber = phoneNumberService.addPhoneNumber(phoneNumber);
        return Response.status(Response.Status.CREATED).entity(newPhoneNumber).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePhoneNumber(@PathParam("id") Long id) {
        phoneNumberService.deletePhoneNumber(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
