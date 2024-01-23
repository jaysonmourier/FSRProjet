package com.miage.app.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.miage.app.entities.Contact;
import com.miage.app.entities.PhoneNumber;

public class ContactDAO {
    private EntityManager entityManager;

    public ContactDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    protected void finalize() throws Throwable {
        entityManager.close();
        super.finalize();
    }

    public Contact get(Long id) {
        Contact contact;
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();
            contact = entityManager.createQuery("SELECT c FROM Contact c WHERE c.id = :id", Contact.class)
                    .setParameter("id", id).getSingleResult();
            transaction.commit();
            return contact;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error while getting contact: " + e.getMessage());
            return null;
        }
    }

    public List<Contact> getAll() {
        List<Contact> contacts;
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();
            contacts = entityManager.createQuery("SELECT c FROM Contact c", Contact.class).getResultList();
            transaction.commit();
            return contacts;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error while getting contacts: " + e.getMessage());
            return null;
        }
    }

    public boolean save(Contact contact) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            Contact existingContact = new Contact(contact.getFirstname(), contact.getLastname(), contact.getEmail());

            if (contact.getAddress() != null) {
                existingContact.setAddress(contact.getAddress());
            }

            if (contact.getPhoneNumbers() != null) {
                for (PhoneNumber phoneNumber : contact.getPhoneNumbers()) {
                    phoneNumber.setContact(existingContact);
                    existingContact.getPhoneNumbers().add(phoneNumber);
                }
            }

            transaction.begin();
            entityManager.persist(existingContact);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error while saving contact: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Contact contact) {
        System.out.println("Trying to update Contact: " + contact);
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(contact);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error while updating contact: " + e.getMessage());
            return false;
        }
    }

    public void delete(Contact contact) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entityManager.contains(contact) ? contact : entityManager.merge(contact));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error while deleting contact: " + e.getMessage());
        }
    }

    public boolean deletePhoneNumber(Long id, Long pid) {
        Contact contact = this.get(id);
        if (contact == null) {
            System.out.println("Contact not found");
            return false;
        }

        PhoneNumber phoneNumber;

        EntityTransaction transaction = entityManager.getTransaction();
        try {

            phoneNumber = entityManager.createQuery(
                    "SELECT p FROM PhoneNumber p WHERE p.contact.id = :id AND p.id = :pid", PhoneNumber.class)
                    .setParameter("id", id).setParameter("pid", pid).getSingleResult();

            if (phoneNumber == null) {
                System.out.println("Phone number not found");
                return false;
            }

            transaction.begin();

            contact.removePhoneNumber(pid);
            entityManager.remove(phoneNumber);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error while deleting phone number: " + e.getMessage());
            return false;
        }
    }

}