package com.miage.app.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Entity(name = "UserGroup")
// @Table(name = "mygroups")
// @Getter
// @Setter
// @NoArgsConstructor
// public class UserGroup {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     private String name;

//     @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
//     @JoinTable(name = "contact_groups", joinColumns = @JoinColumn(name = "idGroup"), inverseJoinColumns = @JoinColumn(name = "idContact"))
//     private List<Contact> contacts;
// }

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
}