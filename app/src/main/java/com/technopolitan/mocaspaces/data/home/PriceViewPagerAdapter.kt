package com.technopolitan.mocaspaces.data.home

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.technopolitan.mocaspaces.databinding.PriceItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.PricePagerMapper
import javax.inject.Inject


class PriceViewPagerAdapter @Inject constructor(private var activity: Activity): PagerAdapter() {

    private var priceList: MutableList<PricePagerMapper> = mutableListOf()

    fun init(priceList: MutableList<PricePagerMapper>){
        this.priceList = priceList
        notifyDataSetChanged()
    }
    private lateinit var binding: PriceItemBinding
    override fun getCount(): Int =priceList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = activity.layoutInflater
        binding = PriceItemBinding.inflate(inflater, container, false)
        binding.priceTextView.text = priceList[position].price
        binding.currencyTextView.text = priceList[position].currency
        binding.perTypeTextView.text = priceList[position].per
        binding.typeStartFromTextView.text = priceList[position].startFrom
        (container as ViewPager).addView(binding.root)
        return binding.root
    }


}