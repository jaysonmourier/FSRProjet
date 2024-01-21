package com.miage.app.daos;

import javax.persistence.EntityManager;

import com.miage.app.entities.PhoneNumber;

import java.util.List;

public class PhoneNumberDAO {
    private EntityManager entityManager;

    public PhoneNumberDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public PhoneNumber find(Long id) {
        return entityManager.find(PhoneNumber.class, id);
    }

    public List<PhoneNumber> findAll() {
        return entityManager.createQuery("SELECT p FROM PhoneNumber p", PhoneNumber.class).getResultList();
    }

    public void save(PhoneNumber phoneNumber) {
        entityManager.getTransaction().begin();
        if (phoneNumber.getId() == null) {
            entityManager.persist(phoneNumber);
        } else {
            entityManager.merge(phoneNumber);
        }
        entityManager.getTransaction().commit();
    }

    public void delete(PhoneNumber phoneNumber) {
        entityManager.getTransaction().begin();
        entityManager.remove(phoneNumber);
        entityManager.getTransaction().commit();
    }
}
