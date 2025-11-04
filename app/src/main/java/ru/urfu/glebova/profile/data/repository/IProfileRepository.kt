package ru.urfu.glebova.profile.data.repository

import kotlinx.coroutines.flow.Flow
import ru.urfu.glebova.profile.data.entity.ProfileEntity

interface IProfileRepository {
    suspend fun getProfile(): ProfileEntity?
    suspend fun setProfile(photoUri: String, name: String, url: String): ProfileEntity
    suspend fun observeProfile(): Flow<ProfileEntity>

}