package ru.urfu.glebova.characters.presentation.mapper

import ru.urfu.glebova.characters.domain.model.CharacterEntity
import ru.urfu.glebova.characters.presentation.model.CharacterUiModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CharacterEntityToModelListMapper {

    fun mapToUi(characters: List<CharacterEntity>): List<CharacterUiModel> =
        characters.map { character ->
            CharacterUiModel(
                id = character.id,
                name = character.name,
                status = character.status,
                species = character.species,
                type = character.type,
                gender = character.gender,
                origin = CharacterUiModel.Origin(
                    name = character.origin.name,
                    url = character.origin.url
                ),
                location = CharacterUiModel.Location(
                    name = character.location.name,
                    url = character.location.url
                ),
                image = character.image,
                episode = character.episode,
                url = character.url,
                created = formatDateTime(character.created)
            )
        }

    private fun formatDateTime(dateTime: LocalDateTime): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            dateTime.format(formatter)
        } catch (e: Exception) {
            "Неизвестная дата"
        }
    }
}