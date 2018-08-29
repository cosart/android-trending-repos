package com.cosart.github.data

import android.support.v7.util.DiffUtil

class RepositoryDiffCallback(private val old: List<Repository>, private val new: List<Repository>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id == new[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].equals(new[newItemPosition])
    }

}