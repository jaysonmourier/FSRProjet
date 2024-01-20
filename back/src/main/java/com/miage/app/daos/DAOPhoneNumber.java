package com.miage.app.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.miage.app.config.EntityManagerConfig;
import com.miage.app.entities.Contact;
import com.miage.app.entities.PhoneNumber;

public class DAOPhoneNumber {
    public DAOPhoneNumber() {
        super();
    }

    public boolean add(PhoneNumber phoneNumber) {
        boolean state = false;
        try {
            System.out.println("Contact: " + phoneNumber.getContact().getEmail());
            PhoneNumber newPhoneNumber = new PhoneNumber(phoneNumber.getPhoneKind(), phoneNumber.getPhoneNumber(),
                    phoneNumber.getContact());
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

    public PhoneNumber get(Long id) {
        EntityManager em = null;
        PhoneNumber phoneNumber = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            phoneNumber = em.find(PhoneNumber.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return phoneNumber;
    }

    public List<PhoneNumber> getAll() {
        EntityManager em = null;
        List<PhoneNumber> phones = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            TypedQuery<PhoneNumber> query = em.createQuery("SELECT p FROM PhoneNumber p", PhoneNumber.class);
            phones = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return phones;
    }

    public boolean update(Long id, PhoneNumber updatedPhoneNumber) {
        EntityManager em = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            PhoneNumber phoneNumber = em.find(PhoneNumber.class, id);

            if (phoneNumber == null) {
                return false;
            }

            phoneNumber.setPhoneKind(updatedPhoneNumber.getPhoneKind());
            phoneNumber.setPhoneNumber(updatedPhoneNumber.getPhoneNumber());

            EntityTransaction et = em.getTransaction();
            et.begin();
            em.merge(phoneNumber);
            et.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public boolean delete(Long id) {
        EntityManager em = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            PhoneNumber phoneNumber = em.find(PhoneNumber.class, id);

            if (phoneNumber == null) {
                return false;
            }

            Contact contact = phoneNumber.getContact();
            if (contact != null) {
                contact.getPhoneNumbers().remove(phoneNumber);
            }

            EntityTransaction et = em.getTransaction();
            et.begin();
            em.remove(phoneNumber);
            et.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
