package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.HomeSearchItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.HomeSearchMapper
import com.technopolitan.mocaspaces.models.location.mappers.SearchHintMapper
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import javax.inject.Inject


class HomeSearchAdapter @Inject constructor(
    private var context: Context,
    private var spannableStringModule: SpannableStringModule,
    private var searchHintAdapter: SearchHintListAdapter
) : BaseRecyclerAdapter<HomeSearchMapper, HomeSearchItemBinding>() {

    private lateinit var searchCallBack: (searchHintMapper: SearchHintMapper) -> Unit
    private var searchHintList: MutableList<SearchHintMapper> = mutableListOf()

    fun setSearchCallBack(searchCallBack: (searchHintMapper: SearchHintMapper) -> Unit) {
        this.searchCallBack = searchCallBack
    }

    fun setSearchHintList(searchHintList: MutableList<SearchHintMapper>, viewType: Int) {
        this.searchHintList = searchHintList
        searchHintAdapter.setList(this.searchHintList)
        searchHintAdapter.setViewType(viewType)

    }

    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            HomeSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: HomeSearchMapper) {
        (holder as ViewHolder).bind(item)
    }

    private inner class ViewHolder(private val itemBinding: HomeSearchItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), TextWatcher, View.OnClickListener {
        private lateinit var item: HomeSearchMapper

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
            itemBinding.searchTextInput.setEndIconOnClickListener(this)
            itemBinding.searchItemAutoCompleteText.setOnEditorActionListener(editActionListener)
            initPopUpMenu()
        }

        private val editActionListener: TextView.OnEditorActionListener =
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doFilter()
                    return@OnEditorActionListener true
                } else {
                    return@OnEditorActionListener false
                }
            }

        private fun doFilter() {
            if (item.searchHintMapper.id == null && item.searchHintMapper.type == null ||
                itemBinding.searchItemAutoCompleteText.text.isNullOrEmpty()
            ) {
                item.searchHintMapper = SearchHintMapper()
            }
            searchCallBack(item.searchHintMapper)
        }


        private fun getHintText(item: HomeSearchMapper) =
            spannableStringModule.newString().addString(context.getString(R.string.findMeA) + " ")
                .init(R.color.white, fontResourceId = R.font.gt_meduim)
                .addString(item.hintSearch)
                .init(item.color, fontResourceId = R.font.gt_meduim)
                .getSpannableString()

        private fun updateDrawable(it: Drawable) {
            it.setColorFilter(context.getColor(item.color), PorterDuff.Mode.SRC_IN)
        }

        private fun getColorStateList(): ColorStateList {
            return context.resources.getColorStateList(item.textInputLayoutBoxColor, context.theme)
        }

        private fun initPopUpMenu() {
            itemBinding.searchItemAutoCompleteText.run {
                setDropDownBackgroundResource(item.searchBackgroundDrawable)
                setAdapter(searchHintAdapter)
                this.onItemClickListener = itemClick
            }
        }

        private val itemClick: AdapterView.OnItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val item = searchHintAdapter.getItem(position)
                if (item.name == context.getString(R.string.no_result_found)) {
                    itemBinding.searchItemAutoCompleteText.setText("")
                    this.item.searchHintMapper = SearchHintMapper()
                } else {
                    itemBinding.searchItemAutoCompleteText.setText(item.name)
                    this.item.searchHintMapper = item
                }
                itemBinding.searchLayout.visibility = View.GONE
                searchCallBack(this.item.searchHintMapper)
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

        override fun onClick(v: View?) {
            doFilter()
        }

    }


}