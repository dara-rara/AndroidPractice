package ru.urfu.glebova.profile.data.di

import androidx.datastore.core.DataStore
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.urfu.glebova.profile.data.datastore.DataStoreProvider
import ru.urfu.glebova.profile.data.entity.ProfileEntity
import ru.urfu.glebova.profile.data.repository.IProfileRepository
import ru.urfu.glebova.profile.data.repository.ProfileRepository
import ru.urfu.glebova.profile.presentation.viewModel.ProfileViewModel
import ru.urfu.glebova.profile.presentation.viewModel.ProfileEditViewModel

val profileFeatureModule = module {
    single<DataStore<ProfileEntity>>(named("profile")) {
        DataStoreProvider(get()).provide()
    }
    single<IProfileRepository> {
        ProfileRepository(get(named("profile")))
    }

    viewModel { ProfileViewModel(get(), get()) }
    viewModel { ProfileEditViewModel(get(), get()) }
}