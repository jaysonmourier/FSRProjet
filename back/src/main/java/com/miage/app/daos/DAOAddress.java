package com.miage.app.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.miage.app.config.EntityManagerConfig;
import com.miage.app.entities.Address;

public class DAOAddress {
    public DAOAddress() {
        super();
    }

    public boolean add(Address address) {
        boolean state = false;
        try {
            Address newAddress = new Address(address.getStreet(), address.getCity(), address.getZip(),
                    address.getCountry());

            EntityManager em = EntityManagerConfig.getEmf().createEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(newAddress);
            et.commit();
            em.close();
            state = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }

    public Address get(Long id) {
        EntityManager em = null;
        Address address = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            address = em.find(Address.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return address;
    }

    public List<Address> getAll() {
        EntityManager em = null;
        List<Address> addresses = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a", Address.class);
            addresses = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return addresses;
    }

    public boolean update(Address address) {
        EntityManager em = null;
        boolean isUpdated = false;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            EntityTransaction et = em.getTransaction();

            Address existingAddress = em.find(Address.class, address.getId());
            if (existingAddress != null) {
                et.begin();
                existingAddress.setStreet(address.getStreet());
                existingAddress.setCity(address.getCity());
                existingAddress.setZip(address.getZip());
                existingAddress.setCountry(address.getCountry());
                em.merge(existingAddress);
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

    public boolean delete(Long id) {
        EntityManager em = null;
        boolean isDeleted = false;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            EntityTransaction et = em.getTransaction();
            Address address = em.find(Address.class, id);
            if (address != null) {
                et.begin();
                em.remove(address);
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
