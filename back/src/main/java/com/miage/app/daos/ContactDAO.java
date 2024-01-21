package com.miage.app.daos;

import java.util.List;

import javax.persistence.EntityManager;

import com.miage.app.entities.Contact;

public class ContactDAO {
    private EntityManager entityManager;

    public ContactDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Contact find(Long id) {
        return entityManager.find(Contact.class, id);
    }

    public List<Contact> findAll() {
        return entityManager.createQuery("SELECT c FROM Contact c", Contact.class).getResultList();
    }

    public void save(Contact contact) {
        entityManager.getTransaction().begin();

        if (contact.getId() == null) {
            entityManager.persist(contact);
        } else {
            entityManager.merge(contact);
        }
        entityManager.getTransaction().commit();
    }

    public void delete(Contact contact) {
        entityManager.getTransaction().begin();
        entityManager.remove(contact);
        entityManager.getTransaction().commit();
    }
}