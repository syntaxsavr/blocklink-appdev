package com.groupthree.blocklink.Events.Utils

class User {

    var name: String = ""
    var location: Location = Location()

    constructor(name: String, location: Location) {
        this.name = name
        this.location = location
    }
}