package com.miage.app.daos;

import java.util.List;

import javax.persistence.EntityManager;

import com.miage.app.entities.Address;

public class AddressDAO {
    private EntityManager entityManager;

    public AddressDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Address find(Long id) {
        return entityManager.find(Address.class, id);
    }

    public List<Address> findAll() {
        return entityManager.createQuery("SELECT a FROM Address a", Address.class).getResultList();
    }

    public void save(Address address) {
        entityManager.getTransaction().begin();
        if (address.getId() == null) {
            entityManager.persist(address);
        } else {
            entityManager.merge(address);
        }
        entityManager.getTransaction().commit();
    }

    public void delete(Address address) {
        entityManager.getTransaction().begin();
        entityManager.remove(address);
        entityManager.getTransaction().commit();
    }
}
