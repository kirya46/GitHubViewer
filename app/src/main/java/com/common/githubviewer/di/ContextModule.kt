package com.common.githubviewer.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Module
class ContextModule(context: Context) {

    var mContext : Context = context

    @Provides
    @Singleton
    fun provideContext(): Context {
        return mContext;
    }

}
