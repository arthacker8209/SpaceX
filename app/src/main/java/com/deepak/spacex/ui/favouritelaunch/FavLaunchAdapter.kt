package com.deepak.spacex.ui.favouritelaunch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deepak.spacex.R
import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse
import com.deepak.spacex.databinding.FavItemLaunchBinding
import com.deepak.spacex.databinding.ItemLaunchBinding
import com.deepak.spacex.viewmodel.FavLaunchViewModel

/**
 * Created by Deepak Kumawat on 18/07/24.
 */
class FavLaunchAdapter(private val listener: FavouriteLaunchClickListener) :
    ListAdapter<BookmarkItem, FavLaunchAdapter.ViewHolder>(
        DIFF_UTIL
    ) {


    companion object {
        private val DIFF_UTIL =
            object : DiffUtil.ItemCallback<BookmarkItem>() {
                override fun areItemsTheSame(
                    oldItem: BookmarkItem,
                    newItem: BookmarkItem
                ) = oldItem.flightNumber == newItem.flightNumber

                override fun areContentsTheSame(
                    oldItem: BookmarkItem,
                    newItem: BookmarkItem
                ): Boolean {
                    return oldItem.flightNumber == newItem.flightNumber && oldItem.missionName == newItem.missionName
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FavItemLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: FavItemLaunchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(launchItem: BookmarkItem) {
            binding.apply {
                tvMissionName.text = launchItem.missionName
                tvLaunchYear.text = launchItem.launchYear
                tvRocketName.text = launchItem.rocket?.rocketName
                root.setOnClickListener { listener.launchItemClicked(launchItem) }
            }
        }
    }

    private fun mapBookmarkItem(launchItem: LaunchResponse.LaunchResponseItem): BookmarkItem{
        return BookmarkItem(
            flightNumber = launchItem.flightNumber,
            missionName = launchItem.missionName,
            launchYear = launchItem.launchYear,
            rocket = launchItem.rocket,
            isFavourite = launchItem.isFavorite,
            links = launchItem.links,
            details = launchItem.details,
            launchSite = launchItem.launchSite,
            launchDateUtc = launchItem.launchDateUtc,
            launchDateUnix = launchItem.launchDateUnix,
            launchDateLocal = launchItem.launchDateLocal
        )
    }
}


