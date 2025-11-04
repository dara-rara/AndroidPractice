package ru.urfu.glebova.characters.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.urfu.glebova.characters.data.api.CharactersApi
import ru.urfu.glebova.characters.data.badge.Badge
import ru.urfu.glebova.characters.data.mapper.CharacterDbMapper
import ru.urfu.glebova.characters.data.mapper.CharactersResponseToEntityMapper
import ru.urfu.glebova.characters.data.repository.CharactersRepository
import ru.urfu.glebova.characters.domain.interactor.CharactersInteractor
import ru.urfu.glebova.characters.presentation.mapper.CharacterEntityToModelListMapper
import ru.urfu.glebova.characters.presentation.mapper.CharacterEntityToUiMapper
import ru.urfu.glebova.characters.presentation.viewModel.CharactersDetailsViewModel
import ru.urfu.glebova.characters.presentation.viewModel.CharactersFilterViewModel
import ru.urfu.glebova.characters.presentation.viewModel.CharactersListViewModel

val charactersFeatureModule = module {
    viewModel { CharactersDetailsViewModel(get(), get(), get(), get()) }
    viewModel { CharactersListViewModel(get(), get (), get (), get()) }
    viewModel { CharactersFilterViewModel(get(), get()) }

    single { get<Retrofit>().create(CharactersApi::class.java) }

    factory { CharactersResponseToEntityMapper() }
    factory { CharacterEntityToModelListMapper() }
    factory { CharacterDbMapper() }
    factory { CharacterEntityToUiMapper() }

    single { CharactersRepository(get(), get(), get(), get(), get()) }

    single { CharactersInteractor(get()) }

    single { Badge() }

}