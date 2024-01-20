import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/services/api.service.dart';

class ContactsViewModel {
  final Api api = Api();
  List<Contact>? contacts;

  List<Contact>? getContactsList() {
    contacts?.sort((a, b) => a.firstname!.compareTo(b.firstname!));
    return contacts;
  }

  Future<List<Contact>?> getAllContacts() async {
    contacts = await api.getAllContacts();
    return contacts;
  }

  Widget buildWidget(AsyncSnapshot<List<Contact>?> snapshot,
      Widget hasDataWidget, Widget noDataWidget) {
    if (snapshot.hasData) {
      return hasDataWidget;
    } else {
      return noDataWidget;
    }
  }
}
