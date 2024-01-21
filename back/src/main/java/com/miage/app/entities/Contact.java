package com.miage.app.entities;

import java.util.ArrayList;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="Contact")
@Table(name="contacts")
@Getter @Setter @NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact", fetch = FetchType.EAGER)
    private Set<PhoneNumber> phoneNumbers = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAddress")
    private Address address;

    @ManyToMany(mappedBy = "contacts", fetch = FetchType.EAGER)
    private Set<UserGroup> groups;

    public Contact(String firstname, String lastname, String email, Set<PhoneNumber> phoneNumbers, Address address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumbers = phoneNumbers;
        this.address = address;
    }
}
