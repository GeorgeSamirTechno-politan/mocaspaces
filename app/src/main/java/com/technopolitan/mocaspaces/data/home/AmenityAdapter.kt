package com.technopolitan.mocaspaces.data.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.AmenityItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.AmenityMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import com.technopolitan.mocaspaces.utilities.setMargin

class AmenityAdapter constructor(private var glideModule: GlideModule) :
    BaseRecyclerAdapter<AmenityMapper, AmenityItemBinding>() {


    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemBinding =
            AmenityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    fun defaultMaxItemCount(maxItemCount: Int) {
        defaultMaxItemCount = maxItemCount
    }


    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: AmenityMapper) {
        (holder as ViewHolder).bind(item)
    }

    private inner class ViewHolder(private val itemBinding: AmenityItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: AmenityMapper) {
            val minWidth = getItemMinWidthWithMaxItemCount()
            if (minWidth != null)
                itemBinding.root.minWidth = minWidth
            glideModule.loadImage(item.image, itemBinding.amenityImageView)
            if (item.name.isNullOrEmpty())
                itemBinding.amenityTextView.visibility = View.GONE
            else {
                itemBinding.amenityTextView.visibility = View.VISIBLE
                itemBinding.amenityTextView.text = item.name
                itemBinding.amenityTextView.setMargin(
                    bottom = itemBinding.amenityTextView.context.resources.getDimensionPixelOffset(
                        com.intuit.sdp.R.dimen._20sdp
                    )
                )
            }
        }
    }

}