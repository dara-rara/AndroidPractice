package ru.urfu.glebova.characters.data.mapper

import ru.urfu.glebova.characters.data.model.CharactersListResponse
import ru.urfu.glebova.characters.domain.model.CharacterEntity
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CharactersResponseToEntityMapper {
    fun mapResponse(response: CharactersListResponse): List<CharacterEntity> {
        return response.results?.map { character ->
            CharacterEntity(
                id = character.id ?: 0,
                name = character.name.orEmpty(),
                status = character.status.orEmpty(),
                species = character.species.orEmpty(),
                type = character.type.orEmpty(),
                gender = character.gender.orEmpty(),
                origin = CharacterEntity.OriginEntity(
                    name = character.origin?.name.orEmpty(),
                    url = character.origin?.url.orEmpty()
                ),
                location = CharacterEntity.LocationEntity(
                    name = character.location?.name.orEmpty(),
                    url = character.location?.url.orEmpty()
                ),
                image = character.image.orEmpty(),
                episode = character.episode ?: emptyList(),
                url = character.url.orEmpty(),
                created = parseDateTime(character.created)
            )
        } ?: emptyList()
    }

    private fun parseDateTime(dateTimeString: String?): LocalDateTime {
        return try {
            dateTimeString?.let {
                ZonedDateTime.parse(it, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                    .toLocalDateTime()
            } ?: LocalDateTime.now()
        } catch (e: Exception) {
            LocalDateTime.now()
        }
    }
}