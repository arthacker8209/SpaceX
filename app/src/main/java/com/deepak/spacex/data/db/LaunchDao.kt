package com.deepak.spacex.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deepak.spacex.data.model.LaunchResponse

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<LaunchResponse.LaunchResponseItem>)

    @Query("SELECT * FROM LAUNCHITEM")
    suspend fun getAll(): List<LaunchResponse.LaunchResponseItem>

    @Query("DELETE FROM LAUNCHITEM")
    suspend fun deleteAll()


}