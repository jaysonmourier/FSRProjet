import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/models/phone.model.dart';
import 'package:front/viewmodels/create.viewmodel.dart';

class CreateContactView extends StatefulWidget {
  final Contact? contact;
  final bool editing;
  CreateContactView({super.key, this.contact, this.editing = false});

  @override
  State<CreateContactView> createState() => _CreateContactViewState();
}

class _CreateContactViewState extends State<CreateContactView> {
  late final CreateContactViewModel viewModel;

  @override
  void initState() {
    super.initState();
    viewModel = CreateContactViewModel(widget.contact, widget.editing);
  }

  void showSnackBar(String message, Color color) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: color,
        duration: const Duration(seconds: 2),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("${widget.editing ? 'Editer' : 'Créer'} un contact"),
        ),
        body: SingleChildScrollView( 
          padding: const EdgeInsets.all(32.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const Text("Informations personnelles", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18),),
              const SizedBox(height: 10),
              buildContactForm(),
              const SizedBox(height: 30),
              const Text("Numéros de téléphone", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18),),
              const SizedBox(height: 10),
              buildPhoneList(),
              const SizedBox(height: 30),
              const Text("Adresse", style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18),),
              const SizedBox(height: 10),
              buildAddressForm(),
              const SizedBox(height: 30),
              ElevatedButton(
                onPressed: () {
                  viewModel.save(context, showSnackBar);
                },
                child: const Text('Enregistrer le contact'),
              ),
            ],
          ),
        ));
  }

  Widget buildAddressForm() {
    return Column(
      children: [
        TextField(
          controller: viewModel.getAddressStreetController(),
          decoration: const InputDecoration(
            hintText: 'Rue',
          ),
        ),
        const SizedBox(height: 10),
        TextField(
          controller: viewModel.getAddressCityController(),
          decoration: const InputDecoration(
            hintText: 'Ville',
          ),
        ),
        const SizedBox(height: 10),
        TextField(
          controller: viewModel.getAddressZipCodeController(),
          decoration: const InputDecoration(
            hintText: 'Code postal',
          ),
        ),
        const SizedBox(height: 10),
        TextField(
          controller: viewModel.getAddressCountryController(),
          decoration: const InputDecoration(
            hintText: 'Pays',
          ),
        ),
      ],
    );
  }

  Widget buildContactForm() {
    return Column(
      children: [
        TextField(
          controller: viewModel.getFirstnameController(),
          decoration: const InputDecoration(
            hintText: 'Prénom',
          ),
        ),
        const SizedBox(height: 10),
        TextField(
          controller: viewModel.getLastnameController(),
          decoration: const InputDecoration(
            hintText: 'Nom de famille',
          ),
        ),
        const SizedBox(height: 10),
        TextField(
          keyboardType: TextInputType.emailAddress,
          controller: viewModel.getEmailController(),
          decoration: const InputDecoration(
            hintText: 'E-mail',
          ),
        ),
      ],
    );
  }

  Widget buildPhoneList() {
    return Column(
      children: viewModel.phones.map((phone) => buildPhoneItem(phone)).toList()
        ..add(
          TextButton(
            onPressed: () => addPhone(),
            child: const Text('Ajouter un numéro de téléphone'),
          ),
        ),
    );
  }

Widget buildPhoneItem(Phone phone) {
    return Row(
      children: [
        Expanded(
          flex: 1,
          child: TextField(
            controller: viewModel.phoneKindControllers[phone],
            decoration: const InputDecoration(
              hintText: 'Type',
            ),
          ),
        ),
        const SizedBox(width: 20),
        Expanded(
          flex: 3,
          child: TextField(
            keyboardType: TextInputType.phone,
            controller: viewModel.phoneNumberControllers[phone],
            decoration: const InputDecoration(
              hintText: 'Numéro de téléphone',
            ),
          ),
        ),
        IconButton(
          icon: const Icon(Icons.delete),
          onPressed: () => removePhone(phone),
        ),
      ],
    );
  }

  void addPhone() {
    setState(() {
      viewModel.addPhone(Phone());
    });
  }

  void removePhone(Phone phone) {
    setState(() {
      viewModel.removePhone(phone);
    });
  }
}
