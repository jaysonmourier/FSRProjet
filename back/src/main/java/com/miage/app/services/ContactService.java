package com.miage.app.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.miage.app.config.EntityManagerConfig;
import com.miage.app.daos.ContactDAO;
import com.miage.app.dtos.AddressDTO;
import com.miage.app.dtos.ContactDTO;
import com.miage.app.dtos.PhoneNumberDTO;
import com.miage.app.dtos.UserGroupDTO;
import com.miage.app.entities.Address;
import com.miage.app.entities.Contact;
import com.miage.app.entities.PhoneNumber;
import com.miage.app.utils.DTOConverter;

public class ContactService {

    private ContactDAO contactDAO = new ContactDAO(EntityManagerConfig.getEmf().createEntityManager());

    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = contactDAO.findAll();
        return contacts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Contact find(Long id) {
        return contactDAO.find(id);
    }

    public ContactDTO getContact(Long id) {
        Contact contact = contactDAO.find(id);
        return contact != null ? convertToDTO(contact) : null;
    }

    public Contact addContact(Contact contact) {
        contactDAO.save(contact);
        return contact;
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        Contact contact = contactDAO.find(id);
        System.out.println("Contact: " + contact);
        if (contact != null) {
            updateEntityWithDTO(contact, contactDTO);
            contactDAO.save(contact);
            return convertToDTO(contact);
        }
        return null;
    }

    public void deleteContact(Long id) {
        Contact contact = contactDAO.find(id);
        if (contact != null) {
            contactDAO.delete(contact);
        }
    }

    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstname(contact.getFirstname());
        contactDTO.setLastname(contact.getLastname());
        contactDTO.setEmail(contact.getEmail());

        if (contact.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(contact.getAddress().getStreet());
            addressDTO.setCity(contact.getAddress().getCity());
            addressDTO.setZip(contact.getAddress().getZip());
            addressDTO.setCountry(contact.getAddress().getCountry());
            contactDTO.setAddress(addressDTO);
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

    private void updateEntityWithDTO(Contact contact, ContactDTO contactDTO) {
        contact.setFirstname(contactDTO.getFirstname());
        contact.setLastname(contactDTO.getLastname());
        contact.setEmail(contactDTO.getEmail());

        if (contactDTO.getAddress() != null) {
            if (contact.getAddress() == null) {
                contact.setAddress(new Address());
            }
            updateAddressEntityWithDTO(contact.getAddress(), contactDTO.getAddress());
        }

        if (contactDTO.getPhoneNumbers() != null) {
            updatePhoneNumbers(contact, contactDTO.getPhoneNumbers());
        }

        if (contactDTO.getGroups() != null) {
            contact.setGroups(contactDTO.getGroups().stream()
                    .map(DTOConverter::convertUserGroupToEntity)
                    .collect(Collectors.toSet()));
        }
    }

    private void updateAddressEntityWithDTO(Address address, AddressDTO addressDTO) {
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setZip(addressDTO.getZip());
        address.setCountry(addressDTO.getCountry());
    }

    private void updatePhoneNumbers(Contact contact, Set<PhoneNumberDTO> phoneNumberDTOs) {
        Set<PhoneNumber> updatedPhoneNumbers = new HashSet<>();

        for (PhoneNumberDTO phoneNumberDTO : phoneNumberDTOs) {
            PhoneNumber phoneNumber = contact.getPhoneNumbers().stream()
                    .filter(p -> p.getId().equals(phoneNumberDTO.getId()))
                    .findFirst()
                    .orElse(new PhoneNumber());

            phoneNumber.setPhoneKind(phoneNumberDTO.getPhoneKind());
            phoneNumber.setPhoneNumber(phoneNumberDTO.getPhoneNumber());
            phoneNumber.setContact(contact);

            updatedPhoneNumbers.add(phoneNumber);
        }

        contact.setPhoneNumbers(updatedPhoneNumbers);
    }
}