package com.common.githubviewer.router

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.common.githubviewer.MainContract
import com.common.githubviewer.view.activity.SingInActivity

/**
 * Created by Kirill Stoianov on 11/07/18.
 */
class MainRouter(var activity: Activity?):MainContract.Router{
    override fun navigateToSingInScreen() {
        val intent = Intent(activity, SingInActivity::class.java)
        activity?.startActivity(intent)
        activity?.finish()
    }

    override fun navigateToBrowserScreen(url:String) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.setPackage("com.android.chrome")
        try {
            activity?.startActivity(i)
        } catch (e: ActivityNotFoundException) {
            // Chrome is probably not installed
            // Try with the default browser
            i.setPackage(null)
            activity?.startActivity(i)
        }
    }


    override fun unregister() {
        activity = null
    }
}