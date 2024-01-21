package com.miage.app.daos;

import javax.persistence.EntityManager;

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
}
