package com.miage.app.services;

import java.util.List;
import java.util.stream.Collectors;

import com.miage.app.config.EntityManagerConfig;
import com.miage.app.daos.AddressDAO;
import com.miage.app.dtos.AddressDTO;
import com.miage.app.entities.Address;
import com.miage.app.utils.DTOConverter;

public class AddressService {
    private AddressDAO addressDAO = new AddressDAO(EntityManagerConfig.getEmf().createEntityManager());

    public List<AddressDTO> getAllAddresses() {
        List<Address> addresses = addressDAO.findAll();
        return addresses.stream().map(DTOConverter::convertAddressToDTO).collect(Collectors.toList());
    }

    public AddressDTO getAddress(Long id) {
        Address address = addressDAO.find(id);
        return address != null ? DTOConverter.convertAddressToDTO(address) : null;
    }

    public AddressDTO addAddress(AddressDTO addressDTO) {
        Address address = DTOConverter.convertAddressToEntity(addressDTO);
        addressDAO.save(address);
        return DTOConverter.convertAddressToDTO(address);
    }

    public void deleteAddress(Long id) {
        Address address = addressDAO.find(id);
        if (address != null) {
            addressDAO.delete(address);
        }
    }
}
