package ru.urfu.glebova.characters.presentation.model

data class CharactersFilterGender(val gender: String = "") {
    fun hasActiveFilters(): Boolean = gender.isNotEmpty()
}
