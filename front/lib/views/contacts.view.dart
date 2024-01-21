import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/services/api.service.dart';
import 'package:front/utils/contact.utils.dart';
import 'package:front/viewmodels/contacts.viewmodel.dart';
import 'package:front/widget/appbar/custom_app_bar.widget.dart';
import 'package:front/widget/contact_tile.widget.dart';
import 'package:go_router/go_router.dart';

class ContactsView extends StatefulWidget {
  const ContactsView({super.key});

  @override
  State<ContactsView> createState() => _ContactsViewState();
}

class _ContactsViewState extends State<ContactsView> {
  final ContactsViewModel viewModel = ContactsViewModel();

  Widget buildContactList(List<Contact>? contacts) {
    if ((contacts == null) || (contacts.isEmpty)) {
      return ContactUtils().noContactsFound();
    }
    return ListView.builder(
      itemCount: contacts.length,
      itemBuilder: (context, index) {
        final contact = contacts[index];
        return Dismissible(
          key: Key(contact.id.toString()),
          background: Container(
            color: Colors.red,
            alignment: Alignment.centerRight,
            child: const Padding(
              padding: EdgeInsets.only(right: 20.0),
              child: Icon(Icons.delete, color: Colors.white),
            ),
          ),
          direction: DismissDirection.endToStart,
          onDismissed: (direction) async {
            Api().deleteContact(contact.id!).then((value) {
              if (value) {
                setState(() {
                  contacts.removeAt(index);
                });
              }
            });
          },
          child: ContactTile(contact: contact),
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(title: 'Mes contacts'),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          await context.push('/contacts/create');
          setState(() {});
        },
        backgroundColor: Colors.blue,
        child: const Icon(Icons.add),
      ),
      body: FutureBuilder(
          future: viewModel.getAllContacts(),
          builder: (context, snapshot) {
            return viewModel.buildWidget(
                snapshot,
                buildContactList(viewModel.getContactsList()),
                const Center(child: CircularProgressIndicator()));
          }),
    );
  }
}
