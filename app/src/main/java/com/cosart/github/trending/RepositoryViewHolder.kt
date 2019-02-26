package com.cosart.github.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cosart.github.R
import com.cosart.github.data.RepositoryData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repository_view.view.*


class RepositoryViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {
    companion object {
        fun create(parent: ViewGroup): RepositoryViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_view, parent, false)
            return RepositoryViewHolder(view)
        }
    }

    fun bind(repositoryData: RepositoryData?, listener: RepositoryItemClickListener) = with(item) {
        if (repositoryData == null) return@with
        name.text = repositoryData.full_name
        owner.text = repositoryData.owner.login

        Picasso.get()
                .load(repositoryData.owner.avatar_url)
                .into(avatar)

        setOnClickListener {
            listener.onClick(repositoryData)
        }
    }
}