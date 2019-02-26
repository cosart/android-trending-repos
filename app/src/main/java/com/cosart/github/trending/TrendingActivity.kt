package com.cosart.github.trending

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cosart.github.R
import com.cosart.github.data.RepositoryData
import com.cosart.github.detail.DetailActivity
import com.cosart.github.detail.EXTRA_REPOSITORY
import kotlinx.android.synthetic.main.activity_trending.*


class TrendingActivity : AppCompatActivity(), RepositoryItemClickListener {

    private lateinit var trendingViewModel: TrendingViewModel
    private val adapter = TrendingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        swipeRefresh.setOnRefreshListener { trendingViewModel.refresh() }

        trendingViewModel = ViewModelProviders.of(this).get(TrendingViewModel::class.java)
        trendingViewModel.apply {
            trendingData.observe(this@TrendingActivity, Observer {
                swipeRefresh.isRefreshing = false
                adapter.submitList(it)
            })
        }
    }

    override fun onClick(repositoryData: RepositoryData) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_REPOSITORY, repositoryData)
        startActivity(intent)
    }
}

interface RepositoryItemClickListener {

    fun onClick(repositoryData: RepositoryData)
}