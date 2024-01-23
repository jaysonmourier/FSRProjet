package com.miage.app.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class UserGroup {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;
    private String name;

    @ManyToMany
    @JoinTable(
        name = "contact_group",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private Set<Contact> contacts = new HashSet<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
        contact.getGroups().add(this);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
        contact.getGroups().remove(this);
    }
}