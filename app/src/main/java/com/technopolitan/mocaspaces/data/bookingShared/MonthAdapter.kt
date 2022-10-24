package com.technopolitan.mocaspaces.data.bookingShared

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.MonthItemBinding
import com.technopolitan.mocaspaces.models.booking.MonthMapper
import com.technopolitan.mocaspaces.modules.SpannableStringModule

class MonthAdapter constructor(
    private val itemClickCallBack: (MonthMapper: MonthMapper) -> Unit,
    private val context: Context,
    private val spannableStringModule: SpannableStringModule
) : BaseRecyclerAdapter<MonthMapper, MonthItemBinding>() {


    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemBinding = MonthItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        itemIndex = 0
        return MonthViewHolder(itemBinding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: MonthMapper) {
        (holder as MonthViewHolder).bind(item)
    }

    inner class MonthViewHolder(private val itemBinding: MonthItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        init {
            itemBinding.monthTextView.setOnClickListener(this)
        }

        fun bind(item: MonthMapper) {
            spannableStringModule.newString()
            spannableStringModule.addString(item.monthName)
            if (itemIndex == bindingAdapterPosition) {
                item.selected = true
                spannableStringModule.init(R.color.accent_color, fontResourceId = R.font.gt_meduim)
            } else {
                item.selected = false
                spannableStringModule.init(
                    R.color.light_black_color_30,
                    fontResourceId = R.font.gt_regular
                )
            }
            itemBinding.monthTextView.text = spannableStringModule.getSpannableString()
        }

        override fun onClick(v: View?) {
            itemIndex = bindingAdapterPosition
            itemClickCallBack(getItem(bindingAdapterPosition))
            notifyDataSetChanged()
        }
    }

}