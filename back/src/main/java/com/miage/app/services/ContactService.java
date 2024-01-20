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
        return daoContact.add(contact);
    }

    public ContactDTO getContact(Long id) {
        Contact contact = daoContact.get(id);
        return contact == null ? null : convertToContactDTO(contact);
    }

    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = daoContact.getAll();
        return contacts.stream().map(this::convertToContactDTO).collect(Collectors.toList());
    }

    public ContactDTO convertToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstname(contact.getFirstname());
        contactDTO.setLastname(contact.getLastname());
        contactDTO.setEmail(contact.getEmail());
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

    public boolean updateContact(Long id, Contact contact) {
        Contact contactToUpdate = daoContact.get(id);
        if (contactToUpdate == null) {
            return false;
        }
        return daoContact.update(contact);
    }

    public boolean deleteContact(Long id) {
        return daoContact.delete(id);
    }
}
