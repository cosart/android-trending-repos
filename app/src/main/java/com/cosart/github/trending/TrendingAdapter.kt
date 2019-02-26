package com.cosart.github.trending

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.cosart.github.data.RepositoryData

class TrendingAdapter(private val listener: RepositoryItemClickListener) :
        PagedListAdapter<RepositoryData, RepositoryViewHolder>(differ) {

    companion object {
        val differ = object : DiffUtil.ItemCallback<RepositoryData>() {
            override fun areItemsTheSame(oldItem: RepositoryData, newItem: RepositoryData): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RepositoryData, newItem: RepositoryData): Boolean =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder =
            RepositoryViewHolder.create(parent)

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) =
            holder.bind(getItem(position), listener)

}