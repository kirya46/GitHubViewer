package com.common.githubviewer.view.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.common.githubviewer.R
import com.common.githubviewer.entity.Repo
import kotlinx.android.synthetic.main.activity_main_list_item.view.*

/**
 * Created by Kirill Stoianov on 09/07/18.
 */
class RepoAdapter(private var listener: Listener, private var dataList: List<Repo>) : RecyclerView.Adapter<RepoAdapter.ViewHolder>() {


    interface Listener {
        fun onOpenRepo(repo: Repo?)
        fun onDeleteRepoFromResults(repo: Repo?)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView? = itemView.tvName
        val tvStarCount: TextView? = itemView.tvStarCount
        val tvViewed: TextView? = itemView.tvViewed
        val ivClose: ImageView? = itemView.ivClose
    }

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewRow = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_list_item, parent, false)
        return ViewHolder(viewRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = dataList[position]

        if (repo.viewed){
            holder.tvName?.setTextColor(Color.MAGENTA)
            holder.tvViewed?.visibility = View.VISIBLE
        }else{
            holder.tvName?.setTextColor(Color.WHITE)
            holder.tvViewed?.visibility = View.INVISIBLE
        }

        //set repo name and star's count
        holder.tvName?.text = repo.name
        holder.tvStarCount?.text = repo.stargazersCount.toString()

        //set actions
        holder.itemView?.setOnClickListener {
            listener.onOpenRepo(repo)
        }

        holder.ivClose?.setOnClickListener {
            listener.onDeleteRepoFromResults(repo)
        }
    }


    fun updateData(list: List<Repo>) {
        this.dataList = list
        this.notifyDataSetChanged()
    }

    fun getData(): List<Repo> {
        return dataList
    }
}