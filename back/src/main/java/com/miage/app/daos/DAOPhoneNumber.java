package com.miage.app.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.miage.app.config.EntityManagerConfig;
import com.miage.app.entities.PhoneNumber;

public class DAOPhoneNumber {
    public DAOPhoneNumber() {
        super();
    }

    public boolean addPhoneNumber(PhoneNumber phoneNumber) {
        boolean state = false;
        try {
            System.out.println("Contact: " + phoneNumber.getContact().getEmail());
            PhoneNumber newPhoneNumber = new PhoneNumber(phoneNumber.getPhoneKind(), phoneNumber.getPhoneNumber(), phoneNumber.getContact());
            EntityManager em = EntityManagerConfig.getEmf().createEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(newPhoneNumber);
            et.commit();
            em.close();
            state = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }
}
