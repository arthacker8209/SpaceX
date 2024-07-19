package com.deepak.spacex.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

@Keep
@Entity(tableName = "bookmark_items")
@Parcelize
data class BookmarkItem(

    @PrimaryKey
    @SerializedName("flight_number") var flightNumber: Int? = null,
    @SerializedName("mission_name") var missionName: String? = null,
    @SerializedName("launch_year") var launchYear: String? = null,
    @SerializedName("launch_date_unix") var launchDateUnix: Int? = null,
    @SerializedName("launch_date_utc") var launchDateUtc: String? = null,
    @SerializedName("launch_date_local") var launchDateLocal: String? = null,

    @Embedded
    @SerializedName("rocket") var rocket: LaunchResponse.Rocket? = LaunchResponse.Rocket(),

    @Embedded
    @SerializedName("launch_site") var launchSite: LaunchResponse.LaunchSite? = LaunchResponse.LaunchSite(),

    @Embedded
    @SerializedName("links") var links: LaunchResponse.Links? = LaunchResponse.Links(),

    @SerializedName("details") var details: String? = null,

    @ColumnInfo("is_favourite")
    var isFavourite: Boolean = false


) : Parcelable