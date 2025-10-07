package ru.urfu.glebova.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.urfu.glebova.Characters
import ru.urfu.glebova.characters.presentation.viewModel.CharactersDetailsViewModel
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack

val mainModule = module {
    single { TopLevelBackStack<Route>(Characters) }

    viewModel { CharactersDetailsViewModel(get(), get()) }
}