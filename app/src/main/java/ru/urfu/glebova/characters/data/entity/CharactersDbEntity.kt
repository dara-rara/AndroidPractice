package ru.urfu.glebova.characters.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharactersDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "status")
    val status: String?,

    @ColumnInfo(name = "species")
    val species: String?,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "gender")
    val gender: String?,

    @ColumnInfo(name = "origin_name")
    val originName: String?,

    @ColumnInfo(name = "origin_url")
    val originUrl: String?,

    @ColumnInfo(name = "location_name")
    val locationName: String?,

    @ColumnInfo(name = "location_url")
    val locationUrl: String?,

    @ColumnInfo(name = "image")
    val image: String?,

    @ColumnInfo(name = "episode")
    val episode: String?, //JSON

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "created")
    val created: String?
)