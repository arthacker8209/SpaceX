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

class LaunchResponse {

    @Parcelize
    @Entity(tableName = "launchitem")
    data class LaunchResponseItem(

        @PrimaryKey
        @SerializedName("flight_number") var flightNumber: Int? = null,
        @SerializedName("mission_name") var missionName: String? = null,
        @SerializedName("launch_year") var launchYear: String? = null,
        @SerializedName("launch_date_unix") var launchDateUnix: Int? = null,
        @SerializedName("launch_date_utc") var launchDateUtc: String? = null,
        @SerializedName("launch_date_local") var launchDateLocal: String? = null,

        @Embedded
        @SerializedName("rocket") var rocket: Rocket? = Rocket(),

        @Embedded
        @SerializedName("launch_site") var launchSite: LaunchSite? = LaunchSite(),

        @Embedded
        @SerializedName("links") var links: Links? = Links(),

        @SerializedName("details") var details: String? = null,

        @ColumnInfo(name = "is_favorite")
        var isFavorite: Boolean = false
    ) : Parcelable

    @Parcelize
    @Entity(tableName = "launchsite")
    data class LaunchSite(

        @ColumnInfo(name = "site_id")
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,

        @SerializedName("site_name") var siteName: String? = null,
        @SerializedName("site_name_long") var siteNameLong: String? = null
    ) : Parcelable

    @Parcelize
    @Entity(tableName = "links")
    data class Links(
        @ColumnInfo(name = "links_id")
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,

        @SerializedName("mission_patch") var missionPatch: String? = null,
        @SerializedName("mission_patch_small") var missionPatchSmall: String? = null,
        @SerializedName("reddit_campaign") var redditCampaign: String? = null,
        @SerializedName("reddit_launch") var redditLaunch: String? = null,
        @SerializedName("reddit_recovery") var redditRecovery: String? = null,
        @SerializedName("reddit_media") var redditMedia: String? = null,
        @SerializedName("presskit") var presskit: String? = null,
        @SerializedName("article_link") var articleLink: String? = null,
        @SerializedName("wikipedia") var wikipedia: String? = null,
        @SerializedName("video_link") var videoLink: String? = null,
        @SerializedName("youtube_id") var youtubeId: String? = null,
        @SerializedName("flickr_images") var flickrImages: ArrayList<String> = arrayListOf()
    ) : Parcelable

    @Parcelize
    @Entity(tableName = "rocket")
    data class Rocket(
        @ColumnInfo(name = "rocket_id")
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        @SerializedName("rocket_name") var rocketName: String? = null,
        @SerializedName("rocket_type") var rocketType: String? = null,
    ) : Parcelable
}