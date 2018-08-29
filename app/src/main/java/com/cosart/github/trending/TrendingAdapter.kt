package com.cosart.github.trending

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cosart.github.R
import com.cosart.github.data.Repository
import com.cosart.github.data.RepositoryDiffCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repository_view.view.*

class TrendingAdapter(private val listener: RepositoryItemClickListener) : RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {

    private val repositories = ArrayList<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repositories[position], listener)
    }

    override fun getItemCount(): Int = repositories.size

    fun setData(newData: List<Repository>) {
        val result = DiffUtil.calculateDiff(RepositoryDiffCallback(repositories, newData))

        repositories.clear()
        repositories.addAll(newData)

        result.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

        fun bind(repository: Repository, listener: RepositoryItemClickListener) = with(item) {
            name.text = repository.full_name
            owner.text = repository.owner.login

            Picasso.get()
                    .load(repository.owner.avatar_url)
                    .into(avatar)

            setOnClickListener {
                listener.onClick(repository)
            }
        }
    }
}