package com.miage.app.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserGroupShortDTO {
    private Long groupId;
    private String groupName;
    private int length;

    public UserGroupShortDTO() {}

    public UserGroupShortDTO(Long groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "UserGroupShortDTO [groupId=" + groupId + ", groupName=" + groupName + "]";
    }
}
