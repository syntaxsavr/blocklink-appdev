package com.groupthree.blocklink.Utils

class User {

    var name: String = ""
    var location: Location = Location()

    constructor(name: String, location: Location) {
        this.name = name
        this.location = location
    }
}