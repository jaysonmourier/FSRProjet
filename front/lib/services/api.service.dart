import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:front/models/contact.model.dart';

class Api {
  static const String port = "8082";
  static const String baseUrl = 'http://192.168.1.80:$port/api';
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
      String decoded = utf8.decode(response.bodyBytes);
      List<dynamic> jsonList = jsonDecode(decoded);

      contactList =
          jsonList.map((jsonItem) => Contact.fromJson(jsonItem)).toList();

      return contactList;
    } else {
      return null;
    }
  }

  Future<bool> addContact(Contact contact) async {
    var url = Uri.parse(contacts);
    var response = await http.post(url,
        headers: headers, body: jsonEncode(contact.toJson()));

    if (response.statusCode == 201) {
      return true;
    } else {
      return false;
    }
  }
}
