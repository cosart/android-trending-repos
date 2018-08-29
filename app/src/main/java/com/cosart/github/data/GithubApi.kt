package com.cosart.github.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubApi {

    @GET("search/repositories")
    fun searchTrending(@Query("q") query: String,
                       @Query("sort") sort: String,
                       @Query("order") order: String): Call<TrendingRepositoriesResponse>


}