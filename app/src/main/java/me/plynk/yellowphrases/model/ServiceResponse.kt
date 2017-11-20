package me.plynk.yellowphrases.model

import java.io.Serializable

data class Character(
        val firstName: String,
        val lastName: String,
        val _id: String,
        val picture: String
): Serializable{
    val fullName get() = "$firstName $lastName"
}

data class Phrase (
        val phrase: String,
        val character: String,
        val _id: String
)

data class SaveRequest(
        val status: Boolean,
        val description: String
)

data class Saved (
        val phraseId: String
)