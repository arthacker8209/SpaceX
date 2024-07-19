package com.deepak.spacex.ui.favouritelaunch

import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

interface FavouriteLaunchClickListener {
    fun launchItemClicked(launchItem: BookmarkItem)
}