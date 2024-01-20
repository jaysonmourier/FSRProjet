import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/viewmodels/contacts.viewmodel.dart';
import 'package:front/widget/contact_tile.widget.dart';

class ContactsView extends StatefulWidget {
  const ContactsView({super.key});

  @override
  State<ContactsView> createState() => _ContactsViewState();
}

class _ContactsViewState extends State<ContactsView> {
  final ContactsViewModel viewModel = ContactsViewModel();

  Widget buildContactList(List<Contact>? contacts) {
    if((contacts == null) || (contacts.isEmpty)) return const Text("No contacts");
    return ListView.builder(
        itemCount: contacts.length,
        itemBuilder: (context, index) {
          return ContactTile(contact: contacts[index]);
        });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: FutureBuilder(
            future: viewModel.getAllContacts(),
            builder: (context, snapshot) {
              return viewModel.buildWidget(snapshot, buildContactList(viewModel.getContactsList()), const CircularProgressIndicator());
            }));
  }
}
