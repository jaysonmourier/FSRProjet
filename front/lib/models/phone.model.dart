class Phone {
  int? id;
  String? phoneKind;
  String? phoneNumber;

  Phone({this.id, this.phoneKind, this.phoneNumber});

  Phone.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    phoneKind = json['phoneKind'];
    phoneNumber = json['phoneNumber'];
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'phoneKind': phoneKind,
      'phoneNumber': phoneNumber,
    };
  }

  @override
  String toString() {
    return 'Phone{id: $id, phoneKind: $phoneKind, phoneNumber: $phoneNumber}';
  }
}