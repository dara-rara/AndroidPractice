package ru.urfu.glebova.characters.presentation.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.urfu.glebova.characters.presentation.model.CharacterDetailsViewState
import ru.urfu.glebova.characters.presentation.model.CharacterUiModel
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack

class CharactersDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val character: CharacterUiModel,
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
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}