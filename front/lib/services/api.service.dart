import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:front/models/contact.model.dart';

class Api {
  static const String port = "8082";
  static const String baseUrl = 'http://localhost:$port/api';
  static const String contacts = '$baseUrl/contacts';

  static const Map<String, String> headers = {
    'Content-type': 'application/json',
    'Accept': 'application/json',
  };

  Future<List<Contact>?> getAllContacts() async {
    var url = Uri.parse(contacts);
    var response = await http.get(url, headers: headers);
    List<Contact>? contactList;

    if (response.statusCode == 200) {
      List<dynamic> jsonList = jsonDecode(response.body);

      contactList =
          jsonList.map((jsonItem) => Contact.fromJson(jsonItem)).toList();

      if (kDebugMode) {
        for (var contact in contactList) {
          print(contact);
        }
      }

      return contactList;
    } else {
      return null;
    }
  }
}
