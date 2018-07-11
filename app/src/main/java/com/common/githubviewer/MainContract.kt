package com.common.githubviewer

import com.common.githubviewer.api.GitHubApi
import com.common.githubviewer.entity.Repo
import com.common.githubviewer.entity.SearchResult
import com.common.githubviewer.view.activity.SingInActivity
import io.reactivex.Observable

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
interface MainContract {

    interface View {

        /**
         * Show loading view.
         */
        fun showLoading()

        /**
         * Hide loading view.
         */
        fun hideLoading()

        /**
         * Bind data with view.
         */
        fun publishDataList(data: SearchResult)

        /**
         * Show toast message
         */
        fun showInfoMessage(msg: String)
    }

    interface Presenter {

        /**
         * Call when activity view created.
         */
        fun onViewCreated()

        /**
         * Open [repo] link in browser.
         *
         * @param repo - destination repo.
         */
        fun openListItem(repo: Repo?)

        /**
         * Search repos by query in [GitHubApi]
         */
        fun startSearchRepos(searchQuery: String, page: Int = 1)

        /**
         * Stop search.
         */
        fun stopSearchRepos()

        /**
         * Update [searchResult] in DB after removing some repositories
         * or reordering repositories for this [searchResult].
         *
         * @param searchResult - destination [SearchResult]
         */
        fun updateSearchResult(searchResult: SearchResult)

        /**
         * Process user login.
         */
        fun singIn()

        /**
         * Process user logout
         */
        fun logOut()

        /**
         * Process opening url in browser.
         */
        fun openBrowser(url: String)

        /**
         * Lifecycle method.
         */
        fun onDestroy()
    }

    interface Interactor {

        /**
         * Load already exist [SearchResult] for destination [searchQuery].
         *
         * @param searchQuery - search keyword.
         */
        fun loadCachedSearchResult(searchQuery: String): Observable<SearchResult>

        /**
         * Load repos for destination [searchQuery]
         *
         * @param searchQuery - search keyword.
         */
        fun loadSearchResult(searchQuery: String, page: Int = 1): Observable<SearchResult>

        /**
         * Update [searchResult] in DB after removing some repositories
         * or reordering repositories for this [searchResult].
         *
         * @param searchResult - destination [SearchResult]
         */
        fun updateSearchResult(searchResult: SearchResult)
    }

    interface InteractorOutput {
        /**
         * Call when search response is success.
         *
         * @param data - search request results.
         */
        fun onQuerySuccess(data: SearchResult)

        /**
         * Call when search response is failed.
         *
         * @param throwable - search request error.
         */
        fun onQueryError(throwable: Throwable)
    }

    interface Router {

        /**
         * Open Chrome browser with destination [url]
         * of GitHub repository.
         *
         * @param url - link to [Repo.htmlUrl]
         */
        fun navigateToBrowserScreen(url: String)

        /**
         * Navigate user to login screen.
         *
         * @see SingInActivity
         */
        fun navigateToSingInScreen()

        /**
         * Lifecycle callback.
         */
        fun unregister()
    }
}