package ru.urfu.glebova.characters.data.mapper

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromEpisodeList(episodes: List<String>?): String? {
        return episodes?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toEpisodeList(episodesString: String?): List<String>? {
        return episodesString?.let {
            Json.decodeFromString<List<String>>(it)
        }
    }
}