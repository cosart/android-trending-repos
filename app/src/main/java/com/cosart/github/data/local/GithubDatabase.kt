package com.cosart.github.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.cosart.github.GithubApplication
import com.cosart.github.data.RepositoryData

@Database(version = 1, entities = [RepositoryData::class], exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun repositoryDataDao(): RepositoryDataDao

    companion object {
        private val instance = Room.databaseBuilder(GithubApplication.get(),
                GithubDatabase::class.java, "repos.db")
                .build()

        fun get() = instance
    }
}

@Dao
interface RepositoryDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<RepositoryData>)

    @Query("SELECT * FROM repositories ORDER by stargazers_count DESC")
    fun getAll(): DataSource.Factory<Int, RepositoryData>

    @Query("DELETE FROM repositories")
    fun deleteAll()
}