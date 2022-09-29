package com.technopolitan.mocaspaces.data.locationDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.MarketingItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.MarketingMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import javax.inject.Inject

class MarketingAdapter @Inject constructor(private var glideModule: GlideModule) :
    BaseRecyclerAdapter<MarketingMapper, MarketingItemBinding>() {

    private lateinit var callBack: (item: MarketingMapper) -> Unit

    fun setCallBack(callBack: (item: MarketingMapper) -> Unit) {
        this.callBack = callBack
    }

    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemBinding =
            MarketingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarketingViewHolder(itemBinding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: MarketingMapper) {
        (holder as MarketingViewHolder).bind(item)
    }

    inner class MarketingViewHolder(private val itemBinding: MarketingItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        init {
            itemBinding.marketingBtn.setOnClickListener(this)
        }

        fun bind(item: MarketingMapper) {
            if (item.showButton)
                itemBinding.marketingBtn.visibility = View.VISIBLE
            else
                itemBinding.marketingBtn.visibility = View.GONE
            glideModule.loadImage(item.path, itemBinding.marketingImageView)
            itemBinding.marketingTextView.text = item.name
        }

        override fun onClick(v: View?) {
            if (v?.id == itemBinding.marketingBtn.id) {
                callBack(getItem(bindingAdapterPosition))
            }
        }
    }
}