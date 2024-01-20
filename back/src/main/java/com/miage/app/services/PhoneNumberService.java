package com.miage.app.services;

import java.util.List;
import java.util.stream.Collectors;

import com.miage.app.daos.DAOPhoneNumber;
import com.miage.app.dtos.ContactDTO;
import com.miage.app.dtos.PhoneNumberDTO;
import com.miage.app.entities.Contact;
import com.miage.app.entities.PhoneNumber;

public class PhoneNumberService {
    private final DAOPhoneNumber daoPhoneNumber;

    public PhoneNumberService() {
        this.daoPhoneNumber = new DAOPhoneNumber();
    }

    public boolean addPhoneNumber(PhoneNumber phoneNumber) {
        System.out.println("\n\n");
        System.out.println("PHONE NUMBER SERVICE");
        System.out.println(phoneNumber);
        System.out.println("\n\n");
        return daoPhoneNumber.addPhoneNumber(phoneNumber);
    }

    public Object getAllPhone() {
        List<PhoneNumber> phones = daoPhoneNumber.getAllPhoneNumbers();
        return phones.stream().map(this::convertToPhoneDTO).collect(Collectors.toList());
    }

    public PhoneNumberDTO getPhone(Long id) {
        PhoneNumber phone = daoPhoneNumber.getPhoneNumber(id);
        return phone == null ? null : convertToPhoneDTO(phone);
    }

    public PhoneNumberDTO convertToPhoneDTO(PhoneNumber phone) {
        PhoneNumberDTO phoneDTO = new PhoneNumberDTO();
        phoneDTO.setId(phone.getId());
        phoneDTO.setPhoneKind(phone.getPhoneKind());
        phoneDTO.setPhoneNumber(phone.getPhoneNumber());
        phoneDTO.setContact(contactToContactDTO(phone.getContact()));
        return phoneDTO;
    }

    public ContactDTO contactToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstname(contact.getFirstname());
        contactDTO.setLastname(contact.getLastname());
        return contactDTO;
    }

    public boolean updatePhone(Long id, PhoneNumber phoneNumber) {
        return daoPhoneNumber.updatePhone(id, phoneNumber);
    }
}