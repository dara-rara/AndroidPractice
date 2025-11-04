package ru.urfu.glebova.profile.data.repository

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import ru.urfu.glebova.profile.data.entity.ProfileEntity

class ProfileRepository(
    private val dataStore: DataStore<ProfileEntity>
) : IProfileRepository {

    override suspend fun observeProfile(): Flow<ProfileEntity> = dataStore.data

    override suspend fun getProfile(): ProfileEntity? =
        try {
            dataStore.data.first()
        } catch (e: NoSuchElementException) {
            null
        }

    override suspend fun setProfile(photoUri: String, name: String, url: String) =
        dataStore.updateData { current ->
            current.toBuilder()
                .setPhotoUri(photoUri)
                .setName(name)
                .setUrl(url)
                .build()
        }
}