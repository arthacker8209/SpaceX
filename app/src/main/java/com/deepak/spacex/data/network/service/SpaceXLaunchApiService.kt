package com.deepak.spacex.data.network.service


import com.deepak.spacex.data.model.LaunchResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

interface SpaceXLaunchApiService {
    @GET("v3/launches")
    suspend fun fetchLaunchData(): Response<List<LaunchResponse.LaunchResponseItem>>
}