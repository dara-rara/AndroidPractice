package ru.urfu.glebova.characters.data.mapper

import kotlinx.serialization.json.Json
import ru.urfu.glebova.characters.data.entity.CharactersDbEntity
import ru.urfu.glebova.characters.domain.model.CharacterEntity
import java.time.LocalDateTime

class CharacterDbMapper {

    fun toDbEntity(character: CharacterEntity): CharactersDbEntity {
        return CharactersDbEntity(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            type = character.type,
            gender = character.gender,
            originName = character.origin.name,
            originUrl = character.origin.url,
            locationName = character.location.name,
            locationUrl = character.location.url,
            image = character.image,
            episode = Json.encodeToString(character.episode),
            url = character.url,
            created = character.created.toString()
        )
    }

    fun toDomainEntity(dbEntity: CharactersDbEntity): CharacterEntity {
        return CharacterEntity(
            id = dbEntity.id,
            name = dbEntity.name ?: "",
            status = dbEntity.status ?: "",
            species = dbEntity.species ?: "",
            type = dbEntity.type ?: "",
            gender = dbEntity.gender ?: "",
            origin = CharacterEntity.OriginEntity(
                name = dbEntity.originName ?: "",
                url = dbEntity.originUrl ?: ""
            ),
            location = CharacterEntity.LocationEntity(
                name = dbEntity.locationName ?: "",
                url = dbEntity.locationUrl ?: ""
            ),
            image = dbEntity.image ?: "",
            episode = dbEntity.episode?.let { Json.decodeFromString<List<String>>(it) } ?: emptyList(),
            url = dbEntity.url ?: "",
            created = LocalDateTime.parse(dbEntity.created ?: "")
        )
    }

    fun toDomainEntityList(dbEntities: List<CharactersDbEntity>): List<CharacterEntity> {
        return dbEntities.map { toDomainEntity(it) }
    }

    fun toDbEntityList(characters: List<CharacterEntity>): List<CharactersDbEntity> {
        return characters.map { toDbEntity(it) }
    }
}