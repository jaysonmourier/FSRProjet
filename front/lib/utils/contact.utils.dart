import 'package:front/models/contact.model.dart';

class ContactUtils {
  String extractInitials(Contact contact) {
    if (contact.firstname == null || contact.lastname == null) {
      return "Aa";
    }
    return contact.firstname![0].toUpperCase() +
        contact.lastname![0].toUpperCase();
  }
}
