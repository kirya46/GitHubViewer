package com.common.githubviewer.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Entity
data class Repo(
        @PrimaryKey
        @SerializedName("full_name")
        var fullName: String = "",
        @SerializedName("stargazers_count")
        var stargazersCount: Int = 0,
        @SerializedName("pushed_at")
        var pushedAt: String = "",
        @SerializedName("subscription_url")
        var subscriptionUrl: String = "",
        @SerializedName("language")
        var language: String = "",
        @SerializedName("branches_url")
        var branchesUrl: String = "",
        @SerializedName("issue_comment_url")
        var issueCommentUrl: String = "",
        @SerializedName("labels_url")
        var labelsUrl: String = "",
        @SerializedName("subscribers_url")
        var subscribersUrl: String = "",
        @SerializedName("releases_url")
        var releasesUrl: String = "",
        @SerializedName("svn_url")
        var svnUrl: String = "",
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("forks")
        var forks: Int = 0,
        @SerializedName("archive_url")
        var archiveUrl: String = "",
        @SerializedName("git_refs_url")
        var gitRefsUrl: String = "",
        @SerializedName("forks_url")
        var forksUrl: String = "",
        @SerializedName("statuses_url")
        var statusesUrl: String = "",
        @SerializedName("ssh_url")
        var sshUrl: String = "",
        /*
        need convert to OBJECT instead String
        @SerializedName("license")
         var license: String? = null,*/

        @SerializedName("size")
        var size: Int = 0,
        @SerializedName("languages_url")
        var languagesUrl: String = "",
        @SerializedName("html_url")
        var htmlUrl: String = "",
        @SerializedName("collaborators_url")
        var collaboratorsUrl: String = "",
        @SerializedName("clone_url")
        var cloneUrl: String = "",
        @SerializedName("name")
        var name: String = "",
        @SerializedName("pulls_url")
        var pullsUrl: String = "",
        @SerializedName("default_branch")
        var defaultBranch: String = "",
        @SerializedName("hooks_url")
        var hooksUrl: String = "",
        @SerializedName("trees_url")
        var treesUrl: String = "",
        @SerializedName("tags_url")
        var tagsUrl: String = "",
        @SerializedName("private")
        var private: Boolean = false,
        @SerializedName("contributors_url")
        var contributorsUrl: String = "",
        @SerializedName("has_downloads")
        var hasDownloads: Boolean = false,
        @SerializedName("notifications_url")
        var notificationsUrl: String = "",
        @SerializedName("open_issues_count")
        var openIssuesCount: Int = 0,
        @SerializedName("description")
        var description: String? = null,
        @SerializedName("created_at")
        var createdAt: String = "",
        @SerializedName("watchers")
        var watchers: Int = 0,
        @SerializedName("keys_url")
        var keysUrl: String = "",
        @SerializedName("deployments_url")
        var deploymentsUrl: String = "",
        @SerializedName("has_projects")
        var hasProjects: Boolean = false,
        @SerializedName("archived")
        var archived: Boolean = false,
        @SerializedName("has_wiki")
        var hasWiki: Boolean = false,
        @SerializedName("updated_at")
        var updatedAt: String = "",
        @SerializedName("comments_url")
        var commentsUrl: String = "",
        @SerializedName("stargazers_url")
        var stargazersUrl: String = "",
        @SerializedName("git_url")
        var gitUrl: String = "",
        @SerializedName("has_pages")
        var hasPages: Boolean = false,
        @SerializedName("commits_url")
        var commitsUrl: String = "",
        @SerializedName("compare_url")
        var compareUrl: String = "",
        @SerializedName("git_commits_url")
        var gitCommitsUrl: String = "",
        @SerializedName("blobs_url")
        var blobsUrl: String = "",
        @SerializedName("git_tags_url")
        var gitTagsUrl: String = "",
        @SerializedName("merges_url")
        var mergesUrl: String = "",
        @SerializedName("downloads_url")
        var downloadsUrl: String = "",
        @SerializedName("has_issues")
        var hasIssues: Boolean = false,
        @SerializedName("url")
        var url: String = "",
        @SerializedName("contents_url")
        var contentsUrl: String = "",
        @SerializedName("mirror_url")
        var mirrorUrl: String? = null,
        @SerializedName("milestones_url")
        var milestonesUrl: String = "",
        @SerializedName("teams_url")
        var teamsUrl: String = "",
        @SerializedName("fork")
        var fork: Boolean = false,
        @SerializedName("issues_url")
        var issuesUrl: String = "",
        @SerializedName("events_url")
        var eventsUrl: String = "",
        @SerializedName("issue_events_url")
        var issueEventsUrl: String = "",
        @SerializedName("assignees_url")
        var assigneesUrl: String = "",
        @SerializedName("open_issues")
        var openIssues: Int = 0,
        @SerializedName("watchers_count")
        var watchersCount: Int = 0,
        @SerializedName("node_id")
        var nodeId: String = "",
        @SerializedName("homepage")
        var homepage: String? = null,
        @SerializedName("forks_count")
        var forksCount: Int = 0,

        /*non-api fields*/
        var viewed: Boolean = false,
        var order: Int = DEFAULT_ORDER

) : Comparable<Repo> {

    companion object {
        const val DEFAULT_ORDER: Int = Int.MIN_VALUE
    }

    override fun compareTo(other: Repo): Int {

        //sort by custom user order if it exist
        if (this.order != DEFAULT_ORDER && other.order != DEFAULT_ORDER) {
            return when {
                this.order > other.order -> 1
                this.order < other.order -> -1
                else -> 0
            }
        }

        //otherwise sort by stars
        return when {
            this.stargazersCount > other.stargazersCount -> -1
            this.stargazersCount < other.stargazersCount -> 1
            else -> 0
        }
    }

    override fun toString(): String {
        return "Repo(fullName='$fullName', stargazersCount=$stargazersCount, pushedAt='$pushedAt', subscriptionUrl='$subscriptionUrl', language='$language', branchesUrl='$branchesUrl', issueCommentUrl='$issueCommentUrl', labelsUrl='$labelsUrl', subscribersUrl='$subscribersUrl', releasesUrl='$releasesUrl', svnUrl='$svnUrl', id=$id, forks=$forks, archiveUrl='$archiveUrl', gitRefsUrl='$gitRefsUrl', forksUrl='$forksUrl', statusesUrl='$statusesUrl', sshUrl='$sshUrl', size=$size, languagesUrl='$languagesUrl', htmlUrl='$htmlUrl', collaboratorsUrl='$collaboratorsUrl', cloneUrl='$cloneUrl', name='$name', pullsUrl='$pullsUrl', defaultBranch='$defaultBranch', hooksUrl='$hooksUrl', treesUrl='$treesUrl', tagsUrl='$tagsUrl', private=$private, contributorsUrl='$contributorsUrl', hasDownloads=$hasDownloads, notificationsUrl='$notificationsUrl', openIssuesCount=$openIssuesCount, description=$description, createdAt='$createdAt', watchers=$watchers, keysUrl='$keysUrl', deploymentsUrl='$deploymentsUrl', hasProjects=$hasProjects, archived=$archived, hasWiki=$hasWiki, updatedAt='$updatedAt', commentsUrl='$commentsUrl', stargazersUrl='$stargazersUrl', gitUrl='$gitUrl', hasPages=$hasPages, commitsUrl='$commitsUrl', compareUrl='$compareUrl', gitCommitsUrl='$gitCommitsUrl', blobsUrl='$blobsUrl', gitTagsUrl='$gitTagsUrl', mergesUrl='$mergesUrl', downloadsUrl='$downloadsUrl', hasIssues=$hasIssues, url='$url', contentsUrl='$contentsUrl', mirrorUrl=$mirrorUrl, milestonesUrl='$milestonesUrl', teamsUrl='$teamsUrl', fork=$fork, issuesUrl='$issuesUrl', eventsUrl='$eventsUrl', issueEventsUrl='$issueEventsUrl', assigneesUrl='$assigneesUrl', openIssues=$openIssues, watchersCount=$watchersCount, nodeId='$nodeId', homepage=$homepage, forksCount=$forksCount, viewed=$viewed)"
    }
}