package ru.urfu.glebova.di

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module
import ru.urfu.glebova.characters.data.db.CharactersDatabase

val databaseModule = module {
    single { DatabaseBuilder.getInstance(get()) }
    single { get<CharactersDatabase>().charactersDao() }
}

object DatabaseBuilder {
    @Volatile
    private var INSTANCE: CharactersDatabase? = null

    fun getInstance(context: Context): CharactersDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildRoomDB(context).also { INSTANCE = it }
        }
    }

    private fun buildRoomDB(context: Context): CharactersDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                CharactersDatabase::class.java,
                "characters.db"
            ).fallbackToDestructiveMigration(false)
            .build()
    }
}