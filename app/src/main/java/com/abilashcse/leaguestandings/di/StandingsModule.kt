package com.abilashcse.leaguestandings.di

import com.abilashcse.leaguestandings.data.api.FootballAPIServices
import com.abilashcse.leaguestandings.data.api.StandingsRemoteDataSource
import com.abilashcse.leaguestandings.data.model.StandingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object StandingsModule {
    @Provides
    @Singleton
    fun provideStandingDataSource(footBallAPIServices: FootballAPIServices) : StandingsRemoteDataSource{
        return StandingsRemoteDataSource(footBallAPIServices)
    }
    @Provides
    @Singleton
    fun provideStandingsRepository(standingDataSource: StandingsRemoteDataSource): StandingsRepository {
        return StandingsRepository(standingDataSource)
    }
}
