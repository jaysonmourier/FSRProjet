package com.miage.app.services;

import java.util.List;

import com.miage.app.daos.DAOAddress;
import com.miage.app.entities.Address;

public class AddressService {
    private final DAOAddress daoAddress;

    public AddressService() {
        this.daoAddress = new DAOAddress();
    }

    public List<Address> getAll() {
        return daoAddress.getAll();
    }

    public Address get(Long id) {
        return daoAddress.get(id);
    }

    public boolean add(Address address) {
        return daoAddress.add(address);
    }

    public boolean update(Long id, Address address) {
        Address addressToUpdate = daoAddress.get(id);
        if (addressToUpdate == null) {
            return false;
        }
        return daoAddress.update(address);
    }

    public boolean delete(Long id) {
        return daoAddress.delete(id);
    }
}
