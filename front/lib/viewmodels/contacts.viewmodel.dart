import 'dart:async';

import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/services/api.service.dart';

class ContactsViewModel with ChangeNotifier {
  final Api api = Api();
  List<Contact> _contacts = [];

  List<Contact> get contacts => _contacts;

  void fetchContacts() async {
    try {
      var contacts = await api.getAllContacts();
      contacts?.sort((a, b) => a.firstname!.compareTo(b.firstname!));
      _contacts = contacts!;
      notifyListeners();
    } catch (e) {
    }
  }

  Future<bool> deleteContact(int id) async {
    try {
      final success = await api.deleteContact(id);
      if (success) {
        _contacts.removeWhere((contact) => contact.id == id);
        notifyListeners();
      }
      return success;
    } catch (e) {
      return false;
    }
  }

  @override
  void dispose() {
    super.dispose();
  }
}
