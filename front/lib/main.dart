import 'package:flutter/material.dart';
import 'package:front/services/api.service.dart';
import 'package:front/views/contacts.view.dart';
import 'package:front/views/groups.view.dart';
import 'package:front/widget/add_contact.widget.dart';

void main() {
  runApp(MaterialApp(
    title: 'Projet FSR',
    theme: ThemeData(
      colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
      useMaterial3: true,
    ),
    debugShowCheckedModeBanner: false,
    home: FSRProject(),
  ));
}

class FSRProject extends StatefulWidget {
  @override
  _FSRProjectState createState() => _FSRProjectState();
}

class _FSRProjectState extends State<FSRProject> {
  int _selectedIndex = 0;
  final _pageController = PageController();

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
      _pageController.animateToPage(index,
          duration: const Duration(milliseconds: 300), curve: Curves.easeIn);
    });
  }

  final List<String> _titles = ['Mes contacts', 'Mes groupes'];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(_titles[_selectedIndex]),
        actions: [
          IconButton(
            icon: const Icon(Icons.add),
            onPressed: () {
              showDialog(
                  context: context,
                  builder: (context) {
                    return AlertDialog(
                      title: const Text("Ajouter un contact"),
                      content: AddContactWidget(onValid: () => setState(() {
                        Navigator.pop(context);
                      }),),
                    );
                  });
            },
            padding: const EdgeInsets.only(right: 10),
          ),
        ],
      ),
      body: PageView(
        controller: _pageController,
        onPageChanged: (index) {
          setState(() {
            _selectedIndex = index;
          });
        },
        physics: const NeverScrollableScrollPhysics(),
        children: const <Widget>[
          ContactsView(),
          GroupsView(),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
              icon: Icon(Icons.contacts), label: 'Contacts'),
          BottomNavigationBarItem(icon: Icon(Icons.group), label: 'Groupes'),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.amber[800],
        onTap: _onItemTapped,
      ),
    );
  }
}
