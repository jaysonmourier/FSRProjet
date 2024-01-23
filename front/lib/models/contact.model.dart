import 'dart:io';

import 'package:front/models/address.model.dart';
import 'package:front/models/group.model.dart';
import 'package:front/models/phone.model.dart';

class Contact {
  int? id;
  String? firstname;
  String? lastname;
  String? email;
  List<Phone>? phoneNumbers;
  Address? address;
  List<Group>? groups;

  Contact({this.id, this.firstname, this.lastname, this.email, this.address, this.phoneNumbers, this.groups});

  Contact.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    firstname = json['firstname'];
    lastname = json['lastname'];
    email = json['email'];
    address = json['address'] != null ? Address.fromJson(json['address']) : null;
    phoneNumbers = json['phoneNumbers']
        ?.map<Phone>((phone) => Phone.fromJson(phone))
        .toList();
    groups = json['groups'] != null ? (json['groups'] as List).map((group) => Group.fromJson(group)).toList() : null;
    print(this.toString());
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'firstname': firstname,
      'lastname': lastname,
      'email': email,
      'phoneNumbers': phoneNumbers?.map((phone) => phone.toJson()).toList(),
      'address': address?.toJson(),
      'groups': groups?.map((group) => group.toJson()).toList(),
    };
  }

  @override
  String toString() {
    return 'Contact{id: $id, firstname: $firstname, lastname: $lastname, email: $email, phoneNumbers: $phoneNumbers, address: $address, groups: $groups}';
  }
}
