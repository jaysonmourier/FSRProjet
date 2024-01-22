import 'package:flutter/material.dart';
import 'package:front/models/contact.model.dart';
import 'package:front/services/api.service.dart';
import 'package:front/views/contacts.view.dart';
import 'package:front/views/create_contact.view.dart';
import 'package:go_router/go_router.dart';

void main() {
  final GoRouter router = GoRouter(
    initialLocation: '/contacts',
    routes: [
      GoRoute(
        path: '/contacts',
        builder: (context, state) => ContactsView(),
      ),
      GoRoute(
        path: '/contacts/create',
        builder: (context, state) => const CreateContactView(),
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
