package ru.urfu.glebova.characters.presentation

import ru.urfu.glebova.characters.presentation.model.CharacterUiModel

object MockData {
    fun getCharacters(): List<CharacterUiModel> = listOf(
        CharacterUiModel(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = CharacterUiModel.Origin(
                "Earth (C-137)",
                "https://rickandmortyapi.com/api/location/1"
            ),
            location = CharacterUiModel.Location(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/3",
                // ... остальные эпизоды
                "https://rickandmortyapi.com/api/episode/51"
            ),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        ),
        CharacterUiModel(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = CharacterUiModel.Origin("unknown", ""),
            location = CharacterUiModel.Location(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/3",
                // ... остальные эпизоды
                "https://rickandmortyapi.com/api/episode/51"
            ),
            url = "https://rickandmortyapi.com/api/character/2",
            created = "2017-11-04T18:50:21.651Z"
        ),
        CharacterUiModel(
            id = 3,
            name = "Summer Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Female",
            origin = CharacterUiModel.Origin(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            location = CharacterUiModel.Location(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/6",
                "https://rickandmortyapi.com/api/episode/7",
                "https://rickandmortyapi.com/api/episode/8",
                // ... остальные эпизоды
                "https://rickandmortyapi.com/api/episode/51"
            ),
            url = "https://rickandmortyapi.com/api/character/3",
            created = "2017-11-04T19:09:56.428Z"
        ),
        CharacterUiModel(
            id = 4,
            name = "Beth Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Female",
            origin = CharacterUiModel.Origin(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            location = CharacterUiModel.Location(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/4.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/6",
                "https://rickandmortyapi.com/api/episode/7",
                "https://rickandmortyapi.com/api/episode/8",
                // ... остальные эпизоды
                "https://rickandmortyapi.com/api/episode/51"
            ),
            url = "https://rickandmortyapi.com/api/character/4",
            created = "2017-11-04T19:22:43.665Z"
        ),
        CharacterUiModel(
            id = 5,
            name = "Jerry Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = CharacterUiModel.Origin(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            location = CharacterUiModel.Location(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/5.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/6",
                "https://rickandmortyapi.com/api/episode/7",
                "https://rickandmortyapi.com/api/episode/8",
                // ... остальные эпизоды
                "https://rickandmortyapi.com/api/episode/51"
            ),
            url = "https://rickandmortyapi.com/api/character/5",
            created = "2017-11-04T19:26:56.301Z"
        ),
        CharacterUiModel(
            id = 6,
            name = "Abadango Cluster Princess",
            status = "Alive",
            species = "Alien",
            type = "",
            gender = "Female",
            origin = CharacterUiModel.Origin(
                "Abadango",
                "https://rickandmortyapi.com/api/location/2"
            ),
            location = CharacterUiModel.Location(
                "Abadango",
                "https://rickandmortyapi.com/api/location/2"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/6.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/27"),
            url = "https://rickandmortyapi.com/api/character/6",
            created = "2017-11-04T19:50:28.250Z"
        ),
        CharacterUiModel(
            id = 7,
            name = "Abradolf Lincler",
            status = "unknown",
            species = "Human",
            type = "Genetic experiment",
            gender = "Male",
            origin = CharacterUiModel.Origin(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            location = CharacterUiModel.Location(
                "Testicle Monster Dimension",
                "https://rickandmortyapi.com/api/location/21"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/7.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/10",
                "https://rickandmortyapi.com/api/episode/11"
            ),
            url = "https://rickandmortyapi.com/api/character/7",
            created = "2017-11-04T19:59:20.523Z"
        ),
        CharacterUiModel(
            id = 8,
            name = "Adjudicator Rick",
            status = "Dead",
            species = "Human",
            type = "",
            gender = "Male",
            origin = CharacterUiModel.Origin("unknown", ""),
            location = CharacterUiModel.Location(
                "Citadel of Ricks",
                "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/8.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/28"),
            url = "https://rickandmortyapi.com/api/character/8",
            created = "2017-11-04T20:03:34.737Z"
        )
    )
}