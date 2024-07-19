package com.deepak.spacex.ui.favouritelaunch

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepak.spacex.common.base.BaseFragment
import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse
import com.deepak.spacex.data.network.ViewState
import com.deepak.spacex.databinding.FragmentFavouriteLaunchBinding
import com.deepak.spacex.ui.launch.LaunchAdapter
import com.deepak.spacex.ui.launch.LaunchItemClickListener
import com.deepak.spacex.viewmodel.FavLaunchViewModel
import com.deepak.spacex.viewmodel.LaunchViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

@AndroidEntryPoint
class FavouriteLaunchFragment : BaseFragment<FragmentFavouriteLaunchBinding>(),
    FavouriteLaunchClickListener {

    private val viewModel by viewModels<FavLaunchViewModel>()
    private val launchAdapter by lazy { FavLaunchAdapter(this) }

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentFavouriteLaunchBinding =
        FragmentFavouriteLaunchBinding.inflate(layoutInflater)


    override fun fetchData() {
        viewModel.getLaunchData()
        observe()
    }

    private fun observe() {
        viewModel.favLaunchLiveData.observe(this) { state ->
            when (state) {
                is ViewState.Error -> {
                    dismissLoadingSpinner()
                }

                ViewState.Loading -> {
                    showLoadingSpinner()
                }

                is ViewState.Success -> {
                    dismissLoadingSpinner()
                    showLaunches(state.data)
                }
            }
        }
    }

    private fun dismissLoadingSpinner() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun showLoadingSpinner() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun showLaunches(launchesList: List<BookmarkItem>) {
        if (launchesList.isEmpty()) {
            binding.rvFavLaunch.visibility = View.GONE
        } else {
            binding.rvFavLaunch.visibility = View.VISIBLE
            launchAdapter.submitList(launchesList)
        }
    }

    override fun setupView() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val verticalLayoutManager = LinearLayoutManager(requireContext())
        binding.rvFavLaunch.apply {
            layoutManager = verticalLayoutManager
            adapter = launchAdapter
        }
    }

    override fun launchItemClicked(launchItem: BookmarkItem) {

    }

}