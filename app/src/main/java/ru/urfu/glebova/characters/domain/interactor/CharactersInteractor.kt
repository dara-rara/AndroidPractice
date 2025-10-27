package ru.urfu.glebova.characters.domain.interactor

import ru.urfu.glebova.characters.data.repository.CharactersRepository

class CharactersInteractor(
    private val repository: CharactersRepository
) {
    suspend fun getCharacters() = repository.getCharacters()
}
