package com.technopolitan.mocaspaces.data.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.databinding.AmenityItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.AmenityMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import javax.inject.Inject

class AmenityAdapter @Inject constructor(private var glideModule: GlideModule) :
    RecyclerView.Adapter<AmenityAdapter.ViewHolder>() {

    private lateinit var list: List<AmenityMapper>

    private fun init(list: List<AmenityMapper>) {
        this.list = list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            AmenityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return if (::list.isInitialized)
            list.size
        else 0
    }

    inner class ViewHolder(private val itemBinding: AmenityItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(amenityMapper: AmenityMapper) {
            amenityMapper.run {
                glideModule.loadImage(image, itemBinding.amenityImageView)
            }

        }

    }

}