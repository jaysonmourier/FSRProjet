import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/services/api.service.dart';

class AddContactWidget extends StatelessWidget {
  final TextEditingController _firstNameController = TextEditingController();
  final TextEditingController _lastNameController = TextEditingController();
  final Contact contact = Contact();
  final Api api = Api();
  final Function onValid;

  AddContactWidget({super.key, required this.onValid});

  void dispose() {
    _firstNameController.dispose();
    _lastNameController.dispose();
  }

  void addContact(BuildContext context) {
    if(_firstNameController.text.isNotEmpty && _lastNameController.text.isNotEmpty) {
      contact.firstname = _firstNameController.text;
      contact.lastname = _lastNameController.text;
      api.addContact(contact).then((value) {
        if(value) {
          onValid();
        }
      });
    } 
    else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Veuillez remplir tous les champs'),
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          TextFormField(
            controller: _firstNameController,
            decoration: const InputDecoration(
              hintText: 'Prénom',
            ),
          ),
          const SizedBox(
            height: 10,
          ),
          TextFormField(
            controller: _lastNameController,
            decoration: const InputDecoration(
              hintText: 'Nom de famille',
            ),
          ),
          const SizedBox(
            height: 25,
          ),
          ElevatedButton(
            onPressed: () {
              addContact(context);
            },
            child: const Text('Ajouter'),
          ),
        ],
      ),
    );
  }
}
