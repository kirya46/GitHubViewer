package com.common.githubviewer.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Entity
data class SearchResult(
        @SerializedName("total_count")
        var totalCount: Long,

        @SerializedName("incomplete_results")
        var incompleteResults: Boolean,

        @SerializedName("items")
        var items: List<Repo>,

        /*Non-api,  fields*/
        @PrimaryKey
        var searchQuery: String,

        var createdTime:Long
) {


    constructor() : this(0, false, ArrayList(), "",System.currentTimeMillis())

    override fun toString(): String {
        return "SearchResult(totalCount=$totalCount, incompleteResults=$incompleteResults, items=$items)"
    }
}