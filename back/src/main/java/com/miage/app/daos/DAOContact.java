package com.miage.app.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.miage.app.config.EntityManagerConfig;
import com.miage.app.entities.Contact;

public class DAOContact {
    public DAOContact() {
        super();
    }

    public boolean addContact(Contact contact) {
        boolean state = false;
        try {
            Contact newContact = new Contact(contact.getFirstname(), contact.getLastname(), contact.getEmail());

            EntityManager em = EntityManagerConfig.getEmf().createEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(newContact);
            et.commit();
            em.close();
            state = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }

    public Contact getContact(Long id) {
        EntityManager em = null;
        Contact contact = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            contact = em.find(Contact.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return contact;
    }

    public List<Contact> getContacts() {
        EntityManager em = null;
        List<Contact> contacts = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            TypedQuery<Contact> query = em.createQuery("SELECT c FROM Contact c", Contact.class);
            contacts = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return contacts;
    }

    public boolean updateContact(Contact updatedContact) {
        EntityManager em = null;
        boolean isUpdated = false;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            EntityTransaction et = em.getTransaction();
    
            Contact existingContact = em.find(Contact.class, updatedContact.getId());
            if (existingContact != null) {
                et.begin();
                existingContact.setFirstname(updatedContact.getFirstname());
                existingContact.setLastname(updatedContact.getLastname());
                existingContact.setEmail(updatedContact.getEmail());
                // Mettez à jour les autres champs nécessaires
                em.merge(existingContact);
                et.commit();
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return isUpdated;
    }
    

    public boolean deleteContact(Long id) {
        EntityManager em = null;
        boolean isDeleted = false;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            EntityTransaction et = em.getTransaction();
            Contact contact = em.find(Contact.class, id);
            if (contact != null) {
                et.begin();
                em.remove(contact);
                et.commit();
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return isDeleted;
    }
}
