package com.technopolitan.mocaspaces.data.home

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.HomeSearchItemBinding
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import javax.inject.Inject


class HomeSearchAdapter @Inject constructor(
    private var context: Context,
    private var activty: Activity,
    private var spannableStringModule: SpannableStringModule
) : RecyclerView.Adapter<HomeSearchAdapter.ViewHolder>() {

    private var itemIndex = 0
    private lateinit var list: List<HomeSearchMapper>
    private lateinit var itemSearchCallback: (entity: String) -> Unit

    fun setList(list: List<HomeSearchMapper>): HomeSearchAdapter {
        this.list = list
        return this
    }

    fun setSearchCallBack(itemSearchCallback: (entity: String) -> Unit) {
        this.itemSearchCallback = itemSearchCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeSearchAdapter.ViewHolder {
        val itemBinding =
            HomeSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    fun getItemPosition(): Int {
        return itemIndex
    }

    override fun onBindViewHolder(holder: HomeSearchAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(private val itemBinding: HomeSearchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), TextWatcher {
        private lateinit var item: HomeSearchMapper

        fun bind(item: HomeSearchMapper) {
            this.item = item
            itemIndex = bindingAdapterPosition
            itemBinding.searchTextInput.setBoxStrokeColorStateList(getColorStateList())
            itemBinding.searchTextInput.startIconDrawable?.let {
                updateDrawable(it)
            }
            itemBinding.searchTextInput.endIconDrawable?.let { updateDrawable(it) }

//            itemBinding.searchItemCard.setCardBackgroundColor(context.getColor(item.color))
            spannableStringModule.newString().addString(context.getString(R.string.findMeA) + " ")
                .init(R.color.white, fontResourceId = R.font.gt_meduim)
                .addString(item.hintSearch)
                .init(item.color, fontResourceId = R.font.gt_meduim)
            itemBinding.searchTextInput.hint = spannableStringModule.getSpannableString()
        }

        private fun updateDrawable(it: Drawable) {
            it.setColorFilter(context.getColor(item.color), PorterDuff.Mode.SRC_IN)
            it.setBounds(100, 100, 100, 100)
            it.setHotspotBounds(100, 100, 100, 100)
        }

        private fun getColorStateList(): ColorStateList {
            return context.resources.getColorStateList(item.textInputLayoutBoxColor, context.theme)
        }


        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }


        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            itemSearchCallback(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {

        }

    }


}