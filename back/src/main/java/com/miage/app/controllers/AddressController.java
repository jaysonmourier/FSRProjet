package com.miage.app.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.miage.app.dtos.AddressDTO;
import com.miage.app.services.AddressService;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressController {
    private AddressService addressService = new AddressService();

    @GET
    public Response getAllAddresses() {
        return Response.ok(addressService.getAllAddresses()).build();
    }

    @GET
    @Path("/{id}")
    public Response getAddress(@PathParam("id") Long id) {
        AddressDTO address = addressService.getAddress(id);
        if (address != null) {
            return Response.ok(address).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addAddress(AddressDTO addressDTO) {
        AddressDTO newAddressDTO = addressService.addAddress(addressDTO);
        return Response.status(Response.Status.CREATED).entity(newAddressDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePhoneNumber(@PathParam("id") Long id) {
        addressService.deleteAddress(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}