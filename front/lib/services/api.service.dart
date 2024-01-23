import 'dart:convert';
import 'package:front/models/group.model.dart';
import 'package:front/models/phone.model.dart';
import 'package:http/http.dart' as http;
import 'package:front/models/contact.model.dart';

class Api {
  static const String port = "8082";
  static const String baseUrl = 'http://192.168.1.80:$port/api';
  static const String contacts = '$baseUrl/contacts';
  static const String groups = '$baseUrl/usergroups';

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

  Future<Contact?> getContact(int id) async {
    var url = Uri.parse('$contacts/$id');
    try {
      var response = await http.get(url, headers: headers);
      if (response.statusCode == 200) {
        String decoded = utf8.decode(response.bodyBytes);
        Map<String, dynamic> json = jsonDecode(decoded);
        return Contact.fromJson(json);
      } else {
        return null;
      }
    } catch (e) {
      print('Erreur lors de la récupération du contact: $e');
      return null;
    }
  }

  Future<bool> deleteContact(int i) async {
    var url = Uri.parse('$contacts/$i');
    var r = await http.delete(url, headers: headers);
    if (r.statusCode == 200) {
      return true;
    } else {
      return false;
    }
  }

  Future<bool> updateContact(Contact contact, List<Phone> toDelete) async {
    var url = Uri.parse('$contacts/${contact.id}');
    var r = await http.put(url,
        headers: headers, body: jsonEncode(contact.toJson()));

    for (var phone in toDelete) {
      await deletePhoneNumber(contact.id!, phone.id!);
    }

    if (r.statusCode == 200) {
      return Future.value(true);
    } else {
      return Future.value(false);
    }
  }

  Future<void> deletePhoneNumber(int contactId, int phoneId) async {
    var url = Uri.parse('$contacts/$contactId/phone/$phoneId');
    await http.delete(url, headers: headers);
  }

  // GROUPS

  Future<List<Group>?> getAllGroups() async {
    var url = Uri.parse(groups);
    var response = await http.get(url, headers: headers);

    List<Group>? groupList;

    if (response.statusCode == 200) {
      String decoded = utf8.decode(response.bodyBytes);
      List<dynamic> jsonList = jsonDecode(decoded);

      groupList = jsonList.map((jsonItem) => Group.fromJson(jsonItem)).toList();

      return groupList;
    } else {
      return null;
    }
  }

  Future<bool> addGroup(Group group) async {
    var url = Uri.parse(groups);
    var response = await http.post(url,
        headers: headers, body: jsonEncode(group.toJson()));

    if (response.statusCode == 201) {
      return true;
    } else {
      return false;
    }
  }

Future<bool> deleteGroup(int groupId) async {
  try {
    var url = Uri.parse('$groups/$groupId');
    var response = await http.delete(url, headers: headers);
    if (response.statusCode == 204) {
      return true;
    } else {
      return false;
    }
  } catch (e) {
    return false;
  }
}

}
