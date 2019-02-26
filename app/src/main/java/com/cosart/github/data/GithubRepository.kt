package com.cosart.github.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.cosart.github.data.local.GithubDatabase
import com.cosart.github.data.remote.GithubApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

object GithubRepository {

    private val repositoryDb = GithubDatabase.get()
    private val repositoryDao = GithubDatabase.get().repositoryDataDao()
    private val githubApi: GithubApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GithubApi::class.java)

    private var nextPage = -1

    private fun request(): Call<TrendingRepositoriesResponse> {
        nextPage += 1
        return githubApi.searchTrending(query = "android", order = "desc", sort = "stars", page = nextPage)
    }

    fun loadRepositories(): LiveData<PagedList<RepositoryData>> {
        val boundaryCallback = GithubBoundaryCallback(
                request = { request() },
                cb = { repositoryDao.insert(it) }
        )

        return repositoryDao.getAll().toLiveData(pageSize = 30, boundaryCallback = boundaryCallback)
    }

    fun refresh() {
        nextPage = 0

        request().enqueue(object : Callback<TrendingRepositoriesResponse> {
            override fun onFailure(call: Call<TrendingRepositoriesResponse>, t: Throwable) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<TrendingRepositoriesResponse>, response: Response<TrendingRepositoriesResponse>) {
                nextPage += 1
                GlobalScope.launch {
                    response.body()?.let {
                        repositoryDb.runInTransaction {
                            repositoryDao.deleteAll()
                            repositoryDao.insert(it.items)
                        }
                    }
                }
            }
        }
        )
    }
}

private const val BASE_URL = "https://api.github.com"