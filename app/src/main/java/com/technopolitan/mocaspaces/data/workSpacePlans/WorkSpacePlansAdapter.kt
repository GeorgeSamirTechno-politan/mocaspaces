package com.technopolitan.mocaspaces.data.workSpacePlans

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.WorkSpaceBookingItemBinding
import com.technopolitan.mocaspaces.models.workSpace.WorkSpacePlanMapper
import com.technopolitan.mocaspaces.utilities.BookingType
import com.technopolitan.mocaspaces.utilities.loadHtml

class WorkSpacePlansAdapter(
    private var context: Context,
    private val callBack: (item: WorkSpacePlanMapper) -> Unit
) : BaseRecyclerAdapter<WorkSpacePlanMapper, WorkSpaceBookingItemBinding>() {

    init {
        infiniteScrolling = true
    }

    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemBinding =
            WorkSpaceBookingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkSpacePlanViewHolder(itemBinding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: WorkSpacePlanMapper) {
        (holder as WorkSpacePlanViewHolder).bind(item)
    }

    inner class WorkSpacePlanViewHolder(private val binding: WorkSpaceBookingItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.bottomLottieView.visibility = View.GONE
            binding.endLottieView.visibility = View.GONE
            binding.selectPlanBtn.setOnClickListener(this)
        }

        fun bind(item: WorkSpacePlanMapper) {
            binding.cardItem.setCardBackgroundColor(context.getColor(item.backGroundColorResId))
            binding.mocaMImageView.setColorFilter(context.getColor(item.mocaMColorResId))
            if (item.planId == BookingType.dayPassTypeId) {
                setBottomIcon(item)
            } else {
                setEndIcon(item)
            }
            binding.descTextView.text = item.planDescText
            binding.descTextView.setTextColor(context.getColor(item.planDescColorResId))
            binding.startFromTextView.setTextColor(context.getColor(item.priceTextColorResId))
            binding.currencyTextView.setTextColor(context.getColor(item.priceTextColorResId))
            binding.currencyTextView.text = item.currency
            binding.priceTextView.setTextColor(context.getColor(item.priceTextColorResId))
            binding.priceTextView.text = item.priceText
            binding.perTypeTextView.setTextColor(context.getColor(item.priceTextColorResId))
            binding.perTypeTextView.text = item.pricePerText
            binding.selectPlanBtn.setTextColor(context.getColor(item.planBtnTextColorResId))
            binding.selectPlanBtn.setBackgroundColor(context.getColor(item.planBtnColorResId))
            binding.termsOfUseTextView.loadHtml(item.planTermsOfUseHtml)
            binding.bookingTypeTextView.text = item.planTypeText
        }

        private fun setEndIcon(item: WorkSpacePlanMapper) {
            binding.bottomLottieView.visibility = View.GONE
            binding.endLottieView.visibility = View.VISIBLE
            binding.endLottieView.setAnimation(item.animatedRawResId)
        }

        private fun setBottomIcon(item: WorkSpacePlanMapper) {
            binding.bottomLottieView.visibility = View.VISIBLE
            binding.endLottieView.visibility = View.GONE
            binding.bottomLottieView.setAnimation(item.animatedRawResId)
        }

        override fun onClick(v: View?) {
            callBack(getItem(bindingAdapterPosition))
        }
    }
}