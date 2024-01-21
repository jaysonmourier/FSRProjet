import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/utils/contact.utils.dart';

class ContactPopUp extends StatelessWidget {
  final Contact contact;

  const ContactPopUp({super.key, required this.contact});

  Widget extractInitials() {
    return Text(ContactUtils().extractInitials(contact));
  }

  String getFirstname() {
    if (contact.firstname == null) {
      return "Unknown";
    }
    return contact.firstname!;
  }

  String getLastname() {
    if (contact.lastname == null) {
      return "Unknown";
    }
    return contact.lastname!;
  }

  Widget getPhoneNumbers() {
    if ((contact.phoneNumbers == null) || (contact.phoneNumbers!.isEmpty)) {
      return const Text("No phone number");
    }
    return Column(
      children: contact.phoneNumbers!.map((phone) => Text(phone.phoneNumber!)).toList(),
    );
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Center(
          child: Column(
                children: [
          CircleAvatar(
            radius: 30,
            child: extractInitials(),
          ),
          const SizedBox(height: 10,),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [Text(getFirstname(), style: const TextStyle(fontSize: 16),), const SizedBox(width: 5,),Text(getLastname(), style: const TextStyle(fontSize: 16),)],
          )
                ],
              )),
      content: getPhoneNumbers(),
    );
  }
}
