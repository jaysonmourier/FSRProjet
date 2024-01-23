package com.miage.app.entities;

import java.util.HashSet;
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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact", fetch = FetchType.EAGER)
    private Set<PhoneNumber> phoneNumbers = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(mappedBy = "contacts", fetch = FetchType.EAGER)
    private Set<UserGroup> groups = new HashSet<>();

    public Contact(){}

    public Contact(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public void removePhoneNumber(Long id) {
        PhoneNumber phoneNumber = phoneNumbers.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (phoneNumber != null) {
            phoneNumbers.remove(phoneNumber);
            phoneNumber.setContact(null);
        }
    }

    @Override
    public String toString() {

        StringBuilder phoneNumbersToString = new StringBuilder();
        for (PhoneNumber phoneNumber : phoneNumbers) {
            phoneNumbersToString.append(phoneNumber.toString()).append(", ");
        }
        String concatenatedPhoneNumbers = phoneNumbersToString.toString();

        return "Contact [address=" + address + ", email=" + email + ", firstname=" + firstname + ", groups=" + groups
                + ", id=" + id + ", lastname=" + lastname + ", phoneNumbers=" + concatenatedPhoneNumbers + "]";
    }
}
