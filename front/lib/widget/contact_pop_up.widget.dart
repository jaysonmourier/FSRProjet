import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/models/phone.model.dart';
import 'package:front/services/api.service.dart';
import 'package:front/utils/contact.utils.dart';
import 'package:front/widget/group_adder.widger.dart';
import 'package:go_router/go_router.dart';

class ContactPopUp extends StatefulWidget {
  Contact contact;

  ContactPopUp({super.key, required this.contact});

  @override
  State<ContactPopUp> createState() => _ContactPopUpState();
}

class _ContactPopUpState extends State<ContactPopUp> {
  final Api api = Api();

  Widget extractInitials() {
    return Text(ContactUtils().extractInitials(widget.contact));
  }

  String getFirstname() {
    if (widget.contact.firstname == null) {
      return "Unknown";
    }
    return widget.contact.firstname!;
  }

  String getLastname() {
    if (widget.contact.lastname == null) {
      return "Unknown";
    }
    return widget.contact.lastname!;
  }

  Widget getPhone(String kind, String number) {
    return Container(
      margin: const EdgeInsets.only(top: 5, bottom: 5),
      child: Column(
        children: [
          Text(number),
          Text(kind, style: const TextStyle(fontSize: 12)),
        ],
      ),
    );
  }

  Widget getPhoneNumbers() {
    if ((widget.contact.phoneNumbers == null) ||
        (widget.contact.phoneNumbers!.isEmpty)) {
      return const Text("No phone number");
    }

    String pluriel = (widget.contact.phoneNumbers!.length > 1 ? "s" : "");

    return Column(
      children: [
        Text("Phone number${pluriel}",
            style: const TextStyle(fontWeight: FontWeight.bold)),
        Column(
          children: widget.contact.phoneNumbers!
              .map((phone) => getPhone(phone.phoneKind!, phone.phoneNumber!))
              .toList(),
        )
      ],
    );
  }

  Widget getEmail() {
    if (widget.contact.email == null) {
      return const SizedBox();
    }

    return Column(
      children: [
        const Text("Email",
            style: const TextStyle(fontWeight: FontWeight.bold)),
        Text(widget.contact.email!)
      ],
    );
  }

  Widget getAddress() {
    if (widget.contact.address == null) {
      return const SizedBox();
    }

    return Column(
      children: [
        const Text("Adresse", style: TextStyle(fontWeight: FontWeight.bold)),
        Text("Rue: ${widget.contact.address!.street!}"),
        Text("Ville: ${widget.contact.address!.city!}"),
        Text("Code ZIP: ${widget.contact.address!.zip!}"),
        Text("Pays: ${widget.contact.address!.country!}"),
      ],
    );
  }

  Widget addGroupButton(
      BuildContext context, int contactId, VoidCallback onEnd) {
    return ElevatedButton(
        onPressed: () async {
          showDialog(
            context: context,
            builder: (context) {
              return GroupAdder(contact: widget.contact);
            },
          ).then((value) => onEnd.call());
        },
        child: const Text("Ajouter un groupe"));
  }

  Widget getGroupsName() {
    if ((widget.contact.groups == null) || (widget.contact.groups!.isEmpty)) {
      return const SizedBox();
    }

    String pluriel = (widget.contact.groups!.length > 1 ? "s" : "");

    return Column(
      children: [
        Text("Groupe${pluriel}",
            style: const TextStyle(fontWeight: FontWeight.bold)),
        Column(
          children:
              widget.contact.groups!.map((group) => Text(group.name!)).toList(),
        )
      ],
    );
  }

  void update() {
    setState(() {});
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
      content: FutureBuilder(
        future: api.getContact(widget.contact.id!),
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            widget.contact = snapshot.data as Contact;
            return Column(
              children: [
                ElevatedButton(
                    onPressed: () async {
                      context
                          .push('/contacts/edit/${widget.contact.id}')
                          .then((value) {
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
                addGroupButton(context, widget.contact.id!, update),
                getGroupsName(),
              ],
            );
          }
          return const CircularProgressIndicator();
        },
      ),
    );
  }
}
