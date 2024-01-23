import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';

class GroupContactView extends StatelessWidget {
  final int groupId;
  final List<Contact>? contacts;

  const GroupContactView(
      {super.key, required this.groupId, required this.contacts});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Contacts du groupe"),
      ),
      body: ListView.builder(
        itemCount: contacts!.length,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text("${contacts![index].firstname!} ${contacts![index].lastname!}"),
            subtitle: Text(contacts![index].email!),
          );
        },
      ),
    );
  }
}
