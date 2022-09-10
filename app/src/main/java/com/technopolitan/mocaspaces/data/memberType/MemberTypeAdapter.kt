package com.technopolitan.mocaspaces.data.memberType

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.databinding.MemberTypeItemBinding
import com.technopolitan.mocaspaces.modules.GlideModule
import javax.inject.Inject

class MemberTypeAdapter @Inject constructor(
    private var glideModule: GlideModule,
    private val context: Context
) : RecyclerView.Adapter<MemberTypeAdapter.ViewHolder>() {

    private var itemIndex = 0
    private lateinit var list: List<DropDownMapper>
    private lateinit var itemClickCallBack: (entity: DropDownMapper) -> Unit

    fun setList(list: List<DropDownMapper>): MemberTypeAdapter {
        this.list = list
        notifyItemRangeInserted(0, list.size)
        return this
    }

    fun setClickCallBack(itemClickCallBack: (entity: DropDownMapper) -> Unit) {
        this.itemClickCallBack = itemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            MemberTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val itemBinding: MemberTypeItemBinding) :
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