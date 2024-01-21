package com.miage.app.services;

import java.util.List;
import java.util.stream.Collectors;

import com.miage.app.daos.DAOUserGroup;
import com.miage.app.dtos.ContactDTO;
import com.miage.app.dtos.UserGroupDTO;
import com.miage.app.dtos.PhoneNumberDTO;
import com.miage.app.entities.Contact;
import com.miage.app.entities.UserGroup;
import com.miage.app.entities.PhoneNumber;

public class GroupService {
    private final DAOUserGroup daoGroup;

    public GroupService() {
        this.daoGroup = new DAOUserGroup();
    }

    public boolean addGroup(UserGroup group) {
        return daoGroup.add(group);
    }

    public UserGroupDTO getGroup(Long id) {
        UserGroup group = daoGroup.get(id);
        return group == null ? null : convertToGroupDTO(group);
    }

    public List<UserGroupDTO> getAllGroups() {
        List<UserGroup> groups = daoGroup.getAll();
        return groups.stream().map(this::convertToGroupDTO).collect(Collectors.toList());
    }

    public UserGroupDTO convertToGroupDTO(UserGroup group) {
        UserGroupDTO groupDTO = new UserGroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setContacts(group.getContacts().stream().map(this::convertToContactDTO).collect(Collectors.toList()));
        return groupDTO;
    }

    public ContactDTO convertToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstname(contact.getFirstname());
        contactDTO.setLastname(contact.getLastname());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setAddress(contact.getAddress());
        contactDTO.setPhoneNumbers(
                contact.getPhoneNumbers().stream().map(this::phoneNumberToPhoneNumberDTO).collect(Collectors.toList()));
        return contactDTO;
    }

    public PhoneNumberDTO phoneNumberToPhoneNumberDTO(PhoneNumber phoneNumber) {
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setId(phoneNumber.getId());
        phoneNumberDTO.setPhoneKind(phoneNumber.getPhoneKind());
        phoneNumberDTO.setPhoneNumber(phoneNumber.getPhoneNumber());
        return phoneNumberDTO;
    }

    public boolean updateGroup(Long id, UserGroup group) {
        return daoGroup.update(group);
    }

    public boolean deleteGroup(Long id) {
        return daoGroup.delete(id);
    }
}
