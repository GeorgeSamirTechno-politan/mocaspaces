package com.technopolitan.mocaspaces.data.bookingShared

import android.R
import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.technopolitan.mocaspaces.databinding.SingleMultiPickingLayoutBinding
import javax.inject.Inject


class SingleMultiplyDataModule @Inject constructor(private var context: Context) {

    private lateinit var binding: SingleMultiPickingLayoutBinding
    private lateinit var callBack: (isSingle: Boolean) -> Unit
    private var inAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
    private var outAnimation: Animation =
        AnimationUtils.loadAnimation(context, R.anim.slide_out_right)

    fun init(binding: SingleMultiPickingLayoutBinding, callBack: (isSingle: Boolean) -> Unit) {
        this.binding = binding
        this.callBack = callBack
        initView()
    }

    private fun initView() {
        binding.viewSwitcher.inAnimation = inAnimation
        binding.viewSwitcher.outAnimation = outAnimation
        binding.singleDayLayout.setOnClickListener {
            binding.viewSwitcher.showNext()
            callBack(false)
        }
        binding.multiplyDayLayout.setOnClickListener {
            binding.viewSwitcher.showPrevious()

            callBack(true)
        }

    }


}