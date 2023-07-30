package com.groupthree.blocklink.Events.Utils

class Event {

    var name: String = ""
    var location: Location = Location()
    var description = ""
    var username = ""

    constructor() {
        this.name = "No name"
        this.location = Location()
        this.description = "No description"
        this.username = "No username"
    }

    constructor(name: String, location: Location, description: String, username: String) {
        this.name = name
        this.location = location
        this.description = description
        this.username = username
    }
}