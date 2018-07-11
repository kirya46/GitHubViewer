package com.common.githubviewer.api

import com.common.githubviewer.entity.Repo
import com.common.githubviewer.entity.SearchResult
import com.common.githubviewer.entity.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Kirill Stoianov on 09/07/18.
 *
 * Api reference: https://developer.github.com/v3/
 */
interface GitHubApi {


    //TODO change date

    companion object {

        /**
         * Base url of GitHub api.
         */
        const val BASE_URL: String = "https://api.github.com/"


        /**
         * Default object size per one page.
         */
        const val DEFAULT_PAGE_SIZE : Int = 15
    }


    /**
     * User auth.
     *
     * Api method reference: https://developer.github.com/v3/auth/#via-username-and-password
     *
     * @param auth - user credentials.
     * @return GitHub user profile.
     */
    @GET("user")
    fun oAuth(@Header("Authorization") auth: String): Observable<Response<User>>


    /**
     * Get user repositories.
     *
     * Api method reference: https://developer.github.com/v3/repos/#list-user-repositories
     *
     * @param username - user name on GitHub
     * @return list with user repos.
     */
    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String): Observable<Response<List<Repo>>>


    /**
     * Search repositories.
     *
     * Api method reference: https://api.github.com/search/repositories
     *
     * @param searchQuery - search keyword
     * @param sort - the sort field. One of stars, forks, or updated. Default: results are sorted by best match.
     * @param order - the sort order if sort parameter is provided. One of asc or desc. Default: desc
     *
     * @return list with repos.
     */
    @GET("search/repositories")
    fun searchRepos(
            @Query("q") searchQuery: String,
            @Query("sort") sort: String = "stars",
            @Query("order") order: String = "desc",
            @Query("per_page") per_page: Int = 15,
            @Query("page") page:Int
    ): Observable<Response<SearchResult>>
}

