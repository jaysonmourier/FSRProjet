package com.miage.app.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.miage.app.entities.UserGroup;

import java.util.List;

public class UserGroupDAO {
    private EntityManager entityManager;

    public UserGroupDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserGroup find(Long id) {
        return entityManager.find(UserGroup.class, id);
    }

    public List<UserGroup> findAll() {
        return entityManager.createQuery("SELECT g FROM UserGroup g", UserGroup.class).getResultList();
    }

    public void save(UserGroup group) {
        entityManager.getTransaction().begin();
        if (group.getGroupId() == null) {
            entityManager.persist(group);
        } else {
            entityManager.merge(group);
        }
        entityManager.getTransaction().commit();
    }

    public void delete(UserGroup group) {
        entityManager.getTransaction().begin();
        entityManager.remove(group);
        entityManager.getTransaction().commit();
    }

    public boolean removeContactFromGroup(Long groupId, Long contactId) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            UserGroup group = this.find(groupId);
            if(group != null) {
                group.getContacts().removeIf(c -> c.getId().equals(contactId));
            }
            entityManager.persist(
                entityManager.merge(group)
            );
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
