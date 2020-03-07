package com.example.docusign.model

class Photo(id: String, largeImageURL: String, likes: String, user: String) {

    var id: String
    var largeImageURL: String
    var likes: String
    var user: String

    init {
        this.id = id
        this.largeImageURL = largeImageURL
        this.likes = likes
        this.user = user
    }

}