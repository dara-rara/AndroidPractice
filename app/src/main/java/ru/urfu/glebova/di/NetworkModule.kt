package ru.urfu.glebova.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.urfu.glebova.characters.data.api.CharactersApi

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(get()))
            .build()
    }

    single {
        val json = Json {
            explicitNulls = false
            ignoreUnknownKeys = true
        }
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .client(get())
            .build()
    }

}