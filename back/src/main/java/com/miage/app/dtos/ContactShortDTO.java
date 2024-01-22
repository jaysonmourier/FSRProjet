package com.miage.app.dtos;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactShortDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private AddressDTO address;
    private Set<PhoneNumberDTO> phoneNumbers;

    @Override
    public String toString() {

        StringBuilder phoneNumbersToString = new StringBuilder();
        for (PhoneNumberDTO phoneNumber : phoneNumbers) {
            phoneNumbersToString.append(phoneNumber.toString()).append(", ");
        }
        String concatenatedPhoneNumbers = phoneNumbersToString.toString();

        return "ContactShortDTO [address=" + address + ", email=" + email + ", firstName=" + firstname
                + ", id=" + id + ", lastName=" + lastname + ", phoneNumbers=" + concatenatedPhoneNumbers + "]";
    }
}
