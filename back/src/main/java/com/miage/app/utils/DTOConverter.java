package com.miage.app.utils;

import java.util.Set;
import java.util.stream.Collectors;

import com.miage.app.dtos.AddressDTO;
import com.miage.app.dtos.ContactDTO;
import com.miage.app.dtos.ContactShortDTO;
import com.miage.app.dtos.PhoneNumberDTO;
import com.miage.app.dtos.UserGroupDTO;
import com.miage.app.dtos.UserGroupShortDTO;
import com.miage.app.entities.Address;
import com.miage.app.entities.Contact;
import com.miage.app.entities.PhoneNumber;
import com.miage.app.entities.UserGroup;

public class DTOConverter {
    public static PhoneNumberDTO convertPhoneNumberToDTO(PhoneNumber phoneNumber) {
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setId(phoneNumber.getId());
        phoneNumberDTO.setPhoneKind(phoneNumber.getPhoneKind());
        phoneNumberDTO.setPhoneNumber(phoneNumber.getPhoneNumber());
        return phoneNumberDTO;
    }

    public static UserGroupDTO convertUserGroupToDTO(UserGroup userGroup) {
        UserGroupDTO userGroupDTO = new UserGroupDTO();
        userGroupDTO.setGroupId(userGroup.getGroupId());
        userGroupDTO.setGroupName(userGroup.getName());
        return userGroupDTO;
    }

    public static Contact convertToEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setFirstname(contactDTO.getFirstname());
        contact.setLastname(contactDTO.getLastname());
        contact.setEmail(contactDTO.getEmail());

        if (contactDTO.getAddress() != null) {
            contact.setAddress(convertAddressToEntity(contactDTO.getAddress()));
        }

        if (contactDTO.getPhoneNumbers() != null) {
            Set<PhoneNumber> phoneNumbers = contactDTO.getPhoneNumbers().stream()
                    .map(DTOConverter::convertPhoneNumberToEntity)
                    .collect(Collectors.toSet());

            for (PhoneNumber phoneNumber : phoneNumbers) {
                phoneNumber.setContact(contact);
            }

            contact.setPhoneNumbers(phoneNumbers);
        }

        if (contactDTO.getGroups() != null) {
            Set<UserGroup> groups = contactDTO.getGroups().stream()
                    .map(DTOConverter::convertUserGroupToEntity)
                    .collect(Collectors.toSet());
            contact.setGroups(groups);
        }

        return contact;
    }

    public static Address convertAddressToEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setZip(addressDTO.getZip());
        address.setCountry(addressDTO.getCountry());
        return address;
    }

    public static PhoneNumber convertPhoneNumberToEntity(PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setId(phoneNumberDTO.getId());
        phoneNumber.setPhoneKind(phoneNumberDTO.getPhoneKind());
        phoneNumber.setPhoneNumber(phoneNumberDTO.getPhoneNumber());
        return phoneNumber;
    }

    public static UserGroup convertUserGroupToEntity(UserGroupDTO userGroupDTO) {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(userGroupDTO.getGroupId());
        userGroup.setName(userGroupDTO.getGroupName());
        return userGroup;
    }

    public static ContactDTO convertContactToDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstname(contact.getFirstname());
        contactDTO.setLastname(contact.getLastname());
        contactDTO.setEmail(contact.getEmail());

        if (contact.getAddress() != null) {
            contactDTO.setAddress(convertAddressToDTO(contact.getAddress()));
        }

        if (contact.getPhoneNumbers() != null) {
            Set<PhoneNumberDTO> phoneNumberDTOs = contact.getPhoneNumbers().stream()
                    .map(DTOConverter::convertPhoneNumberToDTO)
                    .collect(Collectors.toSet());
            contactDTO.setPhoneNumbers(phoneNumberDTOs);
        }

        if (contact.getGroups() != null) {
            Set<UserGroupDTO> userGroupDTOs = contact.getGroups().stream()
                    .map(DTOConverter::convertUserGroupToDTO)
                    .collect(Collectors.toSet());
            contactDTO.setGroups(userGroupDTOs);
        }

        return contactDTO;
    }

    public static Contact convertContactShortDTOToContact(ContactShortDTO contactShortDTO) {
        Contact contact = new Contact();
        contact.setId(contactShortDTO.getId());
        contact.setFirstname(contactShortDTO.getFirstname());
        contact.setLastname(contactShortDTO.getLastname());
        contact.setEmail(contactShortDTO.getEmail());

        if (contactShortDTO.getAddress() != null) {
            contact.setAddress(convertAddressToEntity(contactShortDTO.getAddress()));
        }

        if (contact.getPhoneNumbers() != null) {
            Set<PhoneNumber> phoneNumbers = contactShortDTO.getPhoneNumbers().stream()
                    .map(DTOConverter::convertPhoneNumberToEntity)
                    .collect(Collectors.toSet());
            contact.setPhoneNumbers(phoneNumbers);
        }

        return contact;
    }

    public static ContactShortDTO convertContactToContactShortDTO(Contact contact) {
        ContactShortDTO contactShort = new ContactShortDTO();
        contactShort.setId(contact.getId());
        contactShort.setFirstname(contact.getFirstname());
        contactShort.setLastname(contact.getLastname());
        contactShort.setEmail(contact.getEmail());

        if (contact.getAddress() != null) {
            contactShort.setAddress(convertAddressToDTO(contact.getAddress()));
        }

        if (contact.getPhoneNumbers() != null) {
            Set<PhoneNumber> phoneNumbers = contact.getPhoneNumbers();
            contact.setPhoneNumbers(phoneNumbers);
        }

        return contactShort;
    }

    public static AddressDTO convertAddressToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(address.getStreet());
        addressDTO.setCity(address.getCity());
        addressDTO.setZip(address.getZip());
        addressDTO.setCountry(address.getCountry());
        return addressDTO;
    }

    public static UserGroupShortDTO convertUserGroupToShortDTO(UserGroup userGroup) {
        UserGroupShortDTO userGroupShortDTO = new UserGroupShortDTO();
        userGroupShortDTO.setGroupId(userGroup.getGroupId());
        userGroupShortDTO.setGroupName(userGroup.getName());
        userGroupShortDTO.setLength(userGroup.getContacts().size());
        return userGroupShortDTO;
    }

    public static UserGroupDTO convertUserGroupToUserGroupDTO(UserGroup group) {
        UserGroupDTO groupDTO = new UserGroupDTO();
        groupDTO.setGroupId(group.getGroupId());
        groupDTO.setGroupName(group.getName());
        groupDTO.setLength(group.getContacts().size());
        System.out.println("groupDTO: " + groupDTO);
        return groupDTO;
    }
}
