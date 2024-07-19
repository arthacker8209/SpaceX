package com.deepak.spacex.viewmodel

import androidx.compose.runtime.traceEventEnd
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.spacex.common.CoroutinesDispatcherProvider
import com.deepak.spacex.data.db.BookmarkedItemDao
import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse
import com.deepak.spacex.data.network.ApiResult
import com.deepak.spacex.data.network.ViewState
import com.deepak.spacex.repository.LaunchContract
import com.deepak.spacex.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val repository: LaunchRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
): ViewModel() {

    private val _launchLiveData =
        MutableLiveData<ViewState<List<LaunchResponse.LaunchResponseItem>>>()
    val launchLiveData: LiveData<ViewState<List<LaunchResponse.LaunchResponseItem>>> get() = _launchLiveData

    private val _bookmarkLiveData =
        MutableLiveData<ViewState<Boolean>>()
    val bookmarkLiveData: LiveData<ViewState<Boolean>> get() = _bookmarkLiveData

    private val _unBookmarkLiveData =
        MutableLiveData<ViewState<Boolean>>()
    val unBookmarkLiveData: LiveData<ViewState<Boolean>> get() = _unBookmarkLiveData


    fun bookmarkLaunch(launchItem: BookmarkItem) {
        _bookmarkLiveData.value = ViewState.Loading
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = repository.bookmarkLaunch(launchItem)) {
                 true -> {
                    withContext(dispatcherProvider.main) {
                        _bookmarkLiveData.value = ViewState.Error("")
                    }
                }

                false -> {
                    withContext(dispatcherProvider.main) {
                        _bookmarkLiveData.value = ViewState.Success(true)
                    }
                }
            }
        }
    }

    fun unBookmarkLaunch(launchItem: BookmarkItem) {
        _unBookmarkLiveData.value = ViewState.Loading
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = repository.unBookmarkLaunch(launchItem)) {
                 true -> {
                    withContext(dispatcherProvider.main) {
                        _unBookmarkLiveData.value = ViewState.Error("")
                    }
                }

                false -> {
                    withContext(dispatcherProvider.main) {
                        _unBookmarkLiveData.value = ViewState.Success(true)
                    }
                }

            }

        }
    }

    fun getLaunchData() {
        _launchLiveData.value = ViewState.Loading

        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = repository.fetchDashBoardData()) {
                is ApiResult.Error -> {
                    withContext(dispatcherProvider.main) {
                        _launchLiveData.value = ViewState.Error(result.message)
                    }
                }

                is ApiResult.Success -> {
                    withContext(dispatcherProvider.main) {
                        _launchLiveData.value = ViewState.Success(result.data)
                    }
                }
            }
        }
    }
}