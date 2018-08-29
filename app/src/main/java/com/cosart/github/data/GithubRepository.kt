package com.cosart.github.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class GithubRepository {

    private val repositories = MutableLiveData<TrendingData>()
    private val githubApi: GithubApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GithubApi::class.java)

    //Exposed data
    val data: LiveData<TrendingData> = repositories

    fun loadRepositories(query: String, order: String, sort: String) {
        githubApi.searchTrending(query, sort, order).enqueue(object : Callback<TrendingRepositoriesResponse> {
            override fun onFailure(call: Call<TrendingRepositoriesResponse>, t: Throwable) {
                repositories.postValue(TrendingData(false, null))
            }

            override fun onResponse(call: Call<TrendingRepositoriesResponse>, response: Response<TrendingRepositoriesResponse>) {
                val body = response.body()
                repositories.postValue(TrendingData(body != null, body?.items))
            }

        })
    }

    private object Holder {
        val INSTANCE = GithubRepository()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
    }
}

private const val BASE_URL = "https://api.github.com"
private const val TAG = "GithubRepository"