package com.deepak.spacex.ui.launch

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepak.spacex.common.base.BaseFragment
import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse
import com.deepak.spacex.data.network.ViewState
import com.deepak.spacex.databinding.FragmentLaunchBinding
import com.deepak.spacex.viewmodel.LaunchViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Deepak Kumawat on 18/07/24.
 */

@AndroidEntryPoint
class FragmentLaunch : BaseFragment<FragmentLaunchBinding>(), LaunchItemClickListener {

    private val searchDebounce = 500L
    private val viewModel by viewModels<LaunchViewModel>()
    private val launchAdapter by lazy { LaunchAdapter(this) }
    private var filteredLaunchList: List<LaunchResponse.LaunchResponseItem>? = null
    private var launchList: List<LaunchResponse.LaunchResponseItem>? = null

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLaunchBinding =
        FragmentLaunchBinding.inflate(layoutInflater)

    override fun fetchData() {
        viewModel.getLaunchData()
        observe()
    }

    override fun setupView() {
        setupRecyclerView()
        setupSearchBar()
        bookmark()
    }

    private fun bookmark(){
        binding.ivBookmark.setOnClickListener {
            val action = FragmentLaunchDirections.actionLaunchFragmentToFavouriteLaunchFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        val verticalLayoutManager = LinearLayoutManager(requireContext())
        binding.rvLaunch.apply {
            layoutManager = verticalLayoutManager
            adapter = launchAdapter
        }
    }

    private fun observe() {
        viewModel.launchLiveData.observe(this) { state ->
            when(state){
                is ViewState.Error -> {
                    println("onCreate: ${state.errorMessage}")
                }
                ViewState.Loading -> {
                    showLoadingSpinner()
                }
                is ViewState.Success -> showLaunches(state.data)
            }
        }

        viewModel.bookmarkLiveData.observe(this) { state ->
            when(state){
                is ViewState.Error -> {
                    dismissLoadingSpinner()
                    println("onCreate: ${state.errorMessage}")
                }
                ViewState.Loading -> {
                    showLoadingSpinner()
                }
                is ViewState.Success -> {
                    dismissLoadingSpinner()
                    Toast.makeText(requireContext(), "Bookmarked", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.unBookmarkLiveData.observe(this) { state ->
            when(state){
                is ViewState.Error -> {
                    dismissLoadingSpinner()
                    println("onCreate: ${state.errorMessage}")
                }
                ViewState.Loading -> {
                    showLoadingSpinner()
                }
                is ViewState.Success -> {
                    dismissLoadingSpinner()
                    Toast.makeText(requireContext(), "UnBookmarked", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun showLaunches(launchesList: List<LaunchResponse.LaunchResponseItem>) {
        dismissLoadingSpinner()
        if (launchesList.isEmpty()) {
            binding.rvLaunch.visibility = View.GONE
        } else {
            binding.rvLaunch.visibility = View.VISIBLE
            launchList = launchesList
            launchAdapter.submitList(launchesList)
        }
    }

    private fun dismissLoadingSpinner() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun showLoadingSpinner() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    override fun launchItemClicked(launchItem: LaunchResponse.LaunchResponseItem) {
        // navigate to launch detail screen
        val action = FragmentLaunchDirections.actionLaunchFragmentToLaunchDetailFragment(launchItem)
        findNavController().navigate(action)
    }



    override fun bookmarkItemClicked(launchItem: BookmarkItem) {
        viewModel.bookmarkLaunch(launchItem)
    }

    override fun unBookmarkItemClicked(launchItem: BookmarkItem) {
        viewModel.unBookmarkLaunch(launchItem)
    }

    private fun setupSearchBar(){
        binding.etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                Handler(Looper.getMainLooper()).postDelayed({search(s.toString())}, searchDebounce)
            }
        })
    }

    fun search(searchText: String) {
        filteredLaunchList = if (searchText.isEmpty()) launchList else {
            launchList?.filter {
                it.missionName?.lowercase()?.contains(searchText, true) == true
            }
        }
        launchAdapter.submitList(filteredLaunchList)
    }
}

