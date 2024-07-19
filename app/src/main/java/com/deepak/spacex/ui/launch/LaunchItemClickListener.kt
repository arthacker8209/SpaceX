package com.deepak.spacex.ui.launch

import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse

interface LaunchItemClickListener {
    fun launchItemClicked(launchItem: LaunchResponse.LaunchResponseItem)
    fun bookmarkItemClicked(launchItem: BookmarkItem)
    fun unBookmarkItemClicked(launchItem: BookmarkItem)
}