import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/models/phone.model.dart';
import 'package:front/utils/contact.utils.dart';
import 'package:go_router/go_router.dart';

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

  Widget getPhone(String kind, String number) {
    return Container(
      margin: EdgeInsets.only(top: 5, bottom: 5),
      child: Column(
        children: [
          Text(number),
          Text(kind, style: const TextStyle(fontSize: 12)),
        ],
      ),
    );
  }

  Widget getPhoneNumbers() {
    if ((contact.phoneNumbers == null) || (contact.phoneNumbers!.isEmpty)) {
      return const Text("No phone number");
    }

    String pluriel = (contact.phoneNumbers!.length > 1 ? "s" : "");

    return Column(
      children: [
        Text("Phone number${pluriel}",
            style: const TextStyle(fontWeight: FontWeight.bold)),
        Column(
          children: contact.phoneNumbers!
              .map((phone) => getPhone(phone.phoneKind!, phone.phoneNumber!))
              .toList(),
        )
      ],
    );
  }

  Widget getEmail() {
    if (contact.email == null) {
      return const SizedBox();
    }

    return Column(
      children: [
        const Text("Email",
            style: const TextStyle(fontWeight: FontWeight.bold)),
        Text(contact.email!)
      ],
    );
  }

  Widget getAddress() {
    if (contact.address == null) {
      return const SizedBox();
    }

    return Column(
      children: [
        const Text("Adresse",
            style: TextStyle(fontWeight: FontWeight.bold)),
        Text(contact.address!.street!),
        Text(contact.address!.city!),
        Text(contact.address!.zip!),
        Text(contact.address!.country!),
      ],
    );
  }

  Widget getGroupsName() {
    if ((contact.groups == null) || (contact.groups!.isEmpty)) {
      return const SizedBox();
    }

    String pluriel = (contact.groups!.length > 1 ? "s" : "");

    return Column(
      children: [
        Text("Groupe${pluriel}",
            style: const TextStyle(fontWeight: FontWeight.bold)),
        Column(
          children: contact.groups!
              .map((group) => Text(group.name!))
              .toList(),
        )
      ],
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
          const SizedBox(
            height: 10,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                getFirstname(),
                style: const TextStyle(fontSize: 16),
              ),
              const SizedBox(
                width: 5,
              ),
              Text(
                getLastname(),
                style: const TextStyle(fontSize: 16),
              )
            ],
          )
        ],
      )),
      content: Column(
        children: [
          ElevatedButton(
              onPressed: () async {
                context.push('/contacts/edit/${contact.id}').then((value) {
                  context.pop();
                  context.go('/contacts');
                });
              },
              child: const Text("edit")),
          const SizedBox(
            height: 20,
          ),
          getEmail(),
          const SizedBox(
            height: 20,
          ),
          getPhoneNumbers(),
          const SizedBox(
            height: 20,
          ),
          getAddress(),
          const SizedBox(
            height: 20,
          ),
          getGroupsName(),
        ],
      ),
    );
  }
}
