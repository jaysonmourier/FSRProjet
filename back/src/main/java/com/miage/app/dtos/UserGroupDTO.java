package com.miage.app.dtos;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserGroupDTO {
    private Long groupId;
    private String groupName;
    private int length;
    private Set<ContactShortDTO> contacts;

    @Override
    public String toString() {
        return "UserGroupDTO [contacts=" + contacts + ", groupId=" + groupId + ", groupName=" + groupName + "]";
    }
}