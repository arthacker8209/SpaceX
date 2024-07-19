package com.deepak.spacex.repository

import androidx.room.PrimaryKey
import com.deepak.spacex.data.db.BookmarkedItemDao
import com.deepak.spacex.data.model.LaunchResponse
import com.deepak.spacex.data.network.ApiResult
import com.deepak.spacex.data.network.getResult
import com.deepak.spacex.data.network.service.SpaceXLaunchApiService
import com.deepak.spacex.data.db.LaunchDao
import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.network.ErrorType
import javax.inject.Inject


/**
 * Created by Deepak Kumawat on 18/07/24.
 */

class LaunchRepository @Inject constructor(
    private val spaceXLaunchApiService: SpaceXLaunchApiService,
    private val launchDao: LaunchDao,
    private val bookmarkedItemDao: BookmarkedItemDao
): LaunchContract.Repository {

    override suspend fun fetchDashBoardData(): ApiResult<List<LaunchResponse.LaunchResponseItem>> {
        val networkResult = getResult { spaceXLaunchApiService.fetchLaunchData() }
        val dbResult = bookmarkedItemDao.getAllBookmarkedItems()
        var updatedList = mutableListOf<LaunchResponse.LaunchResponseItem>()
        if (networkResult is ApiResult.Success) {
            launchDao.deleteAll()
            val bookmarkedIds = dbResult.map { it.flightNumber }.toSet()
            if (dbResult.isNotEmpty()){
                 updatedList = networkResult.data.map { item ->
                     if (bookmarkedIds.contains(item.flightNumber)) {
                         item.copy(isFavorite = true)
                     } else {
                         item
                     }
                 }.toMutableList()
                launchDao.insertAll(updatedList)
                return ApiResult.Success(updatedList)
            }else{
                launchDao.insertAll(networkResult.data)
                return ApiResult.Success(networkResult.data)
            }
        } else if (networkResult is ApiResult.Error && networkResult.type == ErrorType.NetworkException) {
            val launchData = launchDao.getAll()
            if (launchData.isNotEmpty()) {
                return ApiResult.Success(launchData)
            }
        }
        return networkResult
    }

    override suspend fun fetchFavoriteData(): List<BookmarkItem> {
         return bookmarkedItemDao.getAllBookmarkedItems()
    }

    override suspend fun bookmarkLaunch(launchResponse: BookmarkItem): Boolean{
        try { val rowId = bookmarkedItemDao.insert(launchResponse)
            return rowId > -1L
        }catch (e: Exception){
            println(e.toString())
        }
       return false
    }

    override suspend fun unBookmarkLaunch(launchResponse: BookmarkItem): Boolean{
       val rowsDeleted = bookmarkedItemDao.delete(launchResponse)
        return rowsDeleted > 0
    }
}