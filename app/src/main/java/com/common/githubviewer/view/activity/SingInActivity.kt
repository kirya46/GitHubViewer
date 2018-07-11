package com.common.githubviewer.view.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import com.common.githubviewer.App
import com.common.githubviewer.R
import com.common.githubviewer.SingInContract
import com.common.githubviewer.entity.User
import com.common.githubviewer.presenter.SingInPresenter
import com.common.githubviewer.util.AppPrefs
import kotlinx.android.synthetic.main.activity_sing_in.*
import javax.inject.Inject

/**
 * Created by Kirill Stoianov on 10/07/18.
 */
class SingInActivity : BaseActivity(),SingInContract.View{

    @Inject
    lateinit var appPrefs: AppPrefs

    private var mPresenter : SingInPresenter? = null

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)


        mPresenter = SingInPresenter(this)

        btnSingIn.setOnClickListener {
            mPresenter?.singIn(etUserName.text.toString(),etUserPassword.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        if (mPresenter==null)mPresenter = SingInPresenter(this)
        mPresenter?.onViewCreated()
    }

    override fun onDestroy() {
        mPresenter?.onDestroy()
        mPresenter = null
        super.onDestroy()
    }


    override fun getToolbarInstance(): Toolbar? {
        return null
    }

    override fun showLoading() {
        etUserName.isEnabled = false
        etUserPassword.isEnabled = false
        btnSingIn.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        etUserName.isEnabled = true
        etUserPassword.isEnabled = true
        btnSingIn.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun publishSingInResult(user: User) {

        //save user login and auth string
        val toAuthString = User.Credentials(etUserName.text.toString(), etUserPassword.text.toString()).toAuthString()
        appPrefs.saveUserAuthString(toAuthString)
        appPrefs.saveUserLogin(user.login)

        //show message
        showInfoMessage("Sing in as ${user.login}")
    }

    override fun showInfoMessage(msg: String) {
        Toast.makeText(this@SingInActivity,msg,Toast.LENGTH_LONG).show()
    }

}