package ru.urfu.glebova.characters.presentation.model

data class CharactersListViewState(
    val state: State = State.Loading,
) {
    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        data class Success(val data: List<CharacterUiModel>) : State
    }
}