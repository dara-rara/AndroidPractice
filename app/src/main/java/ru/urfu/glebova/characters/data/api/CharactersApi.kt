package ru.urfu.glebova.characters.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.urfu.glebova.characters.data.model.CharactersListResponse

interface CharactersApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("status") orderBy: String = STATUS_ASC
    ): CharactersListResponse

    companion object {
        private const val STATUS_ASC = "alive"
    }
}