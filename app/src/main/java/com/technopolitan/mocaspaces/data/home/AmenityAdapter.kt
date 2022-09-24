package com.technopolitan.mocaspaces.data.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.AmenityItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.AmenityMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import javax.inject.Inject

class AmenityAdapter constructor(private var glideModule: GlideModule) :
    BaseRecyclerAdapter<AmenityMapper, AmenityItemBinding>() {


    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            AmenityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: AmenityMapper) {
        (holder as ViewHolder).bind(item)
    }

    private inner class ViewHolder(private val itemBinding: AmenityItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(amenityMapper: AmenityMapper) {
            amenityMapper.run {
                glideModule.loadImage(image, itemBinding.amenityImageView)
            }
        }
    }

}