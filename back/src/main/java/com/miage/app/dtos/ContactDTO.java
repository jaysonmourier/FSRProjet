package com.miage.app.dtos;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private AddressDTO address;
    private Set<PhoneNumberDTO> phoneNumbers;
    private Set<UserGroupDTO> groups;

    @Override
    public String toString() {

        StringBuilder phoneNumbersToString = new StringBuilder();
        for (PhoneNumberDTO phoneNumber : phoneNumbers) {
            phoneNumbersToString.append(phoneNumber.toString()).append(", ");
        }
        String concatenatedPhoneNumbers = phoneNumbersToString.toString();

        return "ContactDTO [address=" + address + ", email=" + email + ", firstName=" + firstname + ", groups=" + groups
                + ", id=" + id + ", lastName=" + lastname + ", phoneNumbers=" + concatenatedPhoneNumbers + "]";
    }
}
