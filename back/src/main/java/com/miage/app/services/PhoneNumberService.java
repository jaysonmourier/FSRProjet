package com.miage.app.services;

import com.miage.app.daos.DAOPhoneNumber;
import com.miage.app.entities.PhoneNumber;

public class PhoneNumberService {
    private final DAOPhoneNumber daoPhoneNumber;

    public PhoneNumberService() {
        this.daoPhoneNumber = new DAOPhoneNumber();
    }

    public boolean addPhoneNumber(PhoneNumber phoneNumber) {
        return daoPhoneNumber.addPhoneNumber(phoneNumber);
    }

    public Object getAllPhone() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPhone'");
    }
}