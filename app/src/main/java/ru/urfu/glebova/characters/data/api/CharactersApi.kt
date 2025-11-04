package ru.urfu.glebova.characters.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.urfu.glebova.characters.data.model.CharactersListResponse

interface CharactersApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("gender") gender: String? = null
    ): CharactersListResponse
}