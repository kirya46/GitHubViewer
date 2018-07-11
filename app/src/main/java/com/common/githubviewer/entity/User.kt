package com.common.githubviewer.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Kirill Stoianov on 09/07/18.
 */

@Entity
data class User(
        @PrimaryKey
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("gists_url")
        var gistsUrl: String = "",
        @SerializedName("repos_url")
        var reposUrl: String = "",
        @SerializedName("two_factor_authentication")
        var twoFactorAuthentication: Boolean = false,
        @SerializedName("following_url")
        var followingUrl: String = "",
        @SerializedName("bio")
        var bio: String? = null,
        @SerializedName("created_at")
        var createdAt: String = "",
        @SerializedName("login")
        var login: String = "",
        @SerializedName("type")
        var type: String = "",
        @SerializedName("blog")
        var blog: String = "",
        @SerializedName("private_gists")
        var privateGists: Int = 0,
        @SerializedName("total_private_repos")
        var totalPrivateRepos: Int = 0,
        @SerializedName("subscriptions_url")
        var subscriptionsUrl: String = "",
        @SerializedName("updated_at")
        var updatedAt: String = "",
        @SerializedName("site_admin")
        var siteAdmin: Boolean = false,
        @SerializedName("disk_usage")
        var diskUsage: Int = 0,
        @SerializedName("collaborators")
        var collaborators: Int = 0,
        @SerializedName("company")
        var company: String = "",
        @SerializedName("owned_private_repos")
        var ownedPrivateRepos: Int = 0,

        @SerializedName("public_repos")
        var publicRepos: Int = 0,
        @SerializedName("gravatar_id")
        var gravatarId: String = "",

        @SerializedName("email")
        var email: String? = "",
        @SerializedName("organizations_url")
        var organizationsUrl: String = "",

        @SerializedName("starred_url")
        var starredUrl: String = "",
        @SerializedName("followers_url")
        var followersUrl: String = "",
        @SerializedName("public_gists")
        var publicGists: Int = 0,
        @SerializedName("url")
        var url: String = "",
        @SerializedName("received_events_url")
        var receivedEventsUrl: String = "",
        @SerializedName("followers")
        var followers: Int = 0,
        @SerializedName("avatar_url")
        var avatarUrl: String = "",
        @SerializedName("events_url")
        var eventsUrl: String = "",
        @SerializedName("html_url")
        var htmlUrl: String = "",
        @SerializedName("following")
        var following: Int = 0,
        @SerializedName("name")
        var name: String = "Unknown",
        @SerializedName("location")
        var location: String? = null,
        @SerializedName("node_id")
        var nodeId: String = "") {

    class Credentials(var userName: String, var password: String) {
        fun toAuthString():String {
            val credenials ="$userName:$password"
            return "Basic " + android.util.Base64.encodeToString(credenials.toByteArray(),android.util.Base64.NO_WRAP)
        }

    }



}


