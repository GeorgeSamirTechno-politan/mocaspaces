package com.technopolitan.mocaspaces.data.home

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListPopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.HomeSearchItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.SearchHintMapper
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import javax.inject.Inject


class HomeSearchAdapter @Inject constructor(
    private var context: Context,
    private var activty: Activity,
    private var spannableStringModule: SpannableStringModule,
    private var searchHintAdapter: SearchHintListAdapter
) : RecyclerView.Adapter<HomeSearchAdapter.ViewHolder>() {

    private var itemIndex = 0
    private lateinit var list: List<HomeSearchMapper>
    private lateinit var searchHintMapperList: List<SearchHintMapper>
    private lateinit var searchCallBack: (searchHintMapper: SearchHintMapper) -> Unit

    fun setList(list: List<HomeSearchMapper>): HomeSearchAdapter {
        this.list = list
        return this
    }

    fun setSearchHintList(it: List<SearchHintMapper>) {
        searchHintAdapter.setList(it)
        this.searchHintMapperList = it
    }

    fun setSearchCallBack(searchCallBack: (searchHintMapper: SearchHintMapper) -> Unit) {
        this.searchCallBack = searchCallBack
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
        private lateinit var listPopUpWindow: ListPopupWindow

        fun bind(item: HomeSearchMapper) {
            this.item = item
            itemIndex = bindingAdapterPosition
            itemBinding.searchTextInput.setBoxStrokeColorStateList(getColorStateList())
            itemBinding.searchTextInput.startIconDrawable?.let {
                updateDrawable(it)
            }
            itemBinding.searchTextInput.endIconDrawable?.let { updateDrawable(it) }
            itemBinding.searchTextInput.hint = getHintText(item)
            itemBinding.searchItemAutoCompleteText.addTextChangedListener(this)
            itemBinding.searchLayout.setBackgroundColor(context.getColor(item.color))
            itemBinding.searchLayout.visibility = View.GONE
            initPopUpMenu()
        }


        private fun getHintText(item: HomeSearchMapper) =
            spannableStringModule.newString().addString(context.getString(R.string.findMeA) + " ")
                .init(R.color.white, fontResourceId = R.font.gt_meduim)
                .addString(item.hintSearch)
                .init(item.color, fontResourceId = R.font.gt_meduim)
                .getSpannableString()

        private fun updateDrawable(it: Drawable) {
            it.setColorFilter(context.getColor(item.color), PorterDuff.Mode.SRC_IN)
//            it.setBounds(100, 100, 100, 100)
//            it.setHotspotBounds(100, 100, 100, 100)
        }

        private fun getColorStateList(): ColorStateList {
            return context.resources.getColorStateList(item.textInputLayoutBoxColor, context.theme)
        }

        private fun initPopUpMenu() {
            itemBinding.searchItemAutoCompleteText.run {
//                dropDownVerticalOffset = context.resources.getDimension(
//                    com.intuit.sdp.R.dimen._minus20sdp).toInt()
                setDropDownBackgroundResource(item.searchBackgroundDrawable)
                setAdapter(searchHintAdapter)
                this.onItemClickListener = itemClick
            }
        }

        private val itemClick: AdapterView.OnItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val item = searchHintAdapter.getItem(position)
                if (item.name == context.getString(R.string.no_result_found))
                    itemBinding.searchItemAutoCompleteText.setText("")
                else
                    itemBinding.searchItemAutoCompleteText.setText(item.name)
                itemBinding.searchLayout.visibility = View.GONE
                searchCallBack(item)
            }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }


        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            searchHintAdapter.updateTypedText(s.toString())
            if (itemBinding.searchItemAutoCompleteText.enoughToFilter())
                itemBinding.searchLayout.visibility = View.VISIBLE
            else itemBinding.searchLayout.visibility = View.GONE

        }

        override fun afterTextChanged(s: Editable?) {

        }

    }


}