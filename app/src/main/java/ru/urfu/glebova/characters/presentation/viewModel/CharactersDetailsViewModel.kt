package ru.urfu.glebova.characters.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.urfu.glebova.characters.domain.interactor.CharactersInteractor
import ru.urfu.glebova.characters.presentation.mapper.CharacterEntityToUiMapper
import ru.urfu.glebova.characters.presentation.model.CharacterDetailsViewState
import ru.urfu.glebova.characters.presentation.model.CharacterUiModel
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack

class CharactersDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    character: CharacterUiModel,
    private val interactor: CharactersInteractor,
    private val mapper: CharacterEntityToUiMapper,
) : ViewModel() {
    private val mutableState = MutableStateFlow(CharacterDetailsViewState(character))
    val state = mutableState.asStateFlow()

    fun onLikeChanged(likes: Int) {
        val currentState = mutableState.value
        val newIsLiked = !currentState.isLiked

        mutableState.update {
            it.copy(
                likes = likes,
                isLiked = newIsLiked
            )
        }

        if (newIsLiked) {
            viewModelScope.launch {
                interactor.saveFavorite(mapper.toCharacterEntity(currentState.character))
            }
        } else {
            viewModelScope.launch {
                interactor.removeFavorite(mapper.toCharacterEntity(currentState.character))
            }
        }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}