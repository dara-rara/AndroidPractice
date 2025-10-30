package ru.urfu.glebova.characters.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.urfu.glebova.characters.data.entity.CharactersDbEntity

@Dao
interface CharactersDao {
    @Query("SELECT * FROM characters")
    suspend fun getAll(): List<CharactersDbEntity>

    @Insert
    suspend fun insert(driverDbEntity: CharactersDbEntity)

    @Delete
    suspend fun delete(character: CharactersDbEntity)
}