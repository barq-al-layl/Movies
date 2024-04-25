package com.phoenix.movies.di

import com.phoenix.movies.BuildConfig
import com.phoenix.movies.data.api.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun providesMoviesApiService(): MoviesApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-RapidAPI-Key", BuildConfig.API_KEY)
                    .addHeader("X-RapidAPI-Host", BuildConfig.API_HOST)
                    .build()
                chain.proceed(request)
            }.build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(MoviesApiService::class.java)
    }
}