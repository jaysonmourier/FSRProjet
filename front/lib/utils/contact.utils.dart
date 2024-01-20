import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';

class ContactUtils {
  String extractInitials(Contact contact) {
    if (contact.firstname == null || contact.lastname == null) {
      return "Aa";
    }
    return contact.firstname![0].toUpperCase() +
        contact.lastname![0].toUpperCase();
  }

  Widget noContactsFound() {
    return const Center(child: Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Text("😱", style: TextStyle(fontSize: 34),),
        Text("Votre liste de contacts est vide !")
      ],));
  }
}
