package ru.urfu.glebova.characters.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import ru.urfu.glebova.characters.data.api.CharactersApi
import ru.urfu.glebova.characters.data.mapper.CharactersResponseToEntityMapper

class CharactersRepository(
    private val api: CharactersApi,
    private val mapper: CharactersResponseToEntityMapper,
) {
    suspend fun getCharacters() = withContext(Dispatchers.IO) {
        val response = api.getCharacters()
        mapper.mapResponse(response)
    }
}