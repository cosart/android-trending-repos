package com.cosart.github.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Repositories")
data class RepositoryData(@PrimaryKey
                          val id: Int,
                          val full_name: String,
                          val description: String?,
                          val stargazers_count: Int,
                          val score: Double,
                          @Embedded val owner: Owner) : Parcelable

@Parcelize
data class Owner(@Json(name = "id")
                 @PrimaryKey
                 val owner_id: Int,
                 val login: String,
                 val avatar_url: String) : Parcelable

data class TrendingRepositoriesResponse(val total_count: Int,
                                        val items: List<RepositoryData>)