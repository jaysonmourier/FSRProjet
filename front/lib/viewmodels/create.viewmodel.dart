import 'package:flutter/material.dart';
import 'package:front/models/address.model.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/models/phone.model.dart';
import 'package:front/services/api.service.dart';
import 'package:go_router/go_router.dart';

class CreateContactViewModel {
  bool editing;
  final Api api = Api();
  final TextEditingController firstnameController = TextEditingController();
  final TextEditingController lastnameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  Contact? contact;
  List<Phone> phones = [];
  Map<Phone, TextEditingController> phoneKindControllers = {};
  Map<Phone, TextEditingController> phoneNumberControllers = {};
  Address? address;
  final TextEditingController addressStreetController = TextEditingController();
  final TextEditingController addressCityController = TextEditingController();
  final TextEditingController addressZipCodeController =
      TextEditingController();
  final TextEditingController addressCountryController =
      TextEditingController();

  CreateContactViewModel(Contact? contact, this.editing) {
    if (contact != null) {
      this.contact = contact;
      firstnameController.text = contact.firstname ?? "";
      lastnameController.text = contact.lastname ?? "";
      emailController.text = contact.email ?? "";
      addressStreetController.text = contact.address?.street ?? "";
      addressCityController.text = contact.address?.city ?? "";
      addressZipCodeController.text = contact.address?.zip ?? "";
      addressCountryController.text = contact.address?.country ?? "";
      for (var phone in contact.phoneNumbers!) {
        addPhone(phone);
        phoneKindControllers[phone]?.text = phone.phoneKind ?? "";
        phoneNumberControllers[phone]?.text = phone.phoneNumber ?? "";
      }
    } else {
      addPhone(Phone());
    }
  }

  void addPhone(Phone phone) {
    phones.add(phone);
    phoneKindControllers[phone] = TextEditingController();
    phoneNumberControllers[phone] = TextEditingController();
  }

  void removePhone(Phone phone) {
    phones.remove(phone);
    phoneKindControllers[phone]?.dispose();
    phoneNumberControllers[phone]?.dispose();
    phoneKindControllers.remove(phone);
    phoneNumberControllers.remove(phone);

    if (phones.isEmpty) {
      addPhone(Phone());
    }
  }

  void dispose() {
    firstnameController.dispose();
    lastnameController.dispose();
    emailController.dispose();
    addressStreetController.dispose();
    addressCityController.dispose();
    addressZipCodeController.dispose();
    addressCountryController.dispose();
    for (var controller in phoneKindControllers.values) {
      controller.dispose();
    }
    for (var controller in phoneNumberControllers.values) {
      controller.dispose();
    }
  }

  TextEditingController getFirstnameController() {
    return firstnameController;
  }

  TextEditingController getLastnameController() {
    return lastnameController;
  }

  TextEditingController getEmailController() {
    return emailController;
  }

  TextEditingController getAddressStreetController() {
    return addressStreetController;
  }

  TextEditingController getAddressCityController() {
    return addressCityController;
  }

  TextEditingController getAddressZipCodeController() {
    return addressZipCodeController;
  }

  TextEditingController getAddressCountryController() {
    return addressCountryController;
  }

  String get firstname => firstnameController.text;
  String get lastname => lastnameController.text;
  String get email => emailController.text;
  String get addressStreet => addressStreetController.text;
  String get addressCity => addressCityController.text;
  String get addressZipCode => addressZipCodeController.text;
  String get addressCountry => addressCountryController.text;

  bool get hasAddress =>
      addressStreet.isNotEmpty ||
      addressCity.isNotEmpty ||
      addressZipCode.isNotEmpty ||
      addressCountry.isNotEmpty;

  void save(BuildContext context, Function(String, Color) onError) {
    if (firstname.isEmpty || lastname.isEmpty || email.isEmpty) {
      onError.call("Veuillez remplir tous les champs", Colors.red);
      return;
    }

    if (!email.contains('@')) {
      onError.call("Veuillez saisir un email valide", Colors.red);
      return;
    }

    List<Phone> phones = [];

    for (var phone in phoneKindControllers.keys) {
      phones.add(Phone(
        phoneKind: phoneKindControllers[phone]?.text,
        phoneNumber: phoneNumberControllers[phone]?.text,
      ));
    }

    if (phones.isEmpty) {
      onError.call(
          "Veuillez saisir au moins un numéro de téléphone", Colors.red);
      return;
    }

    if (phones.any((phone) => phone.phoneNumber?.isEmpty ?? true)) {
      onError.call("Veuillez saisir un numéro de téléphone valide", Colors.red);
      return;
    }

    if (phones.any((phone) => phone.phoneKind?.isEmpty ?? true)) {
      onError.call("Veuillez saisir un type de téléphone valide", Colors.red);
      return;
    }

    if (!hasAddress) {
      onError.call("Veuillez saisir une adresse", Colors.red);
      return;
    }

    contact = Contact(
      id: contact?.id,
      firstname: firstname,
      lastname: lastname,
      email: email,
      phoneNumbers: phones,
      address: Address(
        street: addressStreet,
        city: addressCity,
        zip: addressZipCode,
        country: addressCountry,
      ),
    );

    if (editing) {
      api.updateContact(contact!).then((value) {
        if (value) {
          onError.call("Contact mis à jour", Colors.green);
          context.go('/contacts');
        } else {
          onError.call("Erreur lors de la mise à jour du contact", Colors.red);
        }
      });
    } else {
      api.addContact(contact!).then((value) {
        if (value) {
          onError.call("Contact ajouté", Colors.green);
          context.go('/contacts');
        } else {
          onError.call("Erreur lors de l'ajout du contact", Colors.red);
        }
      });
    }
  }
}
