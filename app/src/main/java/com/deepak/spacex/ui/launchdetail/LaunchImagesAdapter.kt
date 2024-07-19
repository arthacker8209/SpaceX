package com.deepak.spacex.ui.launchdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deepak.spacex.databinding.ImageCardItemBinding


class LaunchImagesAdapter(private val imageCardList: List<String>?): RecyclerView.Adapter<LaunchImagesAdapter.ImageCardViewHolder>() {

    private lateinit var context: Context

    inner class ImageCardViewHolder(private val launchView : ImageCardItemBinding) : RecyclerView.ViewHolder(launchView.root) {
        fun bind(url: String) {
            Glide.with(context).load(url).into(launchView.launchImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageCardViewHolder {
        context = parent.context
        return ImageCardViewHolder(ImageCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return imageCardList?.size!!
    }

    override fun onBindViewHolder(holder: ImageCardViewHolder, position: Int) {
        holder.bind(imageCardList?.get(position) ?: "")
    }
}