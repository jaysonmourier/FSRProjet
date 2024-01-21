class Address {
  String? street;
  String? city;
  String? country;
  String? zip;

  Address({this.street, this.city, this.country, this.zip});

  Address.fromJson(Map<String, dynamic> json) {
    street = json['street'];
    city = json['city'];
    country = json['country'];
    zip = json['zip'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};

    data['street'] = street;
    data['city'] = city;
    data['country'] = country;
    data['zip'] = zip;

    return data;
  }

  @override
  String toString() {
    return 'Address{street: $street, city: $city, country: $country, zip: $zip}';
  }
}