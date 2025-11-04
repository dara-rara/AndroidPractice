package ru.urfu.glebova.characters.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.urfu.glebova.characters.data.repository.CharactersRepository
import ru.urfu.glebova.characters.domain.model.CharacterEntity

class CharactersInteractor(
    private val repository: CharactersRepository
) {
    suspend fun getCharacters(gender: String) = repository.getCharacters(gender)

    fun updateGenderFilter(): Flow<String> = repository.updateGenderFilter()

    suspend fun setGenderFilter(gender: String) = repository.setGenderFilter(gender)

    suspend fun saveFavorite(character: CharacterEntity) = repository.saveFavorite(character)

    suspend fun removeFavorite(character: CharacterEntity) = repository.removeFavorite(character)

    suspend fun getFavorites() = repository.getFavorites()
}
