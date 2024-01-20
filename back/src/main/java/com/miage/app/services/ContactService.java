package com.miage.app.services;

import java.util.List;
import java.util.stream.Collectors;

import com.miage.app.daos.DAOContact;
import com.miage.app.dtos.ContactDTO;
import com.miage.app.dtos.PhoneNumberDTO;
import com.miage.app.entities.Contact;
import com.miage.app.entities.PhoneNumber;

public class ContactService {
    private final DAOContact daoContact;

    public ContactService() {
        this.daoContact = new DAOContact();
    }

    public boolean addContact(Contact contact) {
        return daoContact.addContact(contact);
    }

    public ContactDTO getContact(Long id) {
        Contact contact = daoContact.getContact(id);
       return contact == null ? null : convertToContactDTO(contact);
    }

    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = daoContact.getContacts();
        return contacts.stream().map(this::convertToContactDTO).collect(Collectors.toList());
    }

    public ContactDTO convertToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstname(contact.getFirstname());
        contactDTO.setLastname(contact.getLastname());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setPhoneNumbers(contact.getPhoneNumbers().stream().map(this::convertToPhoneNumberDTO).collect(Collectors.toList()));
        return contactDTO;
    }

    public PhoneNumberDTO convertToPhoneNumberDTO(PhoneNumber phoneNumber) {
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO();
        phoneNumberDTO.setId(phoneNumber.getId());
        phoneNumberDTO.setPhoneKind(phoneNumber.getPhoneKind());
        phoneNumberDTO.setPhoneNumber(phoneNumber.getPhoneNumber());
        return phoneNumberDTO;
    }

    public boolean updateContact(Long id, Contact contact) {
        Contact contactToUpdate = daoContact.getContact(id);
        if (contactToUpdate == null) {
            return false;
        }
        return daoContact.updateContact(contact);
    }

    public boolean deleteContact(Long id) {
        return daoContact.deleteContact(id);
    }
}