package com.common.githubviewer.interactor

import com.common.githubviewer.App
import com.common.githubviewer.MainContract
import com.common.githubviewer.entity.SearchResult
import com.common.githubviewer.repository.GitHubRepoRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Kirill Stoianov on 10/07/18.
 */
class MainInteractor : MainContract.Interactor {


    @Inject
    lateinit var repository: GitHubRepoRepository

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }


    override fun loadCachedSearchResult(searchQuery: String): Observable<SearchResult> {
        val cachedResults = repository.getCachedResults(searchQuery)
        return if (cachedResults != null) Observable.just(cachedResults) else Observable.just(SearchResult())
    }

    override fun loadSearchResult(searchQuery: String, page: Int): Observable<SearchResult> {
        return repository.getSearchResult(searchQuery = searchQuery,page = page)
    }

    override fun updateSearchResult(searchResult: SearchResult) {
        return repository.updateSearchResult(searchResult)
    }
}