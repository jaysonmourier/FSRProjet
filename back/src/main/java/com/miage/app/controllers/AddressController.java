package com.miage.app.controllers;

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

import com.miage.app.entities.Address;
import com.miage.app.services.AddressService;

@Path("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController() {
        this.addressService = new AddressService();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok(addressService.getAll()).build();
    }

    @GET @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response get(@PathParam("id") Long id) {
        Address address = addressService.get(id);
        if (address == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Address not found").build();
        }
        return Response.ok(address).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response add(Address addressEntity) {
        return Response.status(this.addressService.add(addressEntity) ? Response.Status.CREATED.getStatusCode() : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, Address addressEntity) {
        return Response.status(this.addressService.update(id, addressEntity) ? Response.Status.OK.getStatusCode() : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id) {
        return Response.status(this.addressService.delete(id) ? Response.Status.OK.getStatusCode() : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }
}
