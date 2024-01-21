package com.miage.app.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.miage.app.dtos.UserGroupDTO;
import com.miage.app.services.UserGroupService;
import javax.ws.rs.core.MediaType;

@Path("/usergroups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserGroupController {

    private UserGroupService userGroupService = new UserGroupService();

    @GET
    public List<UserGroupDTO> getAllUserGroups() {
        return userGroupService.getAllUserGroups();
    }

    @GET
    @Path("/{groupId}")
    public Response getUserGroup(@PathParam("groupId") Long groupId) {
        UserGroupDTO group = userGroupService.getUserGroup(groupId);
        if (group != null) {
            return Response.ok(group).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addUserGroup(UserGroupDTO group) {
        UserGroupDTO newGroup = userGroupService.addUserGroup(group);
        return Response.status(Response.Status.CREATED).entity(newGroup).build();
    }

    @PUT
    @Path("/{groupId}")
    public Response updateUserGroup(@PathParam("groupId") Long groupId, UserGroupDTO group) {
        UserGroupDTO updatedGroup = userGroupService.updateUserGroup(groupId, group);
        if (updatedGroup != null) {
            return Response.ok(updatedGroup).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{groupId}")
    public Response deleteUserGroup(@PathParam("groupId") Long groupId) {
        userGroupService.deleteUserGroup(groupId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
