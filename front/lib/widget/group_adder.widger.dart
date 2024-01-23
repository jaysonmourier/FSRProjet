import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/models/group.model.dart';
import 'package:front/services/api.service.dart';

class GroupAdder extends StatefulWidget {
  final Contact contact;

  const GroupAdder({super.key, required this.contact});

  @override
  _GroupAdderState createState() => _GroupAdderState();
}

class _GroupAdderState extends State<GroupAdder> {
  late Map<int, bool> groupCheckStatus;
  final Api api = Api();

  @override
  void initState() {
    super.initState();
    groupCheckStatus = {};
    if (widget.contact.groups != null) {
      for (var group in widget.contact.groups!) {
        groupCheckStatus[group.id!] = true;
      }
    }
  }

  Future<void> handleCheckboxChanged(bool? value, Group group) async {
    if (value == true) {
      bool success = await api.addUserToGroup(group.id!, widget.contact.id!);
      print("success: $success");
      if (success) {
        setState(() {
          groupCheckStatus[group.id!] = true;
        });
      } else {
        // Gérer l'erreur ici, par exemple en affichant un message
      }
    } else {
      bool success = await api.removeUserFromGroup(group.id!, widget.contact.id!);
      if(success) {
        setState(() {
          groupCheckStatus[group.id!] = false;
        });
      }
    }
  }

  Widget checkbox(Group group) {
    return Row(
      children: [
        Checkbox(
          value: groupCheckStatus[group.id] ?? false,
          onChanged: (value) {
            handleCheckboxChanged(value, group);
          },
        ),
        Expanded(child: Text(group.name!)),
      ],
    );
  }

  Widget groupList(List<Group> groups) {
    for (var group in groups) {
      if (!groupCheckStatus.containsKey(group.id)) {
        groupCheckStatus[group.id!] = false;
      }
    }

    return Column(
      mainAxisSize: MainAxisSize.min,
      children: groups.map((group) => checkbox(group)).toList(),
    );
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text("Ajouter le contact à un groupe"),
      content: FutureBuilder(
        future: api.getAllGroups(),
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            List<Group> groups = snapshot.data as List<Group>;
            return SingleChildScrollView(
              child: ConstrainedBox(
                constraints: const BoxConstraints(maxHeight: 300),
                child: groupList(groups),
              ),
            );
          } else {
            return const CircularProgressIndicator();
          }
        },
      ),
    );
  }
}

