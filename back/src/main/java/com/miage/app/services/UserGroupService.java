package com.miage.app.services;

import java.util.List;
import java.util.stream.Collectors;

import com.miage.app.config.EntityManagerConfig;
import com.miage.app.daos.UserGroupDAO;
import com.miage.app.dtos.ContactShortDTO;
import com.miage.app.dtos.UserGroupDTO;
import com.miage.app.dtos.UserGroupShortDTO;
import com.miage.app.entities.Contact;
import com.miage.app.entities.UserGroup;
import com.miage.app.utils.DTOConverter;

public class UserGroupService {

    private UserGroupDAO userGroupDAO = new UserGroupDAO(EntityManagerConfig.getEmf().createEntityManager());

    public List<UserGroupShortDTO> getAllUserGroups() {
        List<UserGroup> groups = userGroupDAO.findAll();
        return groups.stream().map(DTOConverter::convertUserGroupToShortDTO).collect(Collectors.toList());
    }

    public UserGroupDTO getUserGroup(Long groupId) {
        UserGroup group = userGroupDAO.find(groupId);
        return group != null ? DTOConverter.convertUserGroupToUserGroupDTO(group) : null;
    }

    public UserGroupDTO addUserGroup(UserGroupDTO groupDTO) {
        UserGroup group = convertToEntity(groupDTO);
        userGroupDAO.save(group);
        return DTOConverter.convertUserGroupToUserGroupDTO(group);
    }

    public void deleteUserGroup(Long groupId) {
        UserGroup group = userGroupDAO.find(groupId);
        if (group != null) {
            userGroupDAO.delete(group);
        }
    }

    private UserGroup convertToEntity(UserGroupDTO groupDTO) {
        UserGroup group = new UserGroup();
        group.setGroupId(groupDTO.getGroupId());
        group.setName(groupDTO.getGroupName());
        return group;
    }

    public Contact convertContactShortDTOToContact(ContactShortDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setFirstname(contactDTO.getFirstname());
        contact.setLastname(contactDTO.getLastname());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhoneNumbers(contactDTO.getPhoneNumbers().stream()
                .map(DTOConverter::convertPhoneNumberToEntity)
                .collect(Collectors.toSet()));
        return contact;
    }

    public UserGroupDTO addContactsToGroup(Long groupId, List<Contact> contacts) {
        UserGroup group = userGroupDAO.find(groupId);
        if (group != null) {
            ContactService contactService = new ContactService();
            // group.setContacts(contacts.stream().collect(Collectors.toSet()));
            for (Contact contact : contacts) {
                contact = contactService.find(contact.getId());
                if (contact != null) {
                    group.addContact(contact);
                }
            }
            userGroupDAO.save(group);
            return DTOConverter.convertUserGroupToUserGroupDTO(group);
        }
        return null;
    }

    public List<ContactShortDTO> getContactsInGroup(Long groupId) {
        UserGroup group = userGroupDAO.find(groupId);
        System.out.println("GROUP: " + group);
        if (group != null) {
            return group.getContacts().stream()
                    .map(DTOConverter::convertContactToContactShortDTO)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public boolean removeContactFromGroup(Long groupId, Long contactId) {
        return userGroupDAO.removeContactFromGroup(groupId, contactId);
    }
}
