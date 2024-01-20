import 'package:front/models/phone.model.dart';

class Contact {
  int? id;
  String? firstname;
  String? lastname;
  String? email;
  List<Phone>? phoneNumbers;

  Contact({this.id, this.firstname, this.lastname});

  Contact.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    firstname = json['firstname'];
    lastname = json['lastname'];
    email = json['email'];
    phoneNumbers = json['phoneNumbers']
        ?.map<Phone>((phone) => Phone.fromJson(phone))
        .toList();
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'firstname': firstname,
      'lastname': lastname,
      'email': email,
      'phoneNumbers': phoneNumbers?.map((phone) => phone.toJson()).toList()
    };
  }

  @override
  String toString() {
    return 'Contact{id: $id, firstname: $firstname, lastname: $lastname, email: $email, phoneNumbers: $phoneNumbers}';
  }
}
