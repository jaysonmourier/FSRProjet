package com.miage.app.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PhoneNumber {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String phoneKind;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
}
