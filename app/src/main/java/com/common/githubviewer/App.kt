package com.common.githubviewer

import android.app.Application
import android.support.annotation.VisibleForTesting
import com.common.githubviewer.di.*


class App : Application() {

    companion object {
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }


    private lateinit var mAppComponent: AppComponent

    @VisibleForTesting
    private fun setAppComponent(appComponent: AppComponent) {
        mAppComponent = appComponent
    }

    fun getAppComponent() : AppComponent = mAppComponent

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        //configure app component
        val component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .contextModule(ContextModule(this))
                .apiModule(ApiModule())
                .dataBaseModule(DataBaseModule())
                .build()

        //inject application for has access to provided objects
        component.inject(this)

        //set component to local value
        setAppComponent(component)
    }

}