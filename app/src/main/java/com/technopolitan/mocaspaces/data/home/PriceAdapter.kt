package com.technopolitan.mocaspaces.data.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.PriceItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.PricePagerMapper
import javax.inject.Inject


class PriceAdapter @Inject constructor() :
    BaseRecyclerAdapter<PricePagerMapper, PriceItemBinding>() {


    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = PriceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceViewHolder(binding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: PricePagerMapper) {
        (holder as PriceViewHolder).bind(item)
    }

    private inner class PriceViewHolder(private val itemBinding: PriceItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: PricePagerMapper) {
            itemBinding.priceTextView.text = item.price
            itemBinding.currencyTextView.text = item.currency
            itemBinding.perTypeTextView.text = item.per
            itemBinding.typeStartFromTextView.text = item.startFrom
        }


    }


}