class Group {
  int? id;
  String? name;
  int? length;

  Group({this.id, this.name, this.length});

  Group.fromJson(Map<String, dynamic> json) {
    id = json['groupId'];
    name = json['groupName'];
    length = json['length'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};

    if(id != null) data['groupId'] = id;
    if(name != null) data['groupName'] = name;
    if(length != null) data['length'] = length;
    
    return data;
  }

  @override
  String toString() {
    return 'Group{id: $id, name: $name, length: $length}';
  }
}