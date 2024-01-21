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

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        showDialog(
          context: context,
          builder: (context) {
            return ContactPopUp(contact: contact);
          },
        );
      },
      child: Container(
          padding: const EdgeInsets.all(10),
          color: Colors.white,
          child: Row(children: [
            CircleAvatar(
              radius: 30,
              child: extractInitials(),
            ),
            const SizedBox(width: 10),
            Text(contact.firstname!, style: const TextStyle(fontSize: 18),),
            const SizedBox(width: 5),
            Text(contact.lastname!, style: const TextStyle(fontSize: 18),),
          ])),
    );
  }
}
