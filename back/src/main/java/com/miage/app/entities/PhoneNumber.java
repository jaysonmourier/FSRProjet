package com.miage.app.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="PhoneNumber")
@Table(name="phone_numbers")
@Getter @Setter @NoArgsConstructor
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String phoneKind;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idContact")
    private Contact contact;

    public PhoneNumber(String phoneKind, String phoneNumber, Contact contact) {
        this.phoneKind = phoneKind;
        this.phoneNumber = phoneNumber;
        this.contact = contact;
    }
}
