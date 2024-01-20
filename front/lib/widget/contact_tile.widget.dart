import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/utils/contact.utils.dart';
import 'package:front/widget/contact_pop_up.widget.dart';

class ContactTile extends StatelessWidget {
  final Contact contact;

  const ContactTile({super.key, required this.contact});

  Widget extractInitials() {
    return Text(ContactUtils().extractInitials(contact));
  }

  Widget getEmail() {
    if (contact.email == null) {
      return const Text("No email");
    }
    return Text(contact.email!, maxLines: 1, overflow: TextOverflow.ellipsis);
  }

  Widget getPhoneNumber() {
    if ((contact.phoneNumbers == null) || (contact.phoneNumbers!.isEmpty)) {
      return const Text("No phone number");
    }
    return Text(contact.phoneNumbers![0].phoneNumber!);
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        showDialog(context: context, builder:(context) {
          return ContactPopUp(contact: contact);
        },);
      },
      child: Container(
          padding: const EdgeInsets.all(10),
          color: Colors.grey[300],
          child: Row(children: [
            CircleAvatar(
              radius: 30,
              child: extractInitials(),
            ),
            const SizedBox(width: 10),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(contact.firstname!),
                  Text(contact.lastname!),
                ],
              ),
            ),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  getEmail(),
                  getPhoneNumber(),
                ],
              ),
            ),
          ])),
    );
  }
}
