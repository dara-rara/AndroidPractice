package ru.urfu.glebova.characters.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.urfu.glebova.CharactersDetails
import ru.urfu.glebova.characters.domain.interactor.CharactersInteractor
import ru.urfu.glebova.characters.domain.model.CharacterEntity
import ru.urfu.glebova.characters.presentation.model.CharacterUiModel
import ru.urfu.glebova.characters.presentation.model.CharactersListViewState
import ru.urfu.glebova.core.launchLoadingAndError
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CharactersListViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: CharactersInteractor,
) : ViewModel() {
    private val mutableState = MutableStateFlow(CharactersListViewState())
    val viewState = mutableState.asStateFlow()

    init {
        loadCharacters()
    }

    fun onRetryClick() = loadCharacters()

    fun onCharactersClick(character: CharacterUiModel) {
        topLevelBackStack.add(CharactersDetails(character))
    }

    private fun loadCharacters() {
        viewModelScope.launchLoadingAndError(
            handleError = { e ->
                updateState(CharactersListViewState.State.Error(e.localizedMessage.orEmpty()))
            }
        ) {
            updateState(CharactersListViewState.State.Loading)

            val characters = interactor.getCharacters()
            updateState(CharactersListViewState.State.Success(mapToUi(characters)))
        }
    }

    private fun updateState(state: CharactersListViewState.State) =
        mutableState.update { it.copy(state = state) }

    private fun mapToUi(characters: List<CharacterEntity>): List<CharacterUiModel> =
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