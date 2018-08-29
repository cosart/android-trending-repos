package com.cosart.github.trending

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.cosart.github.SingleLiveEvent
import com.cosart.github.data.GithubRepository
import com.cosart.github.data.TrendingData

class TrendingViewModel : ViewModel(), Observer<TrendingData?> {

    private val githubRepository = GithubRepository.instance
    val trendingData = SingleLiveEvent<TrendingData>()

    init {
        githubRepository.data.observeForever(this)
        loadRepositories()
    }

    fun loadRepositories() {
        //TODO: Add search functionality, and take user input
        val query = "topic:android"
        val order = "desc"
        val sort = "stars"

        githubRepository.loadRepositories(query, order, sort)
    }

    override fun onChanged(t: TrendingData?) {
        trendingData.postValue(t)
    }

    override fun onCleared() {
        super.onCleared()
        githubRepository.data.removeObserver(this)
    }
}