package com.technopolitan.mocaspaces.data.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.databinding.PriceItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.PricePagerMapper
import com.technopolitan.mocaspaces.modules.RecyclerDiffUtilModule
import javax.inject.Inject


class PriceAdapter @Inject constructor() :
    RecyclerView.Adapter<PriceAdapter.ViewHolder>() {

    private lateinit var binding: PriceItemBinding

    private var priceList: MutableList<PricePagerMapper> = mutableListOf()


    fun init(priceList: MutableList<PricePagerMapper>) {
        val diffCallback = RecyclerDiffUtilModule(this.priceList, priceList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        if (this.priceList.isNotEmpty())
            this.priceList.clear()
        this.priceList.addAll(priceList)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceAdapter.ViewHolder {
        binding = PriceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceAdapter.ViewHolder, position: Int) {
        holder.bind(priceList[position])
    }

    override fun getItemCount(): Int = priceList.size


    inner class ViewHolder(private val itemBinding: PriceItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: PricePagerMapper) {
            itemBinding.priceTextView.text = item.price
            itemBinding.currencyTextView.text = item.currency
            itemBinding.perTypeTextView.text = item.per
            itemBinding.typeStartFromTextView.text = item.startFrom
        }


    }

}