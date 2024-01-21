package com.miage.app.services;

import java.util.List;
import java.util.stream.Collectors;

import com.miage.app.config.EntityManagerConfig;
import com.miage.app.daos.PhoneNumberDAO;
import com.miage.app.dtos.PhoneNumberDTO;
import com.miage.app.entities.PhoneNumber;
import com.miage.app.utils.DTOConverter;

public class PhoneNumberService {
    private PhoneNumberDAO phoneNumberDAO = new PhoneNumberDAO(EntityManagerConfig.getEmf().createEntityManager());

    public List<PhoneNumberDTO> getAllPhoneNumbers() {
        List<PhoneNumber> phoneNumbers = phoneNumberDAO.findAll();
        return phoneNumbers.stream().map(DTOConverter::convertPhoneNumberToDTO).collect(Collectors.toList());
    }

    public PhoneNumberDTO getPhoneNumber(Long id) {
        PhoneNumber phoneNumber = phoneNumberDAO.find(id);
        return phoneNumber != null ? DTOConverter.convertPhoneNumberToDTO(phoneNumber) : null;
    }

    public PhoneNumberDTO addPhoneNumber(PhoneNumberDTO phoneNumberDTO) {
        PhoneNumber phoneNumber = DTOConverter.convertPhoneNumberToEntity(phoneNumberDTO);
        phoneNumberDAO.save(phoneNumber);
        return DTOConverter.convertPhoneNumberToDTO(phoneNumber);
    }

    public void deletePhoneNumber(Long id) {
        PhoneNumber phoneNumber = phoneNumberDAO.find(id);
        if (phoneNumber != null) {
            phoneNumberDAO.delete(phoneNumber);
        }
    }
}
