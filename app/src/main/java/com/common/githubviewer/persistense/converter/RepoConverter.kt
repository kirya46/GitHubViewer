package com.common.githubviewer.persistense.converter

import android.arch.persistence.room.TypeConverter
import com.common.githubviewer.entity.Repo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
class RepoConverter{
    @TypeConverter
    fun convertRepoListToString(repoList:List<Repo>): String{

        val gson = Gson()
        val type = object : TypeToken<List<Repo>>() {}.type
        return gson.toJson(repoList, type)
    }

    @TypeConverter
    fun convertStringToRepoList(repoListString: String): List<Repo> {


        if ( "" == repoListString) return ArrayList()

        val gson = Gson()
        val type = object : TypeToken<List<Repo>>() {

        }.type
        return gson.fromJson(repoListString, type)
    }
}