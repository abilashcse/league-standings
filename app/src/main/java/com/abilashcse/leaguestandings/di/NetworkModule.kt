package com.abilashcse.leaguestandings.di

import com.abilashcse.leaguestandings.data.api.FootballAPIServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val API_BASE_URL = "https://api.football-data.org/v2/"


    @Provides
    @Singleton
    fun provideFootballAPIServices(retrofit: Retrofit): FootballAPIServices{

        val footBallAPIServices = retrofit.create(
            FootballAPIServices::class.java
        )
        return footBallAPIServices as FootballAPIServices
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpLoggingInterceptor: HttpLoggingInterceptor): Retrofit {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(httpLoggingInterceptor)

        return builder.client(httpClient.build()).build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}
