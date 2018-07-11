package com.common.githubviewer.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences


/**
 * Created by Kirill Stoianov on 09/07/18.
 */
class AppPrefs(application: Application) {

    companion object {

        val TAG: String = AppPrefs::class.java.simpleName

        private const val SHARED_PREFERENCES_NAME: String = "app_shared_preferences"

        private const val LAST_SEARCH_QUERY_TAG = "LAST_SEARCH_QUERY_TAG"

        private const val USER_AUTH_STRING = "USER_AUTH_STRING"


        private const val USER_LOGIN = "USER_LOGIN"
    }

    /**
     * Instance of application preferences.
     */
    private var mPref: SharedPreferences

    init {
        mPref = application.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun clearAll(){
        mPref.edit().clear().apply()
    }

    fun clearUserCredentials(){
        mPref.edit().putString(USER_LOGIN,null).apply()
        mPref.edit().putString(USER_AUTH_STRING,null).apply()
    }

    fun saveLastSearchQuery(searchQuery: String) {
        mPref.edit().putString(LAST_SEARCH_QUERY_TAG, searchQuery).apply()
    }

    fun getLastSearchQuery(): String {
        return mPref.getString(LAST_SEARCH_QUERY_TAG, "")
    }

    fun saveUserAuthString(userAuthString: String) {
        mPref.edit().putString(USER_AUTH_STRING, userAuthString).apply()
    }

    fun getUserAuthString(): String? {
        return mPref.getString(USER_AUTH_STRING, null)
    }

    fun saveUserLogin(login: String) {
        mPref.edit().putString(USER_LOGIN, login).apply()
    }

    fun getUserLogin(): String? {
        return mPref.getString(USER_LOGIN, null)
    }
}