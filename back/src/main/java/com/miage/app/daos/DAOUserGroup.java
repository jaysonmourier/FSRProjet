package com.miage.app.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.miage.app.config.EntityManagerConfig;
import com.miage.app.entities.UserGroup;

public class DAOUserGroup {
    public DAOUserGroup() {
        super();
    }

    public boolean add(UserGroup group) {
        boolean state = false;
        EntityManager em = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(group);
            et.commit();
            state = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return state;
    }

    public UserGroup get(Long id) {
        EntityManager em = null;
        UserGroup group = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            group = em.find(UserGroup.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return group;
    }

    public List<UserGroup> getAll() {
        EntityManager em = null;
        List<UserGroup> groups = null;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            TypedQuery<UserGroup> query = em.createQuery("SELECT g FROM UserGroup g", UserGroup.class);
            groups = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return groups;
    }

    public boolean update(UserGroup updatedGroup) {
        EntityManager em = null;
        boolean isUpdated = false;
        try {
            em = EntityManagerConfig.getEmf().createEntityManager();
            EntityTransaction et = em.getTransaction();

            UserGroup existingGroup = em.find(UserGroup.class, updatedGroup.getId());
            if (existingGroup != null) {
                et.begin();
                existingGroup.setName(updatedGroup.getName());
                existingGroup.setContacts(updatedGroup.getContacts());
                em.merge(existingGroup);
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
            UserGroup group = em.find(UserGroup.class, id);
            if (group != null) {
                et.begin();
                em.remove(group);
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
