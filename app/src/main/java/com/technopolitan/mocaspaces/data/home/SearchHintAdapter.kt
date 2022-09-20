package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.HomeSearchItemHintBinding
import com.technopolitan.mocaspaces.models.location.mappers.SearchHintMapper
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import javax.inject.Inject

class SearchHintAdapter @Inject constructor(
    private var spannableStringModule: SpannableStringModule,
    private var context: Context
) : RecyclerView.Adapter<SearchHintAdapter.ViewHolder>() {

    private val list: MutableList<SearchHintMapper> = mutableListOf()
    private var filteredList: MutableList<SearchHintMapper> = mutableListOf()
    private lateinit var itemBinding: HomeSearchItemHintBinding
    private lateinit var searchCallBack: (searchHintMapper: SearchHintMapper) -> Unit

    private var typedText: String = ""


    fun callBackOnClick(searchCallBack: (searchHintMapper: SearchHintMapper) -> Unit) {
        this.searchCallBack = searchCallBack
    }


    private fun filterList() {
        val localFilteredList = mutableListOf<SearchHintMapper>()
        this.list.forEach { item ->
            if (item.name.lowercase().contains(typedText.lowercase())) {
                localFilteredList.add(item)
            }
        }
        this.filteredList = localFilteredList
        if (filteredList.isEmpty()) {

        }
        filteredList.add(SearchHintMapper().initEmpty(context.getString(R.string.no_result_found)))
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemBinding =
            HomeSearchItemHintBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    inner class ViewHolder(private val itemBinding: HomeSearchItemHintBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        fun bind(item: SearchHintMapper) {
            itemBinding.searchItem.setOnClickListener(this)
            if (filteredList.isEmpty()) {
                showNoResultText()
            } else {
                itemBinding.searchItem.text = getTypedAndUnTypedText(item)
                hideNoResultText()
            }
        }

        private fun getTypedAndUnTypedText(hintMapper: SearchHintMapper): SpannableString {
            val split = hintMapper.name.lowercase().split(typedText.lowercase())
            return spannableStringModule.newString().addString(typedText)
                .init(R.color.accent_color_60)
                .addString(split[1])
                .init(R.color.accent_color)
                .getSpannableString()

        }

        private fun showNoResultText() {
            itemBinding.homeSearchNoResultTextView.visibility = View.VISIBLE
        }

        private fun hideNoResultText() {
            itemBinding.homeSearchNoResultTextView.visibility = View.GONE
        }


        override fun onClick(v: View?) {
//            if(list[bindingAdapterPosition].id != -1)
//            searchCallBack(list[bindingAdapterPosition])
        }

    }


}