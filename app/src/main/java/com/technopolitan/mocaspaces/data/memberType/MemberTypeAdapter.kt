package com.technopolitan.mocaspaces.data.memberType

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.databinding.MemberTypeItemBinding
import com.technopolitan.mocaspaces.modules.GlideModule
import javax.inject.Inject

class MemberTypeAdapter @Inject constructor(
    private var glideModule: GlideModule,
    private val context: Context
) : BaseRecyclerAdapter<DropDownMapper, MemberTypeItemBinding>() {

    private var itemIndex = 0
    private lateinit var itemClickCallBack: (entity: DropDownMapper) -> Unit

    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            MemberTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(itemBinding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: DropDownMapper) {
        (holder as MemberViewHolder).bind(item)
    }

    fun setClickCallBack(itemClickCallBack: (entity: DropDownMapper) -> Unit) {
        this.itemClickCallBack = itemClickCallBack
    }


    private inner class MemberViewHolder(private val itemBinding: MemberTypeItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {
        private lateinit var item: DropDownMapper
        fun bind(item: DropDownMapper) {
            this.item = item
            glideModule.loadImage(item.image, itemBinding.memberTypeImageView)
            itemBinding.memberTypeTextView.text = item.name
            if (itemIndex == bindingAdapterPosition) {
                item.selected = true
                itemBinding.memberTypeImageView.setBackgroundColor(context.getColor(R.color.accent_color))
                itemBinding.memberTypeTextView.setTextColor(context.getColor(R.color.accent_color))
            } else {
                item.selected = false
                itemBinding.memberTypeImageView.setBackgroundColor(context.getColor(R.color.blue_grey_color))
                itemBinding.memberTypeTextView.setTextColor(context.getColor(R.color.blue_grey_color))
            }
            itemBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemIndex = bindingAdapterPosition
            itemClickCallBack(item)
            notifyDataSetChanged()
        }

    }


}