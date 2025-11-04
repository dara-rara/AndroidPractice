package ru.urfu.glebova

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.urfu.glebova.characters.di.charactersFeatureModule
import ru.urfu.glebova.di.databaseModule
import ru.urfu.glebova.di.mainModule
import ru.urfu.glebova.di.networkModule
import ru.urfu.glebova.profile.data.di.profileFeatureModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(mainModule, charactersFeatureModule, networkModule, databaseModule,
                profileFeatureModule
            )
        }
    }
}