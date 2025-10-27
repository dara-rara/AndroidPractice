package ru.urfu.glebova.characters.domain.model

import java.time.LocalDateTime

class CharacterEntity (
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginEntity,
    val location: LocationEntity,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: LocalDateTime
) {
    class OriginEntity(
        val name: String,
        val url: String
    )

    class LocationEntity(
        val name: String,
        val url: String
    )
}