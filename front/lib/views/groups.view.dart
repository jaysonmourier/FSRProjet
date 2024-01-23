import 'package:flutter/material.dart';
import 'package:front/viewmodels/groups.viewmodel.dart';
import 'package:front/widget/appbar/custom_app_bar.widget.dart';
import 'package:go_router/go_router.dart';

class GroupsView extends StatefulWidget {
  const GroupsView({super.key});

  @override
  State<GroupsView> createState() => _GroupsViewState();
}

class _GroupsViewState extends State<GroupsView> {
  final GroupsViewModel viewModel = GroupsViewModel();
  Key futuerBuilderKey = UniqueKey();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(title: 'Mes Groupes'),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          showDialog(
            context: context,
            builder: (context) {
              return AlertDialog(
                title: const Text('Créer un groupe'),
                content: TextField(
                  controller: viewModel.getGroupNameController(),
                  decoration: const InputDecoration(
                    labelText: 'Nom du groupe',
                  ),
                ),
                actions: [
                  TextButton(
                    onPressed: () {
                      context.pop();
                    },
                    child: const Text('Annuler'),
                  ),
                  TextButton(
                    onPressed: () {
                      viewModel.create().then((value) {
                        print("value: $value");
                        if (value) {
                          setState(() {
                            context.pop();
                            futuerBuilderKey = UniqueKey();
                          });
                        }
                      });
                    },
                    child: const Text('Créer'),
                  ),
                ],
              );
            },
          ).then((value) => setState(() {}));
        },
        backgroundColor: Colors.blue,
        child: const Icon(Icons.add),
      ),
      body: FutureBuilder(
        key: futuerBuilderKey,
        future: viewModel.api.getAllGroups(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Text('Erreur: ${snapshot.error}');
          } else if (snapshot.hasData) {
            final data = snapshot.data;
            if ((data == null) || (data.isEmpty)) {
              return const Center(child: Text('Aucun groupe trouvé.'));
            }
            return ListView.builder(
              itemCount: data.length,
              itemBuilder: (context, index) {
                final group = data[index];
                return Dismissible(
                  key: Key(group.id.toString()),
                  background: Container(
                    color: Colors.red,
                    alignment: Alignment.centerRight,
                    child: const Padding(
                      padding: EdgeInsets.only(right: 20.0),
                      child: Icon(Icons.delete, color: Colors.white),
                    ),
                  ),
                  direction: DismissDirection.endToStart,
                  onDismissed: (direction) async {
                    viewModel.api.deleteGroup(group.id!).then((isDeleted) {
                      if (isDeleted) {
                        setState(() {
                          data.removeAt(index);
                          futuerBuilderKey = UniqueKey();
                        });
                      } else {}
                    });
                  },
                  child: ListTile(
                    title: Text(group.name!),
                    onTap: () {
                      context.go('/groups/${group.id}');
                    },
                  ),
                );
              },
            );
          } else {
            return const Text('Aucun groupe trouvé.');
          }
        },
      ),
    );
  }
}
