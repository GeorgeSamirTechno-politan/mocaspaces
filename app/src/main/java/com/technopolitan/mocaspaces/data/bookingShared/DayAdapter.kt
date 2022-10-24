package com.technopolitan.mocaspaces.data.bookingShared

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.DayItemBinding
import com.technopolitan.mocaspaces.models.booking.DayMapper

class DayAdapter(
    private val itemCallBack: (dayMapper: DayMapper, position: Int) -> Unit,
    private val context: Context
) : BaseRecyclerAdapter<DayMapper, DayItemBinding>() {


    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemBinding = DayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(itemBinding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: DayMapper) {
        (holder as DayViewHolder).bind(item)
    }

    private fun updateItem(dayMapper: DayMapper, position: Int) {
        list[position]!!.selected = true
        notifyItemChanged(position)
    }

    inner class DayViewHolder(private val itemBinding: DayItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        init {
            itemBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val item = getItem(bindingAdapterPosition)
            if (item.enable) {
                itemIndex = bindingAdapterPosition
                itemCallBack(item, bindingAdapterPosition)
                notifyDataSetChanged()
            }
        }

        fun bind(item: DayMapper) {
            itemBinding.dayNameTextView.text = item.dayName
            itemBinding.dayNumberTextView.text = item.dayNumber
            enableAndDisable(item)
//            setSelectedItem(item)
//            setSelectedItemFromBooking(item)
        }

        private fun enableAndDisable(item: DayMapper) {
            if (item.enable) {
                setEnableAndNotSelectedItem()
            } else {
                setDisabledItem()
            }
        }


        private fun setSelectedItem(item: DayMapper) {
            if (bindingAdapterPosition == itemIndex) {
                item.currentSelected = true
                setSelectedItem()
            } else {
                enableAndDisable(item)
            }
        }

        private fun setSelectedItemFromBooking(item: DayMapper) {
            if (item.selected) {
                setSelectedItem()
            } else
                setEnableAndNotSelectedItem()
        }

        private fun setDisabledItem() {
            itemBinding.root.isEnabled = false
            itemBinding.root.cardElevation =
                context.resources.getDimension(com.intuit.sdp.R.dimen._1sdp)
            itemBinding.root.setCardBackgroundColor(context.getColor(R.color.background_color))
            itemBinding.dayNameTextView.setTextColor(context.getColor(R.color.light_black_color_30))
            itemBinding.dayNumberTextView.setTextColor(context.getColor(R.color.light_black_color_30))
        }

        private fun setEnableAndNotSelectedItem() {
            itemBinding.root.isEnabled = true
            itemBinding.root.cardElevation =
                context.resources.getDimension(com.intuit.sdp.R.dimen._2sdp)
            itemBinding.root.setCardBackgroundColor(context.getColor(R.color.medium_grey_color))
            itemBinding.dayNameTextView.setTextColor(context.getColor(R.color.accent_color))
            itemBinding.dayNumberTextView.setTextColor(context.getColor(R.color.accent_color))
        }

        private fun setSelectedItem() {
            itemBinding.root.cardElevation =
                context.resources.getDimension(com.intuit.sdp.R.dimen._2sdp)
            itemBinding.root.setCardBackgroundColor(context.getColor(R.color.accent_color))
            itemBinding.dayNameTextView.setTextColor(context.getColor(R.color.white))
            itemBinding.dayNumberTextView.setTextColor(context.getColor(R.color.white))
        }
    }
}