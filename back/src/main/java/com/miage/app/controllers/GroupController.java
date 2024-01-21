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

import com.miage.app.dtos.UserGroupDTO;
import com.miage.app.entities.UserGroup;
import com.miage.app.services.GroupService;

@Path("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController() {
        this.groupService = new GroupService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGroups() {
        return Response.ok(groupService.getAllGroups()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroup(@PathParam("id") Long id) {
        UserGroupDTO group = groupService.getGroup(id);
        if (group == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Group not found").build();
        }
        return Response.ok(group).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGroup(UserGroup groupEntity) {
        return Response.status(groupService.addGroup(groupEntity) ? Response.Status.CREATED.getStatusCode()
                : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGroup(@PathParam("id") Long id, UserGroup groupEntity) {
        return Response.status(groupService.updateGroup(id, groupEntity) ? Response.Status.OK.getStatusCode()
                : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGroup(@PathParam("id") Long id) {
        return Response.status(groupService.deleteGroup(id) ? Response.Status.OK.getStatusCode()
                : Response.Status.BAD_REQUEST.getStatusCode()).build();
    }
}