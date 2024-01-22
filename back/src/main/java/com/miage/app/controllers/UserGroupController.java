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

import com.miage.app.dtos.ContactDTO;
import com.miage.app.dtos.ContactShortDTO;
import com.miage.app.dtos.UserGroupDTO;
import com.miage.app.dtos.UserGroupShortDTO;
import com.miage.app.entities.Contact;
import com.miage.app.services.UserGroupService;
import javax.ws.rs.core.MediaType;

@Path("/usergroups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserGroupController {

    private UserGroupService userGroupService = new UserGroupService();

    @GET
    public List<UserGroupShortDTO> getAll() {
        return userGroupService.getAllUserGroups();
    }

    @GET
    @Path("/{groupId}")
    public Response get(@PathParam("groupId") Long groupId) {
        UserGroupDTO group = userGroupService.getUserGroup(groupId);
        if (group != null) {
            return Response.ok(group).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response add(UserGroupDTO group) {
        UserGroupDTO newGroup = userGroupService.addUserGroup(group);
        return Response.status(Response.Status.CREATED).entity(newGroup).build();
    }

    @POST
    @Path("/{groupId}/add")
    public Response addContact(@PathParam("groupId") Long groupId, List<Contact> contacts) {
        UserGroupDTO newGroup = userGroupService.addContactsToGroup(groupId, contacts);
        return Response.status(Response.Status.CREATED).entity(newGroup).build();
    }

    @GET
    @Path("/{groupId}/get")
    public Response getContactsInGroup(@PathParam("groupId") Long groupId) {
        List<ContactShortDTO> contacts = userGroupService.getContactsInGroup(groupId);
        if (contacts != null) {
            return Response.ok(contacts).build();
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
