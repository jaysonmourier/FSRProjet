package com.miage.app.dtos;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

// public class UserGroupDTO {
//     private long id;
//     private String name;
//     private List<ContactDTO> contacts;

//     public long getId() {
//         return id;
//     }

//     public void setId(long id) {
//         this.id = id;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public List<ContactDTO> getContacts() {
//         return contacts;
//     }

//     public void setContacts(List<ContactDTO> contacts) {
//         this.contacts = contacts;
//     }
// }

@Getter @Setter
public class UserGroupDTO {
    private Long groupId;
    private String groupName;
    private Set<ContactDTO> contacts;
}