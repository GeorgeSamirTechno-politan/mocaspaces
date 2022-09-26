package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.BizLoungeItemBinding
import com.technopolitan.mocaspaces.models.location.bizLounge.BizLoungeMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import javax.inject.Inject

class BizLoungeAdapter @Inject constructor(
    private var glideModule: GlideModule,
    private var context: Context,
    private var spannableStringModule: SpannableStringModule
) :
    BaseRecyclerAdapter<BizLoungeMapper, BizLoungeItemBinding>() {

    private lateinit var callBack: (item: BizLoungeMapper) -> Unit

    fun setClickOnBook(callBack: (item: BizLoungeMapper) -> Unit) {
        this.callBack = callBack
    }

    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemBinding =
            BizLoungeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BizLoungeItemViewHolder(itemBinding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: BizLoungeMapper) {
        (holder as BizLoungeItemViewHolder).bind(item)
    }

    inner class BizLoungeItemViewHolder(private val itemBind: BizLoungeItemBinding) :
        RecyclerView.ViewHolder(itemBind.root), OnClickListener {

        init {
            itemBind.bizLoungeCheckPriceButton.setOnClickListener(this)
        }

        fun bind(item: BizLoungeMapper) {
            glideModule.loadImage(item.image, itemBinding.bizLoungeRoomImageView)
            setOpenClose(item)
            itemBinding.workingHourTextView.text =
                item.getOpenHourText(context, spannableStringModule)
            itemBind.bizLoungeLocationNameTextView.text = item.locationName
            itemBind.bizLoungeLocationAddressTextView.text = item.address
        }

        private fun setOpenClose(item: BizLoungeMapper) {
            if (item.workTimeMapper.isOpen()) {
                setOpen()
            } else {
                setClose()
            }
        }

        private fun setOpen() {
            itemBinding.bizLoungeStatusTextView.background =
                AppCompatResources.getDrawable(context, R.drawable.green_shape)
            itemBinding.bizLoungeStatusTextView.text = context.getText(R.string.open)
        }

        private fun setClose() {
            itemBinding.bizLoungeStatusTextView.background =
                AppCompatResources.getDrawable(context, R.drawable.red_shape)
            itemBinding.bizLoungeStatusTextView.text = context.getText(R.string.close)
        }

        override fun onClick(v: View?) {
            callBack(getItem(bindingAdapterPosition))
        }
    }

}