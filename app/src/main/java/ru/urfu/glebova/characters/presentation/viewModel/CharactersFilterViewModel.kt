package ru.urfu.glebova.characters.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.urfu.glebova.characters.domain.interactor.CharactersInteractor
import ru.urfu.glebova.characters.presentation.model.CharactersFilterGender
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack

class CharactersFilterViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: CharactersInteractor,
) : ViewModel() {

    private val mutableState = MutableStateFlow(CharactersFilterGender(""))
    val viewState = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            interactor.updateGenderFilter().collect { savedFilter ->
                mutableState.update { it.copy(gender = savedFilter) }
            }
        }
    }

    fun onFilterCheckedChange(gender: String) {
        mutableState.update { it.copy(gender = gender) }
    }

    fun onSaveFilter(selectedGender: String) {
        viewModelScope.launch {
            interactor.setGenderFilter(selectedGender)
            onBack()
        }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}