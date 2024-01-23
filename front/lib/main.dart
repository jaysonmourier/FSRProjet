import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/services/api.service.dart';
import 'package:front/views/contacts.view.dart';
import 'package:front/views/create_contact.view.dart';
import 'package:front/views/group_contact.view.dart';
import 'package:front/views/groups.view.dart';
import 'package:go_router/go_router.dart';

void main() {
  final GoRouter router = GoRouter(
    initialLocation: '/contacts',
    routes: [
      GoRoute(
        path: '/groups',
        builder: (context, state) => const GroupsView(),
      ),
      GoRoute(
        path: '/groups/:id',
        builder: (context, state) {
          final String? rawId = state.pathParameters['id']?.trim();

          if (rawId == null || rawId.isEmpty) {
            return const GroupsView();
          }

          final int? id = int.tryParse(rawId);

          if (id == null) {
            return const GroupsView();
          }

          return FutureBuilder<List<Contact>>(
            future: Api().getContactsByGroup(id),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return const CircularProgressIndicator();
              } else if (snapshot.hasError) {
                return Text('Erreur: ${snapshot.error}');
              } else if (snapshot.hasData) {
                return GroupContactView(
                  contacts: snapshot.data,
                  groupId: id,
                );
              } else {
                return const Text('Aucun contact trouvé.');
              }
            },
          );
        },
      ),
      GoRoute(
        path: '/contacts',
        builder: (context, state) => const ContactsView(),
      ),
      GoRoute(
        path: '/contacts/create',
        builder: (context, state) => CreateContactView(),
      ),
      GoRoute(
        path: '/contacts/edit/:id',
        builder: (context, state) {
          final String? rawId = state.pathParameters['id']?.trim();

          if (rawId == null || rawId.isEmpty) {
            return ContactsView();
          }

          final int? id = int.tryParse(rawId);

          if (id == null) {
            return ContactsView();
          }

          return FutureBuilder<Contact?>(
            future: Api().getContact(id),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return const CircularProgressIndicator();
              } else if (snapshot.hasError) {
                return Text('Erreur: ${snapshot.error}');
              } else if (snapshot.hasData) {
                return CreateContactView(
                  contact: snapshot.data,
                  editing: true,
                );
              } else {
                return const Text('Aucun contact trouvé.');
              }
            },
          );
        },
      ),
    ],
  );

  runApp(FSRApp(
    router: router,
  ));
}

class FSRApp extends StatelessWidget {
  final GoRouter router;

  const FSRApp({super.key, required this.router});

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      debugShowCheckedModeBanner: false,
      routerConfig: router,
      title: "FSR Project",
    );
  }
}
