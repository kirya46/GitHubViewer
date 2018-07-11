package com.common.githubviewer.view.activity

/**
 * Created by Kirill Stoianov on 09/07/18.
 */

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        this.getToolbarInstance()?.let { this.initView(it) }
    }

    private fun initView(toolbar: Toolbar) {
        // Toolbar setup
        setSupportActionBar(toolbar)
    }

    abstract fun getToolbarInstance(): Toolbar?
}
