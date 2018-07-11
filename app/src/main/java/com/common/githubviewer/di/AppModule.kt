package com.common.githubviewer.di

import android.app.Application
import com.common.githubviewer.App
import com.common.githubviewer.util.AppPrefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Module
class AppModule(app: App) {

    var mApp: App = app

    @Provides
    @Singleton
    internal fun provideApp(): App {
        return mApp
    }

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return mApp
    }


    @Provides
    @Singleton
    internal fun provideAppPrefs(): AppPrefs = AppPrefs(application = mApp)

}