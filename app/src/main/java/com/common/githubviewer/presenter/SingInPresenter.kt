package com.common.githubviewer.presenter

import android.app.Activity
import com.common.githubviewer.SingInContract
import com.common.githubviewer.entity.User
import com.common.githubviewer.interactor.SingInInteractor
import com.common.githubviewer.router.SingInRouter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Kirill Stoianov on 10/07/18.
 */
class SingInPresenter(private var view: SingInContract.View?) : SingInContract.Presenter, SingInContract.InteractorOutput {

    private var mInteractor: SingInContract.Interactor? = SingInInteractor()

    private var mRouter : SingInContract.Router? = SingInRouter(view as Activity)

    private val mCompositeDisposable = CompositeDisposable()


    override fun onViewCreated() {
        view?.hideLoading()
    }

    override fun singIn(userName: String, password: String) {


        val subscribe = mInteractor?.singIn(userName, password)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe { view?.showLoading() }
                ?.subscribe(
                        { user -> this.onSingInSuccess(user) },
                        { throwable ->
                            this.onSingInError(throwable)
                        }
                )

        subscribe?.let { mCompositeDisposable.add(it) }
    }

    override fun onDestroy() {
        view = null

        mInteractor = null

        mRouter?.unregister()
        mRouter = null

        mCompositeDisposable.clear()
    }

    override fun onSingInSuccess(user: User) {

        view?.hideLoading()

        //show log in result
        view?.publishSingInResult(user)

        //navigate to main screen
        mRouter?.navigateToMainScreen()
    }

    override fun onSingInError(throwable: Throwable) {
        view?.hideLoading()
        view?.showInfoMessage("Auth error. \nCause: ${throwable.message}")
    }

}