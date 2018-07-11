package com.common.githubviewer.di

import com.common.githubviewer.api.GitHubApi
import com.common.githubviewer.persistense.dao.RepoDao
import com.common.githubviewer.persistense.dao.SearchResultDao
import com.common.githubviewer.repository.GitHubRepoRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Module
class RepositoryModule {

    companion object {
        val TAG: String = RepositoryModule::class.java.simpleName
    }

    @Provides
    fun providesGitHubRepoRepository(searchResultDao: SearchResultDao,repoDao: RepoDao, gitHubApi: GitHubApi): GitHubRepoRepository {
        return GitHubRepoRepository(searchResultDao,repoDao, gitHubApi)
    }

}