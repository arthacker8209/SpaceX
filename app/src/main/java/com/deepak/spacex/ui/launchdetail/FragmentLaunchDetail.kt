package com.deepak.spacex.ui.launchdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.deepak.spacex.R
import com.deepak.spacex.common.base.BaseFragment
import com.deepak.spacex.common.utils.HorizontalMarginItemDecoration
import com.deepak.spacex.data.model.LaunchResponse
import com.deepak.spacex.databinding.FragmentLaunchDetailBinding
import com.deepak.spacex.databinding.ItemLinkBinding
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class FragmentLaunchDetail : BaseFragment<FragmentLaunchDetailBinding>() {

    private val args: FragmentLaunchDetailArgs by navArgs()
    private lateinit var inflater: LayoutInflater
    private var viewPager: ViewPager2? = null
    private var launchImagesAdapter: LaunchImagesAdapter? = null

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLaunchDetailBinding{
        inflater = layoutInflater
        return FragmentLaunchDetailBinding.inflate(layoutInflater)
    }

    override fun fetchData() {}


    override fun setupView() {
        val launchDetail = args.launchDetail as LaunchResponse.LaunchResponseItem
        setupViewPager(launchDetail)
    }


    private fun setupViewPager(launchDetail: LaunchResponse.LaunchResponseItem){
        if (!launchDetail.links?.flickrImages.isNullOrEmpty()){
            launchImagesAdapter = LaunchImagesAdapter(launchDetail.links?.flickrImages)
            viewPager = binding.viewPager
            viewPager?.adapter = launchImagesAdapter
            viewPager?.offscreenPageLimit = 1
            viewPager?.clipToPadding = false
            viewPager?.clipChildren = false
            binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager?.offscreenPageLimit = 1

            val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
            val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
            val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = -pageTranslationX * position
                page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
                page.alpha = 0.25f + (1 - kotlin.math.abs(position))
            }
            viewPager?.setPageTransformer(pageTransformer)
            val itemDecoration = HorizontalMarginItemDecoration(
                requireContext(),
                R.dimen.viewpager_current_item_horizontal_margin
            )
            viewPager?.addItemDecoration(itemDecoration)
        }else{
            viewPager?.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setLaunchDetail(launchDetail: LaunchResponse.LaunchResponseItem) {
        binding.apply {
            tvMissionName.text = launchDetail.missionName
            if (launchDetail.links?.flickrImages.isNullOrEmpty())viewPager.visibility = View.GONE
            val date = launchDetail.launchDateUtc?.let { getDateFromUtc(it) }
            tvLaunchDate.text = date
            tvLaunchSite.text = "Launch Site: " + launchDetail.launchSite?.siteName
            tvDetails.text = "Details: " + launchDetail.details
            tvRocketName.text = "Rocket Name: " + launchDetail.rocket?.rocketName
            tvRocketType.text = "Rocket Type: " + launchDetail.rocket?.rocketType
            launchDetail.links?.let {links -> addLinksToContainer(links, llLinks) }
        }

    }


    private fun getDateFromUtc(utcTime: String): String {
        val zonedDateTime = ZonedDateTime.parse(utcTime, DateTimeFormatter.ISO_DATE_TIME)
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return zonedDateTime.format(dateFormatter)
    }



    private fun addLinksToContainer(links: LaunchResponse.Links, container: LinearLayout) {
        links.run {
            listOf(
                missionPatch,
                missionPatchSmall,
                redditCampaign,
                redditLaunch,
                redditRecovery,
                redditMedia,
                presskit,
                articleLink,
                wikipedia,
                videoLink,
                youtubeId
            ).forEach { link ->
                link?.let {
                    val linkView = ItemLinkBinding.inflate(inflater)
                    linkView.root.text = link
                    linkView.root.setOnClickListener {
                        openLink(link)
                    }
                    container.addView(linkView.root)
                }
            }
        }
    }

    private fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(link)
        startActivity(intent)
    }

}