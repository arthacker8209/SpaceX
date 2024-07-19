package com.deepak.spacex.ui.launch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deepak.spacex.R
import com.deepak.spacex.data.model.BookmarkItem
import com.deepak.spacex.data.model.LaunchResponse
import com.deepak.spacex.databinding.ItemLaunchBinding

/**
 * Created by Deepak Kumawat on 18/07/24.
 */
class LaunchAdapter(private val listener: LaunchItemClickListener) :
    ListAdapter<LaunchResponse.LaunchResponseItem, LaunchAdapter.ViewHolder>(
        DIFF_UTIL
    ) {


    companion object {
        private val DIFF_UTIL =
            object : DiffUtil.ItemCallback<LaunchResponse.LaunchResponseItem>() {
                override fun areItemsTheSame(
                    oldItem: LaunchResponse.LaunchResponseItem,
                    newItem: LaunchResponse.LaunchResponseItem
                ) = oldItem.flightNumber == newItem.flightNumber

                override fun areContentsTheSame(
                    oldItem: LaunchResponse.LaunchResponseItem,
                    newItem: LaunchResponse.LaunchResponseItem
                ): Boolean {
                    return oldItem.flightNumber == newItem.flightNumber && oldItem.missionName == newItem.missionName
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemLaunchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(launchItem: LaunchResponse.LaunchResponseItem) {
            binding.apply {
                tvMissionName.text = launchItem.missionName
                tvLaunchYear.text = launchItem.launchYear
                tvRocketName.text = launchItem.rocket?.rocketName
                if (launchItem.isFavorite){
                    bookmarkIcon.setImageResource(R.drawable.ic_bookmark_selected)
                }else{
                    bookmarkIcon.setImageResource(R.drawable.ic_bookmark_not_selected)
                }
                bookmarkIcon.setOnClickListener {
                    if (launchItem.isFavorite) {
                        bookmarkIcon.setImageResource(R.drawable.ic_bookmark_not_selected)
                        launchItem.isFavorite = false
                        listener.unBookmarkItemClicked(mapBookmarkItem(launchItem))
                    }else{
                        listener.bookmarkItemClicked(mapBookmarkItem(launchItem))
                        launchItem.isFavorite = true
                        bookmarkIcon.setImageResource(R.drawable.ic_bookmark_selected)
                    }
                }
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
            launchDateLocal = launchItem.launchDateLocal,
        )
    }
}


