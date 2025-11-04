package ru.urfu.glebova.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module
import ru.urfu.glebova.Characters
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.android.ext.koin.androidContext


val mainModule = module {
    single { TopLevelBackStack<Route>(Characters) }
    single<DataStore<Preferences>> {
        getDataSource(androidContext())
    }
}

fun getDataSource(context: Context): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("settings")
    }
}
