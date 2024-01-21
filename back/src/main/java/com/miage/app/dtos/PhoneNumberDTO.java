package com.miage.app.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PhoneNumberDTO {
    private Long id;
    private String phoneKind;
    private String phoneNumber;
    private ContactDTO contact;

    @Override
    public String toString() {
        return "PhoneNumberDTO [contact=" + contact + ", id=" + id + ", phoneKind=" + phoneKind + ", phoneNumber="
                + phoneNumber + "]";
    }
}
