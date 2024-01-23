import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
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

  @override
  void initState() {
    super.initState();
    viewModel.fetchContacts();
  }

  @override
  void dispose() {
    viewModel.dispose();
    super.dispose();
  }

  void _refreshContacts() {
    viewModel.fetchContacts();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(title: 'Mes contacts'),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          await context.push('/contacts/create');
          _refreshContacts();
        },
        backgroundColor: Colors.blue,
        child: const Icon(Icons.add),
      ),
      body: AnimatedBuilder(
        animation: viewModel,
        builder: (context, _) {
          if (viewModel.contacts.isEmpty) {
            return ContactUtils().noContactsFound();
          }
          return _buildContactList(viewModel.contacts);
        },
      ),
    );
  }

  Widget _buildContactList(List<Contact> contacts) {
    return ListView.builder(
      itemCount: contacts.length,
      itemBuilder: (context, index) {
        final contact = contacts[index];
        return Dismissible(
          key: Key(contact.id.toString()),
          background: _buildDismissBackground(),
          direction: DismissDirection.endToStart,
          onDismissed: (direction) {
            viewModel.deleteContact(contact.id!);
          },
          child: ContactTile(contact: contact),
        );
      },
    );
  }

  Widget _buildDismissBackground() {
    return Container(
      color: Colors.red,
      alignment: Alignment.centerRight,
      child: const Padding(
        padding: EdgeInsets.only(right: 20.0),
        child: Icon(Icons.delete, color: Colors.white),
      ),
    );
  }
}
