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
        builder: (context, state) => const ContactsView(),
      ),
      GoRoute(
        path: '/contacts/create',
        builder: (context, state) => CreateContactView(),
      ),
      GoRoute(
        path: '/contacts/edit:id',
        builder: (context, state) {
          final id = state.pathParameters['id'];

          // Utilisation de FutureBuilder pour gérer l'opération asynchrone
          return FutureBuilder<Contact?>(
            future: Api()
                .getContact(int.parse(id!)), // Appel asynchrone à getContact
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return CircularProgressIndicator(); // Afficher un indicateur de chargement
              } else if (snapshot.hasError) {
                return Text('Erreur: ${snapshot.error}');
              } else if (snapshot.hasData) {
                return CreateContactView(
                    contact: snapshot
                        .data); // Création de la vue avec les données du contact
              } else {
                return Text('Aucun contact trouvé.');
              }
            },
          );
        },
      ),
    ],
  );

  runApp(FSRApp(router: router));
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
