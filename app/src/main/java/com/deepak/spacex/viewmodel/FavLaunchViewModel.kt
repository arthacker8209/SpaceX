package com.deepak.spacex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.spacex.common.CoroutinesDispatcherProvider
import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse
import com.deepak.spacex.data.network.ApiResult
import com.deepak.spacex.data.network.ViewState
import com.deepak.spacex.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class FavLaunchViewModel @Inject constructor(
    private val repository: LaunchRepository,
    private val dispatcherProvider: CoroutinesDispatcherProvider
): ViewModel() {

    private val _favLaunchLiveData =
        MutableLiveData<ViewState<List<BookmarkItem>>>()
    val favLaunchLiveData: LiveData<ViewState<List<BookmarkItem>>> get() = _favLaunchLiveData

    fun getLaunchData(){
        _favLaunchLiveData.value = ViewState.Loading

        viewModelScope.launch(dispatcherProvider.io) {
            val result = repository.fetchFavoriteData()
                if (result.isNotEmpty()){
                    withContext(dispatcherProvider.main){
                        _favLaunchLiveData.value = ViewState.Success(result)
                    }
                }else{
                    withContext(dispatcherProvider.main){
                        _favLaunchLiveData.value = ViewState.Error("No Data Found")
                }
            }
        }
    }
}