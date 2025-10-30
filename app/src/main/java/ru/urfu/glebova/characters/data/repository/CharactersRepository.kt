package ru.urfu.glebova.characters.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.urfu.glebova.characters.data.api.CharactersApi
import ru.urfu.glebova.characters.data.db.CharactersDatabase
import ru.urfu.glebova.characters.data.entity.CharactersDbEntity
import ru.urfu.glebova.characters.data.mapper.CharacterDbMapper
import ru.urfu.glebova.characters.data.mapper.CharactersResponseToEntityMapper
import ru.urfu.glebova.characters.data.model.Location
import ru.urfu.glebova.characters.data.model.Origin
import ru.urfu.glebova.characters.domain.model.CharacterEntity

class CharactersRepository(
    private val api: CharactersApi,
    private val mapper: CharactersResponseToEntityMapper,
    private val dataStore: DataStore<Preferences>,
    private val db: CharactersDatabase,
    private val characterDbMapper: CharacterDbMapper,
) {
    private val genderKey = stringPreferencesKey(GENDER)
    suspend fun getCharacters(gender: String) = withContext(Dispatchers.IO) {
        val response = if (gender.isNotEmpty()) {
            api.getCharacters(gender)
        } else {
            api.getCharacters()
        }
        mapper.mapResponse(response)
    }

    suspend fun setGenderFilter(gender: String) = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            if (gender.isNotEmpty()) {
                preferences[genderKey] = gender
            } else {
                preferences.remove(genderKey)
            }
        }
    }

    fun updateGenderFilter(): Flow<String> = dataStore.data.map { it[genderKey] ?: "" }

    suspend fun saveFavorite(character: CharacterEntity) = withContext(Dispatchers.IO) {
        db.charactersDao().insert(
            characterDbMapper.toDbEntity(character)
        )
    }

    suspend fun removeFavorite(character: CharacterEntity) = withContext(Dispatchers.IO) {
        db.charactersDao().delete(characterDbMapper.toDbEntity(character))
    }

    suspend fun getFavorites(): List<CharacterEntity> = withContext(Dispatchers.IO) {
        characterDbMapper.toDomainEntityList(db.charactersDao().getAll())
    }
    companion object {
        private const val GENDER = "GENDER"
    }
}