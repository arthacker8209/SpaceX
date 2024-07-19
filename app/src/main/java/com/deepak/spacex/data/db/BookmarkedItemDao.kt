package com.deepak.spacex.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse

@Dao
interface BookmarkedItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmarkedItem: BookmarkItem): Long

    @Delete
    suspend fun delete(bookmarkedItem: BookmarkItem): Int

    @Query("SELECT * FROM bookmark_items")
    suspend fun getAllBookmarkedItems(): List<BookmarkItem>
}