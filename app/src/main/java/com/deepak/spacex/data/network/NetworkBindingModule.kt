package com.deepak.spacex.data.network

import com.deepak.spacex.data.network.interceptor.NetworkStateChecker
import com.deepak.spacex.data.network.interceptor.NetworkStateCheckerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

@Module
@InstallIn(SingletonComponent::class)
interface NetworkBindingModule {
    @Binds
    fun bindNetworkStateChecker(networkStateCheckerImpl: NetworkStateCheckerImpl): NetworkStateChecker
}