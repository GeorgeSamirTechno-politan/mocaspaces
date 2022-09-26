package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.view.View
import com.google.android.material.textview.MaterialTextView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FilterPaxLayoutBinding
import com.technopolitan.mocaspaces.models.location.mappers.LocationPaxMapper
import com.technopolitan.mocaspaces.utilities.Constants
import javax.inject.Inject

class PaxFilterDataModule @Inject constructor(private var context: Context) {

    private lateinit var binding: FilterPaxLayoutBinding
    private var featureId: Int = Constants.meetingTypeId
    private lateinit var callBack: (item: LocationPaxMapper) -> Unit
    private lateinit var list: List<LocationPaxMapper>

    fun init(
        binding: FilterPaxLayoutBinding,
        featureId: Int,
        list: List<LocationPaxMapper>,
        callBack: (item: LocationPaxMapper) -> Unit
    ) {
        this.callBack = callBack
        this.list = list
        this.binding = binding
        this.featureId = featureId
        initView()
    }

    private fun initView() {
        if (featureId == Constants.meetingTypeId) {
            binding.filterLayout.setBackgroundColor(context.getColor(R.color.meeting_space_color))
        } else if (featureId == Constants.eventTypeId)
            binding.filterLayout.setBackgroundColor(context.getColor(R.color.event_space_color))
        initItemFromList()
        setClickOnItem()
    }


    private fun initItemFromList() {
        if (list.isNotEmpty()) {
            when {
                list.size >= 3 -> {
                    setFirstPax()
                    setSecondPax()
                    setThirdPax()
                }
                list.size == 2 -> {
                    setFirstPax()
                    setSecondPax()
                    binding.thirdPaxFilterTextView.visibility = View.INVISIBLE
                    binding.thirdPaxFilterTextView.isEnabled = false
                }
                list.size == 1 -> {
                    setFirstPax()
                    binding.secondPaxFilterTextView.visibility = View.INVISIBLE
                    binding.thirdPaxFilterTextView.visibility = View.INVISIBLE
                    binding.thirdPaxFilterTextView.isEnabled = false
                    binding.secondPaxFilterTextView.isEnabled = false
                }

            }

        }
    }

    private fun setFirstPax() {
        binding.firstPaxFilterTextView.text =
            context.getString(R.string.pax_filter, "${list[0].fromPax} - ${list[0].toPax}")
    }

    private fun setSecondPax() {
        binding.secondPaxFilterTextView.text =
            context.getString(R.string.pax_filter, "${list[1].fromPax} - ${list[1].toPax}")
    }

    private fun setThirdPax() {
        binding.thirdPaxFilterTextView.text =
            context.getString(R.string.pax_filter, "${list[2].fromPax} - ${list[2].toPax}")
    }

    private fun setClickOnItem() {
        setClickOnPax(binding.firstPaxFilterTextView, 0)
        setClickOnPax(binding.secondPaxFilterTextView, 1)
        setClickOnPax(binding.thirdPaxFilterTextView, 2)
    }

    private fun setClickOnPax(textView: MaterialTextView, position: Int) {
        textView.setOnClickListener {
            setUnSelectedForOtherItems(position)
            if (list[position].selected) {
                list[position].selected = list[position].selected.not()
                setUnSelectedText(textView)
                callBack(list[position])
            } else {
                list[position].selected = list[position].selected.not()
                setSelectedText(textView)
                callBack(list[position])
            }
        }
    }

    private fun setSelectedText(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.rounded_accent_solid_shape)
        when (featureId) {
            Constants.meetingTypeId -> {
                textView.setTextColor(context.getColor(R.color.meeting_space_color))
                textView.compoundDrawables[0].setTint(context.getColor(R.color.meeting_space_color))
            }
            Constants.eventTypeId -> {
                textView.setTextColor(context.getColor(R.color.event_space_color))
                textView.compoundDrawables[0].setTint(context.getColor(R.color.event_space_color))
            }
        }
    }

    private fun setUnSelectedForOtherItems(position: Int) {
        when {
            list.size >= 3 -> {
                if (position != 0) {
                    setUnSelectedText(binding.firstPaxFilterTextView)
                    this.list[0].selected = false
                }
                if (position != 1) {
                    setUnSelectedText(binding.secondPaxFilterTextView)
                    this.list[1].selected = false
                }
                if (position != 2) {
                    setUnSelectedText(binding.thirdPaxFilterTextView)
                    this.list[2].selected = false
                }
            }
            list.size == 2 -> {
                if (position != 0) {
                    setUnSelectedText(binding.firstPaxFilterTextView)
                    this.list[0].selected = false
                }
                if (position != 1) {
                    setUnSelectedText(binding.secondPaxFilterTextView)
                    this.list[1].selected = false
                }
            }
        }
    }

    private fun setUnSelectedText(textView: MaterialTextView) {
        textView.setBackgroundResource(R.drawable.rounded_accent_stroke_shape)
        textView.setTextColor(context.getColor(R.color.accent_color))
        textView.compoundDrawables[0].setTint(context.getColor(R.color.accent_color))
    }
}