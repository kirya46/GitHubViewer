package com.common.githubviewer.persistense

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.common.githubviewer.entity.Repo
import com.common.githubviewer.entity.SearchResult
import com.common.githubviewer.entity.User
import com.common.githubviewer.persistense.converter.RepoConverter
import com.common.githubviewer.persistense.dao.RepoDao
import com.common.githubviewer.persistense.dao.SearchResultDao


/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Database(
        entities = [User::class, Repo::class, SearchResult::class],
        version = 1,
        exportSchema = false)
@TypeConverters(RepoConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    abstract fun searchResultDao(): SearchResultDao
}