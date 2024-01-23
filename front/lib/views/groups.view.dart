import 'package:flutter/material.dart';
import 'package:front/models/group.model.dart';
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

  @override
  void initState() {
    super.initState();
    viewModel.fetchGroups();
  }

  @override
  void dispose() {
    viewModel.dispose();
    super.dispose();
  }

  void _refreshGroups() {
    setState(() {
      viewModel.fetchGroups();
    });
  }

  void _addGroupDialog() async {
    final result = await showDialog<bool>(
      context: context,
      builder: (context) => CreateGroupDialog(viewModel: viewModel),
    );
    if (result == true) {
      _refreshGroups();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(title: 'Mes Groupes'),
      floatingActionButton: FloatingActionButton(
        onPressed: _addGroupDialog,
        backgroundColor: Colors.blue,
        child: const Icon(Icons.add),
      ),
      body: StreamBuilder<List<Group>>(
        stream: viewModel.groupsStream,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasError) {
            return Text('Erreur: ${snapshot.error}');
          }
          if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('Aucun groupe trouvé.'));
          }
          final groups = snapshot.data!;
          return _buildGroupList(groups);
        },
      ),
    );
  }

  Widget _buildGroupList(List<Group> groups) {
    return ListView.builder(
      itemCount: groups.length,
      itemBuilder: (context, index) {
        final group = groups[index];
        return _buildGroupListItem(group, index);
      },
    );
  }

  Widget _buildGroupListItem(Group group, int index) {
    return Dismissible(
      key: Key(group.id.toString()),
      background: _buildDismissBackground(),
      direction: DismissDirection.endToStart,
      onDismissed: (direction) => _deleteGroup(group, index),
      child: ListTile(
        title: _buildGroupTitle(group),
        onTap: () => context.go('/groups/${group.id}'),
      ),
    );
  }

  Widget _buildDismissBackground() {
    return Container(
      color: Colors.red,
      alignment: Alignment.centerRight,
      child: const Padding(
        padding: EdgeInsets.only(right: 20.0),
        child: Icon(Icons.delete, color: Colors.white),
      ),
    );
  }

  Widget _buildGroupTitle(Group group) {
    return Row(
      children: [
        Text(group.name!),
        const Spacer(),
        Text('${group.length} contacts'),
      ],
    );
  }

  void _deleteGroup(Group group, int index) async {
    final isDeleted = await viewModel.deleteGroup(group.id!);
    if (isDeleted) {
      _refreshGroups();
    }
  }
}

class CreateGroupDialog extends StatelessWidget {
  final GroupsViewModel viewModel;

  const CreateGroupDialog({Key? key, required this.viewModel}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('Créer un groupe'),
      content: TextField(
        controller: viewModel.groupNameController,
        decoration: const InputDecoration(
          labelText: 'Nom du groupe',
        ),
      ),
      actions: [
        TextButton(
          onPressed: () => Navigator.pop(context, false),
          child: const Text('Annuler'),
        ),
        TextButton(
          onPressed: () async {
            final success = await viewModel.create();
            Navigator.pop(context, success);
          },
          child: const Text('Créer'),
        ),
      ],
    );
  }
}

