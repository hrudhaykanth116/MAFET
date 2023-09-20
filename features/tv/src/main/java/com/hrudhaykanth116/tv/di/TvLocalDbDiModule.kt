package com.hrudhaykanth116.tv.di

import android.content.Context
import androidx.room.Room
import com.hrudhaykanth116.tv.data.datasources.local.room.dao.MyTvListDao
import com.hrudhaykanth116.tv.data.datasources.local.room.TvDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TvLocalDbDiModule {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): TvDb {
        return Room.databaseBuilder(
            appContext, TvDb::class.java,
            TvDb.TABLE_NAME
        ).apply {
            fallbackToDestructiveMigration()
        }.build()
    }

    @Singleton
    @Provides
    fun provideMyTvListDao(todoDb: TvDb): MyTvListDao =
        todoDb.myTvListDao()

}