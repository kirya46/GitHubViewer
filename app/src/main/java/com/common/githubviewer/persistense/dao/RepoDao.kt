package com.common.githubviewer.persistense.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.common.githubviewer.entity.Repo

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<Repo>)

    @Query("SELECT * FROM Repo")
    fun findAll(): List<Repo>
}