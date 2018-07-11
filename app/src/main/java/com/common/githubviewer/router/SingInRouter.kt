package com.common.githubviewer.router

import android.app.Activity
import android.content.Intent
import com.common.githubviewer.SingInContract
import com.common.githubviewer.view.activity.MainActivity

/**
 * Created by Kirill Stoianov on 11/07/18.
 */
class SingInRouter(var activity: Activity?):SingInContract.Router{

    override fun navigateToMainScreen() {
        val intent = Intent(activity, MainActivity::class.java)
        activity?.startActivity(intent)
        activity?.finish()
    }

    override fun unregister() {
        activity = null
    }
}