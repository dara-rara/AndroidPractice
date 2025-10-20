package ru.urfu.glebova.characters.presentation.model

data class CharacterDetailsViewState(
    val character: CharacterUiModel,
    val likes: Int = 0,
    val isLiked: Boolean = false
)