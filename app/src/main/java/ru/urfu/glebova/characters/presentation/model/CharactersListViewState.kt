package ru.urfu.glebova.characters.presentation.model

data class CharactersListViewState(
    val state: State = State.Loading,
    val filters: List<CharactersListFilter> = CharactersListFilter.entries,
    val currentFilter: CharactersListFilter = CharactersListFilter.ALL,
) {
    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        data class Success(val data: List<CharacterUiModel>) : State
    }
}

enum class CharactersListFilter(val text: String) {
    ALL("Все"),
    FAVORITES("Избранные")
}