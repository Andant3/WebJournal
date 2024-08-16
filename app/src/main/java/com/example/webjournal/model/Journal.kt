package com.example.webjournal.model

import com.google.firebase.Timestamp

data class Journal(
    private val title: String,
    private val description: String,
    private val imageURL: String,

    private val userID: String,
    private val timeAdded: Timestamp,
    private val username: String
) {
}