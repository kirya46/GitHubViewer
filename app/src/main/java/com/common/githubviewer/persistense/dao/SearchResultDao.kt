package com.common.githubviewer.persistense.dao

import android.arch.persistence.room.*
import com.common.githubviewer.entity.SearchResult

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Dao
abstract class SearchResultDao {

    companion object {
        val TAG: String = SearchResultDao::class.java.simpleName
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(searchResult: SearchResult)

    @Update
    abstract fun update(searchResult: SearchResult)

    @Query("SELECT * FROM SearchResult")
    abstract fun findAll(): List<SearchResult>

    @Query("SELECT * FROM SearchResult WHERE  searchQuery = :searchQuery")
    abstract fun findByQuery(searchQuery: String): SearchResult?


    fun insertOrUpdate(searchResult: SearchResult): SearchResult {
        var findByQuery = findByQuery(searchResult.searchQuery)

        if (findByQuery == null) {
            findByQuery = searchResult
        }
        else {
            val listOfExist = ArrayList(findByQuery.items)

            val map = searchResult.items
                    .filter { repo ->
                        //skip new repo if it already exist
                        return@filter !listOfExist.any { it.fullName == repo.fullName }
                    }
                    .map {
                        return@map it
                    }

            listOfExist.addAll(map)

            findByQuery.items = listOfExist
        }

        this.insert(findByQuery)

        return findByQuery
    }
}