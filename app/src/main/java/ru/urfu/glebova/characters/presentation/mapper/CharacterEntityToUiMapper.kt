package ru.urfu.glebova.characters.presentation.mapper

import ru.urfu.glebova.characters.domain.model.CharacterEntity
import ru.urfu.glebova.characters.presentation.model.CharacterUiModel
import java.time.LocalDateTime

class CharacterEntityToUiMapper {
    fun toCharacterEntity(character: CharacterUiModel): CharacterEntity {
        return CharacterEntity(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            type = character.type,
            gender = character.gender,
            origin = CharacterEntity.OriginEntity(
                name = character.origin.name,
                url = character.origin.url
            ),
            location = CharacterEntity.LocationEntity(
                name = character.location.name,
                url = character.location.url
            ),
            image = character.image,
            episode = character.episode,
            url = character.url,
            created = parseCreatedDate(character.created)
        )
    }

    private fun parseCreatedDate(dateString: String): LocalDateTime {
        return try {
            LocalDateTime.parse(dateString)
        } catch (e: Exception) {
            LocalDateTime.now()
        }
    }
}