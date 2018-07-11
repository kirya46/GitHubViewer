package com.common.githubviewer.presenter

import android.app.Activity
import com.common.githubviewer.App
import com.common.githubviewer.MainContract
import com.common.githubviewer.entity.Repo
import com.common.githubviewer.entity.SearchResult
import com.common.githubviewer.interactor.MainInteractor
import com.common.githubviewer.router.MainRouter
import com.common.githubviewer.util.AppPrefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * Created by Kirill Stoianov on 09/07/18.
 */
class MainPresenter(private var view: MainContract.View?) : MainContract.Presenter, MainContract.InteractorOutput {


    @Inject
    lateinit var appPrefs: AppPrefs

    private var mInteractor: MainContract.Interactor? = MainInteractor()
    private var mRouter : MainContract.Router? = MainRouter(view as Activity)
    private var mCompositeDisposable = CompositeDisposable()


    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    override fun onViewCreated() {
        //hide on start
        view?.hideLoading()

        view?.showLoading()

        val lastSearchQuery = appPrefs.getLastSearchQuery()

        val subscribe = mInteractor?.loadCachedSearchResult(lastSearchQuery)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { data ->
                            this.onQuerySuccess(data)
                        },
                        { throwable -> this.onQueryError(throwable) }
                )

        subscribe?.let { mCompositeDisposable.add(it) }
    }



    override fun startSearchRepos(searchQuery: String, page: Int) {

        view?.showLoading()

        val subscribe = mInteractor?.loadSearchResult(searchQuery = searchQuery,page = page)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        { data -> this.onQuerySuccess(data) },
                        { throwable -> this.onQueryError(throwable) }
                )

        subscribe?.let { mCompositeDisposable.add(it) }
    }

    override fun stopSearchRepos() {
        mCompositeDisposable.clear()
    }


    override fun openListItem(repo: Repo?) {

        //open browser with repo url
        repo?.let {
            mRouter?.navigateToBrowserScreen(repo.htmlUrl)
        }
    }

    override fun updateSearchResult(searchResult: SearchResult){
        mInteractor?.updateSearchResult(searchResult)

    }

    override fun singIn() {

        //clear app prefs
        appPrefs.clearUserCredentials()

        mRouter?.navigateToSingInScreen()
    }

    override fun logOut() {

        //clear app prefs
        appPrefs.clearUserCredentials()

        //navigate to SingInActivity
        mRouter?.navigateToSingInScreen()
    }

    override fun openBrowser(url: String) {
        mRouter?.navigateToBrowserScreen(url)
    }

    override fun onQuerySuccess(data: SearchResult) {
        view?.hideLoading()
        view?.publishDataList(data)
    }

    override fun onQueryError(throwable: Throwable) {
        view?.hideLoading()
        view?.showInfoMessage("Error when loading data.\nCause: ${throwable.message}")
    }

    override fun onDestroy() {
        view = null

        mInteractor = null

        mRouter?.unregister()
        mRouter = null

        mCompositeDisposable.clear()
    }
}
