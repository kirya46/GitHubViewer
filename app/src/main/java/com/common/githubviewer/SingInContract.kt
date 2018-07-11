package com.common.githubviewer

import com.common.githubviewer.api.GitHubApi
import com.common.githubviewer.entity.User
import com.common.githubviewer.view.activity.MainActivity
import io.reactivex.Observable


/**
 * Created by Kirill Stoianov on 09/07/18.
 */
interface SingInContract {

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
         * */
        fun publishSingInResult(user: User)

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
         * Do request to [GitHubApi.oAuth] for authorizing user.
         *
         * @param userName - user name on GitHub.
         * @param password - user password on GitHub
         */
        fun singIn(userName: String, password: String)

        /**
         * Lifecycle method.
         */
        fun onDestroy()
    }

    interface Interactor {
        /**
         * Do request to [GitHubApi.oAuth] for authorizing user.
         *
         * @param userName - user name on GitHub.
         * @param password - user password on GitHub
         */
        fun singIn(userName: String, password: String): Observable<User>
    }

    interface InteractorOutput {
        /**
         * Call when search response is success.
         *
         * @param user - profile of authorized user on GitHub.
         */
        fun onSingInSuccess(user: User)

        /**
         * Call when authorization failed.
         *
         * @param throwable - authorization error.
         */
        fun onSingInError(throwable: Throwable)
    }

    interface Router{

        /**
         * Navigate user to [MainActivity] after success login.
         */
        fun navigateToMainScreen()

        /**
         * Lifecycle callback.
         */
        fun unregister()
    }
}