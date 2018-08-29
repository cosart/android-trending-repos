package com.cosart.github.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(val id: Int, val full_name: String, val description: String,
                      val stargazers_count: Int, val score: Double, val owner: Owner) : Parcelable

@Parcelize
data class Owner(val id: Int, val login: String, val avatar_url: String) : Parcelable

data class TrendingRepositoriesResponse(val total_count: Int, val items: List<Repository>)

data class TrendingData(val success: Boolean, val data: List<Repository>?)
