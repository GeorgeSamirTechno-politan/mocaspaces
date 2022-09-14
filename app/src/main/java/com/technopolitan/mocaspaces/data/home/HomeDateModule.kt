package com.technopolitan.mocaspaces.data.home

import android.content.Context
import com.technopolitan.mocaspaces.databinding.HomeFragmentBinding
import dagger.Module
import javax.inject.Inject

@Module
class HomeDateModule @Inject constructor(
    private var context: Context,
    private var homeViewPagerAdapter: HomeViewPagerAdapter
) {

    private lateinit var binding: HomeFragmentBinding
    fun init(binding: HomeFragmentBinding) {
        this.binding = binding
    }

    private fun initViewPager() {
        binding.homeFragmentViewPager.adapter = homeViewPagerAdapter
    }
}