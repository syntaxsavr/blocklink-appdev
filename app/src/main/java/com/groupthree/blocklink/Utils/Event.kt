package com.groupthree.blocklink.Utils

class Event {

    var name: String = ""
    var location: Location = Location()
    var description = ""

    constructor(name: String, location: Location, radius: String) {
        this.name = name
        this.location = location
        this.description = radius
    }
}