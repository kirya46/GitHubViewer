package com.common.githubviewer.interactor

import com.common.githubviewer.App
import com.common.githubviewer.SingInContract
import com.common.githubviewer.api.GitHubApi
import com.common.githubviewer.entity.User
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Kirill Stoianov on 10/07/18.
 */
class SingInInteractor : SingInContract.Interactor {

    companion object {
        val TAG : String = SingInInteractor::class.java.simpleName
    }

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    @Inject
    lateinit var api: GitHubApi

    override fun singIn(userName: String, password: String): Observable<User> {

        //prepare auth string
        val authString = User.Credentials(userName, password).toAuthString()

        return api.oAuth(authString)
                .subscribeOn(Schedulers.io())
                .map {
                    val user = it.body() ?: throw Exception("User not found.")
                    return@map user
                }
    }


}