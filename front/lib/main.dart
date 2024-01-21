import 'package:flutter/material.dart';
import 'package:front/views/contacts.view.dart';
import 'package:go_router/go_router.dart';

void main() {
  final GoRouter router = GoRouter(
    initialLocation: '/contacts',
    routes: [
      GoRoute(
        path: '/contacts',
        builder: (context, state) => const ContactsView(),
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
      routerConfig: router,
      title: "FSR Project",
    );
  }
}
