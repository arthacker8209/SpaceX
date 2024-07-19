package com.deepak.spacex.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deepak.spacex.data.db.convertors.Converters
import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

@Database(
    entities = [LaunchResponse.LaunchResponseItem::class, LaunchResponse.LaunchSite::class, LaunchResponse.Links::class, LaunchResponse.Rocket::class, BookmarkItem::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class LaunchDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
    abstract fun bookmarkItemDao(): BookmarkedItemDao

    companion object {
        @Volatile
        private var instance: LaunchDatabase? = null

        fun getInstance(context: Context): LaunchDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LaunchDatabase::class.java,
                        "space-launch-dummy.db"
                    ).build()
                }
            }
            return instance!!
        }
    }
}