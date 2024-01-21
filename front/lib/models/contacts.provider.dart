import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';

class ContactsProvider extends ChangeNotifier {
  List<Contact> _contacts = [];

  List<Contact> get contacts => _contacts;

  set contacts(List<Contact> contacts) {
    _contacts = contacts;
    notifyListeners();
  }

  void addContact(Contact contact) {
    _contacts.add(contact);
    notifyListeners();
  }

  void removeContact(Contact contact) {
    _contacts.remove(contact);
    notifyListeners();
  }

  void setContacts(List<Contact>? data) {
    _contacts = data??[];
    notifyListeners();
  }

  void updateContact(Contact contact) {
    final index = _contacts.indexWhere((element) => element.id == contact.id);
    _contacts[index] = contact;
    notifyListeners();
  }

  Contact getContactById(int contactId) {
    return _contacts.firstWhere((element) => element.id == contactId);
  }
}