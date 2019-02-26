package com.cosart.github.data

import androidx.paging.PagedList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class GithubBoundaryCallback(val request: () -> Call<TrendingRepositoriesResponse>, val cb: (List<RepositoryData>) -> Unit) : PagedList.BoundaryCallback<RepositoryData>() {

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
    }

    override fun onItemAtFrontLoaded(itemAtFront: RepositoryData) {
        super.onItemAtFrontLoaded(itemAtFront)
    }

    override fun onItemAtEndLoaded(itemAtEnd: RepositoryData) {
        request.invoke().enqueue(object : Callback<TrendingRepositoriesResponse> {
            override fun onFailure(call: Call<TrendingRepositoriesResponse>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<TrendingRepositoriesResponse>, response: Response<TrendingRepositoriesResponse>) {
                GlobalScope.launch {
                    response.body()?.let {
                        cb.invoke(it.items)
                    }
                }
            }
        })
    }
}