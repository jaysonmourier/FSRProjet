package com.miage.app.services;

import java.util.List;
import java.util.stream.Collectors;

import com.miage.app.config.EntityManagerConfig;
import com.miage.app.daos.UserGroupDAO;
import com.miage.app.dtos.UserGroupDTO;
import com.miage.app.entities.UserGroup;
import com.miage.app.utils.DTOConverter;

public class UserGroupService {

    private UserGroupDAO userGroupDAO = new UserGroupDAO(EntityManagerConfig.getEmf().createEntityManager());

    public List<UserGroupDTO> getAllUserGroups() {
        List<UserGroup> groups = userGroupDAO.findAll();
        return groups.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserGroupDTO getUserGroup(Long groupId) {
        UserGroup group = userGroupDAO.find(groupId);
        return group != null ? convertToDTO(group) : null;
    }

    public UserGroupDTO addUserGroup(UserGroupDTO groupDTO) {
        UserGroup group = convertToEntity(groupDTO);
        userGroupDAO.save(group);
        return convertToDTO(group);
    }

    public UserGroupDTO updateUserGroup(Long groupId, UserGroupDTO groupDTO) {
        UserGroup group = userGroupDAO.find(groupId);
        if (group != null) {
            group.setName(groupDTO.getGroupName());
            // If your UserGroupDTO has a list of ContactDTOs, you would update the list of Contact entities.
            // This might involve complex logic to add, update, or remove contacts.
            userGroupDAO.save(group);
            return convertToDTO(group);
        }
        return null;
    }

    public void deleteUserGroup(Long groupId) {
        UserGroup group = userGroupDAO.find(groupId);
        if (group != null) {
            userGroupDAO.delete(group);
        }
    }

    private UserGroupDTO convertToDTO(UserGroup group) {
        UserGroupDTO groupDTO = new UserGroupDTO();
        groupDTO.setGroupId(group.getGroupId());
        groupDTO.setGroupName(group.getName());
        groupDTO.setContacts(group.getContacts().stream()
                 .map(DTOConverter::convertContactToDTO)
                 .collect(Collectors.toSet()));
        return groupDTO;
    }
    
    private UserGroup convertToEntity(UserGroupDTO groupDTO) {
        UserGroup group = new UserGroup();
        group.setGroupId(groupDTO.getGroupId());
        group.setName(groupDTO.getGroupName());
        return group;
    }
}
