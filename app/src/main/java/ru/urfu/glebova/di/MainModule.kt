package ru.urfu.glebova.di

import org.koin.dsl.module
import ru.urfu.glebova.Characters
import ru.urfu.glebova.navigation.Route
import ru.urfu.glebova.navigation.TopLevelBackStack

val mainModule = module {
    single { TopLevelBackStack<Route>(Characters) }
}