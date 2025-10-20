package ru.urfu.glebova.characters.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CharactersListResponse(
    val info: PageInfo?,
    val results: List<Character>?
)

@Keep
@Serializable
data class PageInfo(
    val count: Int?,
    val pages: Int?,
    val next: String?,
    val prev: String?
)

@Keep
@Serializable
data class Character(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: Origin?,
    val location: Location?,
    val image: String?,
    val episode: List<String>?,
    val url: String?,
    val created: String?
)

@Keep
@Serializable
data class Origin(
    val name: String?,
    val url: String?
)

@Keep
@Serializable
class Location(
    val name: String?,
    val url: String?
)