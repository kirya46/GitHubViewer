package com.common.githubviewer.view.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.common.githubviewer.App
import com.common.githubviewer.MainContract
import com.common.githubviewer.R
import com.common.githubviewer.api.GitHubApi
import com.common.githubviewer.entity.Repo
import com.common.githubviewer.entity.SearchResult
import com.common.githubviewer.presenter.MainPresenter
import com.common.githubviewer.util.AppPrefs
import com.common.githubviewer.view.adapter.RepoAdapter
import com.common.githubviewer.widget.EndlessScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.Collections.sort
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * Created by Kirill Stoianov on 09/07/18.
 */
class MainActivity : BaseActivity(), MainContract.View, RepoAdapter.Listener {

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    @Inject
    lateinit var appPrefs: AppPrefs

    private var mPresenter: MainContract.Presenter? = null

    private var mRepoAdapter: RepoAdapter? = null

    private var mSearchResult: SearchResult? = null


    private var mToast: Toast? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.INSTANCE.getAppComponent().inject(this)

        mPresenter = MainPresenter(this)

        //set last search query in EditText
        etSearch.setText(appPrefs.getLastSearchQuery())

        //init RecyclerView adapter
        mRepoAdapter = RepoAdapter(this, ArrayList())

        //setup RecyclerView
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mRepoAdapter
        recyclerView.setHasFixedSize(true)

        //set scroll listener for load next search results
        recyclerView.addOnScrollListener(object : EndlessScrollListener(linearLayoutManager, GitHubApi.DEFAULT_PAGE_SIZE) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {

                if (appPrefs.getUserLogin() == null) {

                    showInfoMessage("For load more need login")

                    return
                }

                val searchQuery = appPrefs.getLastSearchQuery()

                val nextPage = page + 1

                mPresenter?.startSearchRepos(searchQuery, nextPage)

            }
        })

        //recyclerView item touch callback for drag'n'drop
        val ithCallback: ItemTouchHelper.Callback = object : ItemTouchHelper.Callback() {

            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.START or ItemTouchHelper.END)
            }

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {

                Collections.swap(/*RecyclerView.Adapter's data collection*/mRepoAdapter?.getData(), viewHolder?.adapterPosition!!, target?.adapterPosition!!)

                // and notify the adapter that its dataSet has changed
                mRepoAdapter?.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)

                //update local value
                mRepoAdapter?.let {
                    //update order
                    it.getData().forEach { repo ->
                        repo.order = it.getData().indexOf(repo)
                    }

                    mSearchResult?.items = it.getData()
                }


                //update in DB
                mSearchResult?.let { mPresenter?.updateSearchResult(it) }

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            }
        }
        val itemTouchHelper = ItemTouchHelper(ithCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        //'Search' button listener
        btnSearch.setOnClickListener {

            if (appPrefs.getUserLogin() == null) {


                //navigate to SingInActivity
                mPresenter?.singIn()

                return@setOnClickListener
            }

            //get user input data
            val searchQuery = etSearch.text.toString()

            //save to SharedPreferences last user search query
            appPrefs.saveLastSearchQuery(searchQuery)

            //search repos
            mPresenter?.startSearchRepos(searchQuery)
        }


        //'Stop search' button listener
        btnStop.setOnClickListener {
            mPresenter?.stopSearchRepos()
            hideLoading()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val userLogin = appPrefs.getUserLogin()
        if (userLogin != null) {
            menu?.findItem(R.id.actionExitApp)?.title = userLogin
            menu?.findItem(R.id.actionExitApp)?.isVisible = true

            menu?.findItem(R.id.actionLogin)?.isVisible = false
        } else {
            menu?.findItem(R.id.actionExitApp)?.isVisible = false
            menu?.findItem(R.id.actionLogin)?.isVisible = true
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        item?.let {
            when (it.itemId) {
                R.id.actionExitApp -> {

                    //navigate to SingInActivity
                    mPresenter?.logOut()

                    return true
                }

                R.id.actionLogin -> {

                    //navigate to SingInActivity
                    mPresenter?.singIn()

                    return true
                }

                else -> {
                    return false
                }
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        if (mPresenter == null) mPresenter = MainPresenter(this)
        mPresenter?.onViewCreated()
    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        mPresenter = null
        super.onDestroy()
    }

    override fun showLoading() {
        recyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
        btnSearch.visibility = View.INVISIBLE
        btnStop.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        recyclerView.isEnabled = true
        progressBar.visibility = View.INVISIBLE
        btnSearch.visibility = View.VISIBLE
        btnStop.visibility = View.INVISIBLE
    }

    override fun publishDataList(data: SearchResult) {


        //update repo Recycler
        if (data.items.isNotEmpty()) {

            //save current result
            mSearchResult = data

            sort(data.items)
            mRepoAdapter?.updateData(data.items)
        }

    }

    override fun showInfoMessage(msg: String) {
        mToast?.cancel()
        mToast= Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT)
        mToast?.show()

    }

    override fun getToolbarInstance(): Toolbar? {
        return toolbar
    }

    override fun onOpenRepo(repo: Repo?) {

        mSearchResult?.items?.find { it.fullName == repo?.fullName }?.viewed = true
        mSearchResult?.let {
            mPresenter?.updateSearchResult(it)
            publishDataList(it)
        }

        mPresenter?.openListItem(repo)
    }

    override fun onDeleteRepoFromResults(repo: Repo?) {

        mSearchResult?.let {
            (it.items as ArrayList).remove(repo)
            mPresenter?.updateSearchResult(it)
            publishDataList(it)
        }
    }
}