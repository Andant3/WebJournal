package com.example.webjournal.model

import com.google.firebase.Timestamp

data class Journal(
    val title: String,
    val description: String,
    var imageURL: String,

    val userID: String,
    val timeAdded: Timestamp,
    val username: String
) {
}