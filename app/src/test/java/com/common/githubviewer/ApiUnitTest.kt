package com.common.githubviewer

import com.common.githubviewer.api.GitHubApi
import com.common.githubviewer.entity.Repo
import com.common.githubviewer.entity.SearchResult
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.observers.TestObserver
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiUnitTest {

    private lateinit var api: GitHubApi

    @Before
    fun setUp() {
        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(GitHubApi.BASE_URL)
                .build()

        api = retrofit.create(GitHubApi::class.java)
    }


    @Test
    fun getRepos() {


        val testObserver: TestObserver<Response<List<Repo>>> = TestObserver.create()

        val authResponse = api.getRepos("kirya46")

        authResponse.subscribe(testObserver)

        //check errors
        testObserver.assertNoErrors()
        testObserver.assertComplete()

        //check is result contains some objects
        assertTrue(testObserver.valueCount() > 0)

        //get Response with body
        val response: Response<List<Repo>>? = testObserver.values()[0]
        val repos = response?.body()

        assertEquals("Request successful", HttpURLConnection.HTTP_OK.toLong(), response?.code()?.toLong())
    }


    @Test
    fun searchRepos() {


        val testObserver: TestObserver<Response<SearchResult>> = TestObserver.create()

        val authResponse = api.searchRepos("websocket", page = 1)

        authResponse.subscribe(testObserver)

        //check errors
        testObserver.assertNoErrors()
        testObserver.assertComplete()

        //check is result contains some objects
        assertTrue(testObserver.valueCount() > 0)

        //get Response with body
        val response: Response<SearchResult>? = testObserver.values()[0]
        val repos = response?.body()

        assertEquals("Request successful", HttpURLConnection.HTTP_OK.toLong(), response?.code()?.toLong())
        assertNotNull(repos)
    }
}