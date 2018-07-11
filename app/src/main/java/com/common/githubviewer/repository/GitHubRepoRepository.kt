package com.common.githubviewer.repository

import com.common.githubviewer.api.GitHubApi
import com.common.githubviewer.entity.Repo
import com.common.githubviewer.entity.SearchResult
import com.common.githubviewer.persistense.dao.RepoDao
import com.common.githubviewer.persistense.dao.SearchResultDao
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
class GitHubRepoRepository(private val searchResultDao: SearchResultDao, private val dao: RepoDao, private val api: GitHubApi) {

    companion object {
        val TAG: String = GitHubRepoRepository::class.java.simpleName
    }


    /**
     * Get list of cached [Repo]'s  from DB.
     *
     * @param searchQuery - search keyword.
     */
    fun getCachedResults(searchQuery: String): SearchResult? {
        return searchResultDao.findByQuery(searchQuery)
    }


    /**
     * Search repos in GitHub Api.
     *
     * @param searchQuery - search keyword.
     * @return list with found [Repo]'s.
     */
    fun getSearchResult(searchQuery: String, page: Int): Observable<SearchResult> {


        val mapperFunction = Function<Response<SearchResult>, SearchResult> { response ->
            val searchResult = response.body()

            //set destination query
            searchResult?.searchQuery = searchQuery

            //save result in DB
            searchResult?.let {
                val updated = searchResultDao.insertOrUpdate(it)
                return@Function updated
            }

            return@Function if (searchResult != null) return@Function searchResult else SearchResult()
        }

        val firstCall = api.searchRepos(searchQuery, page = page)
                .subscribeOn(Schedulers.io())
                .map(mapperFunction)


        val secondCall = api.searchRepos(searchQuery, page = (page + 1))
                .subscribeOn(Schedulers.io())
                .map(mapperFunction)

        return Observable.merge(firstCall, secondCall)
    }


    /**
     * Update [searchResult] in DB.
     */
    fun updateSearchResult(searchResult: SearchResult) {
        searchResultDao.update(searchResult)
    }
}