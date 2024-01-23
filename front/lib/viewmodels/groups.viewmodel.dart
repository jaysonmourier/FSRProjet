import 'dart:async';

import 'package:flutter/material.dart';
import 'package:front/models/group.model.dart';
import 'package:front/services/api.service.dart';

class GroupsViewModel {
  final Api api = Api();
  final TextEditingController groupNameController = TextEditingController();
  final StreamController<List<Group>> _groupsController =
      StreamController.broadcast();
  List<Group> _groups = [];

  Stream<List<Group>> get groupsStream => _groupsController.stream;
  GroupsViewModel() {
    fetchGroups();
  }

  void fetchGroups() async {
    try {
      _groups = await api.getAllGroups() ?? [];
      _groupsController.add(_groups);
    } catch (e) {
      _groupsController.addError(e);
    }
  }

  Future<bool> create() async {
    if (groupNameController.text.isEmpty) {
      return false;
    }
    final group = Group(name: groupNameController.text);
    final success = await api.addGroup(group);
    if (success) {
      groupNameController.clear();
      fetchGroups();
    }
    return success;
  }

  Future<bool> deleteGroup(int id) async {
    final success = await api.deleteGroup(id);
    if (success) {
      _groups.removeWhere(
          (group) => group.id == id); 
      _groupsController.add(_groups); 
    }
    return success;
  }

  void dispose() {
    groupNameController.dispose();
    _groupsController.close();
  }
}
