import 'package:flutter/material.dart';
import 'package:front/models/group.model.dart';
import 'package:front/services/api.service.dart';

class GroupsViewModel {
  List<Group>? groups;
  final TextEditingController groupNameController = TextEditingController();
  final Api api = Api();

  GroupsViewModel({this.groups});

  void dispose() {
    groupNameController.dispose();
  }

  TextEditingController getGroupNameController() {
    return groupNameController;
  }

  Future<bool> create() async {
    if(groupNameController.text.isEmpty) {
      return false;
    }
    Group group = Group(name: groupNameController.text);
    bool value = await api.addGroup(group);
    if(value) {
        groupNameController.clear();
      }
    return value;
  }
}