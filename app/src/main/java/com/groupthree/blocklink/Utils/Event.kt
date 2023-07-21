package com.groupthree.blocklink.Utils

class Event {

    var name: String = ""
    var location: Location = Location()
    var radius: Double = 0.0

    constructor(name: String, location: Location, radius: Double) {
        this.name = name
        this.location = location
        this.radius = radius
    }
}