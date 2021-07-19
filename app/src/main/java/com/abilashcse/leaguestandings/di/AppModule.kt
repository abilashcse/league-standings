package com.abilashcse.leaguestandings.di

import com.abilashcse.leaguestandings.data.api.FootballAPIServices
import com.abilashcse.leaguestandings.data.api.RecentWinsRemoteDataSource
import com.abilashcse.leaguestandings.data.api.StandingsRemoteDataSource
import com.abilashcse.leaguestandings.data.model.recentwins.RecentWinsDataSource
import com.abilashcse.leaguestandings.data.model.recentwins.RecentWinsRepository
import com.abilashcse.leaguestandings.data.model.standings.StandingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
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

    @Provides
    @Singleton
    fun provideRecentWinDataSource(footBallAPIServices: FootballAPIServices) : RecentWinsRemoteDataSource{
        return RecentWinsRemoteDataSource(footBallAPIServices)
    }
    @Provides
    @Singleton
    fun provideRecentWinRepository(recentWinDataSource: RecentWinsRemoteDataSource): RecentWinsRepository {
        return RecentWinsRepository(recentWinDataSource)
    }
}
