package com.cosart.github.trending

import androidx.lifecycle.ViewModel
import com.cosart.github.data.GithubRepository

class TrendingViewModel : ViewModel() {

    private val githubRepository = GithubRepository
    val trendingData = githubRepository.loadRepositories()

    init {
        loadRepositories()
    }

    fun loadRepositories() {
        githubRepository.loadRepositories()
    }

    fun refresh() {
        githubRepository.refresh()
    }
}