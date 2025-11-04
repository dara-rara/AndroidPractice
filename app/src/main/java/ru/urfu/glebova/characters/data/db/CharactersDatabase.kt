package ru.urfu.glebova.characters.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.urfu.glebova.characters.data.dao.CharactersDao
import ru.urfu.glebova.characters.data.entity.CharactersDbEntity
import ru.urfu.glebova.characters.data.mapper.Converters

@Database(
    entities = [CharactersDbEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}