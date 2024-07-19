package com.deepak.spacex.repository

import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse
import com.deepak.spacex.data.network.ApiResult

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

class LaunchContract {
    interface Repository {
        suspend fun fetchDashBoardData(): ApiResult<List<LaunchResponse.LaunchResponseItem>>
        suspend fun fetchFavoriteData(): List<BookmarkItem>
        suspend fun bookmarkLaunch(launchResponse: BookmarkItem): Boolean
        suspend fun unBookmarkLaunch(launchResponse: BookmarkItem): Boolean
    }
}
