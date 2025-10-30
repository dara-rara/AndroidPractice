package ru.urfu.glebova.characters.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.urfu.glebova.CharactersDetails
import ru.urfu.glebova.CharactersFilter
import ru.urfu.glebova.characters.data.badge.Badge
import ru.urfu.glebova.characters.domain.interactor.CharactersInteractor
import ru.urfu.glebova.characters.presentation.mapper.CharacterEntityToModelListMapper
import ru.urfu.glebova.characters.presentation.model.CharacterUiModel
import ru.urfu.glebova.characters.presentation.model.CharactersListFilter
import ru.urfu.glebova.characters.presentation.model.CharactersListViewState
import ru.urfu.glebova.core.launchLoadingAndError
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack

class CharactersListViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: CharactersInteractor,
    private val mapper: CharacterEntityToModelListMapper,
    private val badge: Badge,
) : ViewModel() {
    private val mutableState = MutableStateFlow(CharactersListViewState())
    val viewState = mutableState.asStateFlow()

    init {
        loadCharacters()
        viewModelScope.launch {
            interactor.updateGenderFilter().collect { currentFilter ->
                val shouldShowBadge = currentFilter.isNotEmpty()
                badge.setFiltersActive(shouldShowBadge)
            }
        }
    }

    fun onRetryClick() = loadCharacters()

    fun onSettingsClick() = topLevelBackStack.add(CharactersFilter)

    fun onCharactersClick(character: CharacterUiModel) {
        topLevelBackStack.add(CharactersDetails(character))
    }

    fun onFilterChange(filter: CharactersListFilter) {
        mutableState.update { it.copy(currentFilter = filter) }
        loadCharacters()
    }

    fun shouldShowBadge(): Boolean {
        return badge.hasActiveFilters()
    }

    private fun loadCharacters() {
        viewModelScope.launchLoadingAndError(
            handleError = { e ->
                updateState(CharactersListViewState.State.Error(e.localizedMessage.orEmpty()))
            }
        ) {
            updateState(CharactersListViewState.State.Loading)

            interactor.updateGenderFilter()
                .onEach { updateState(CharactersListViewState.State.Loading) }
                .map {
                    if (viewState.value.currentFilter == CharactersListFilter.ALL){
                        interactor.getCharacters(it)
                    } else {
                        interactor.getFavorites()
                    }
                }
                .collect { updateState(CharactersListViewState.State.Success(mapper.mapToUi(it))) }

        }
    }

    private fun updateState(state: CharactersListViewState.State) =
        mutableState.update { it.copy(state = state) }
}