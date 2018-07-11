package com.common.githubviewer.di

import android.arch.persistence.room.Room
import android.content.Context
import com.common.githubviewer.R
import com.common.githubviewer.persistense.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Module
class DataBaseModule {

    companion object {
        val TAG: String = DataBaseModule::class.java.simpleName
    }

    //DATABASE

    @Provides
    fun providesAppDatabase(context: Context): AppDatabase {

        return Room
                .databaseBuilder(context, AppDatabase::class.java, context.getString(R.string.app_name) + "-database.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration() //useful for clear database and recreate tables after schema number changed without migration
                .fallbackToDestructiveMigrationFrom()
                .build()
    }


    //DAO

    @Provides
    @Singleton
    fun provideRepoDao(database: AppDatabase) = database.repoDao()

    @Provides
    @Singleton
    fun proviceSearchResultDao(database: AppDatabase) = database.searchResultDao()

}