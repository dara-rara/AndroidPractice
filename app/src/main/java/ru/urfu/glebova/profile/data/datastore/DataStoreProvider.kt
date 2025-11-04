package ru.urfu.glebova.profile.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import ru.urfu.glebova.profile.data.entity.ProfileEntity
import ru.urfu.glebova.profile.data.entity.ProfileSerializer

class DataStoreProvider(val context: Context) {
    private val Context.profileDataStore: DataStore<ProfileEntity> by dataStore(
        fileName = "profile.pb",
        serializer = ProfileSerializer
    )

    fun provide(): DataStore<ProfileEntity> = context.profileDataStore
}
