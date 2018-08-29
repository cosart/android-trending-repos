package com.cosart.github.trending

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.cosart.github.R
import com.cosart.github.data.Repository
import com.cosart.github.detail.DetailActivity
import com.cosart.github.detail.EXTRA_REPOSITORY
import kotlinx.android.synthetic.main.activity_trending.*


class TrendingActivity : AppCompatActivity(), RepositoryItemClickListener {

    private lateinit var trendingViewModel: TrendingViewModel
    private val adapter = TrendingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)

        swipeRefresh.setOnRefreshListener {
            trendingViewModel.loadRepositories()
        }

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        trendingViewModel = ViewModelProviders.of(this).get(TrendingViewModel::class.java)
        trendingViewModel.apply {
            trendingData.observe(this@TrendingActivity, Observer {
                swipeRefresh.isRefreshing = false
                if (it == null) return@Observer

                if (it.success && it.data != null) {
                    adapter.setData(it.data)
                } else {
                    Toast.makeText(this@TrendingActivity,
                            R.string.error_loading_data, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onClick(repository: Repository) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_REPOSITORY, repository)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}

interface RepositoryItemClickListener {

    fun onClick(repository: Repository)
}