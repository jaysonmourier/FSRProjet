import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/utils/contact.utils.dart';
import 'package:front/viewmodels/contacts.viewmodel.dart';
import 'package:front/widget/appbar/custom_app_bar.widget.dart';
import 'package:front/widget/contact_tile.widget.dart';

class ContactsView extends StatefulWidget {
  const ContactsView({super.key});

  @override
  State<ContactsView> createState() => _ContactsViewState();
}

class _ContactsViewState extends State<ContactsView> {
  final ContactsViewModel viewModel = ContactsViewModel();

  Widget buildContactList(List<Contact>? contacts) {
    if((contacts == null) || (contacts.isEmpty)) return ContactUtils().noContactsFound();
    return ListView.builder(
        itemCount: contacts.length,
        itemBuilder: (context, index) {
          return ContactTile(contact: contacts[index]);
        });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(title: 'Mes contacts'),
      body: FutureBuilder(
              future: viewModel.getAllContacts(),
              builder: (context, snapshot) {
                return viewModel.buildWidget(snapshot, buildContactList(viewModel.getContactsList()), const Center(child: CircularProgressIndicator()));
              }),
    );
  }
}
