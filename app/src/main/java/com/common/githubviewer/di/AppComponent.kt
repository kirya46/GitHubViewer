package com.common.githubviewer.di

import com.common.githubviewer.App
import com.common.githubviewer.interactor.MainInteractor
import com.common.githubviewer.interactor.SingInInteractor
import com.common.githubviewer.presenter.MainPresenter
import com.common.githubviewer.view.activity.MainActivity
import com.common.githubviewer.view.activity.SingInActivity
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Singleton
@Component(
        modules = [
            (AppModule::class),
            (ContextModule::class),
            (DataBaseModule::class),
            (ApiModule::class),
            RepositoryModule::class
        ]
)
interface AppComponent {
    fun inject(app: App)

    fun inject(mainInteractor: MainInteractor)
    fun inject(mainPresenter: MainPresenter)
    fun inject(mainActivity: MainActivity)

    fun inject(singInInteractor: SingInInteractor)
    fun inject(singInActivity: SingInActivity)
}