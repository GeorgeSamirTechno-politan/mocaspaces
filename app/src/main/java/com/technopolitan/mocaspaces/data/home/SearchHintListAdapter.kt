package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.HomeSearchItemHintBinding
import com.technopolitan.mocaspaces.models.location.mappers.SearchHintMapper
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import javax.inject.Inject


class SearchHintListAdapter @Inject constructor(
    private var context: Context,
    private var spannableStringModule: SpannableStringModule
) : BaseAdapter(), Filterable {

    private val list: MutableList<SearchHintMapper> = mutableListOf()
    private var filteredList: MutableList<SearchHintMapper> = mutableListOf()
    private lateinit var itemBinding: HomeSearchItemHintBinding
    private var typedText: String = ""
    private val mFilter = ItemFilter()


    fun setList(list: List<SearchHintMapper>) {
        filteredList.addAll(list)
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    override fun getCount(): Int = filteredList.size

    override fun getItem(position: Int): SearchHintMapper = filteredList[position]

    override fun getItemId(position: Int): Long = filteredList[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = filteredList[position]

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        itemBinding = HomeSearchItemHintBinding.inflate(inflater, parent, false)
        if (filteredList[position].id == -1 && count == 1) {
            itemBinding.homeSearchNoResultTextView.visibility = View.VISIBLE
        } else {
            itemBinding.homeSearchNoResultTextView.visibility = View.GONE
            itemBinding.searchItem.text = getTypedAndUnTypedText(item)
        }
        return itemBinding.root
    }


    private fun getTypedAndUnTypedText(hintMapper: SearchHintMapper): SpannableString {
        val split = hintMapper.name.lowercase().split(typedText.lowercase())
        return spannableStringModule.newString().addString(typedText)
            .init(R.color.accent_color_60)
            .addString(split[1])
            .init(R.color.accent_color)
            .getSpannableString()

    }

    fun updateTypedText(typedText: String) {
        this.typedText = typedText
//        filterList()
    }

    override fun getFilter(): Filter = mFilter

    inner class ItemFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterString = constraint.toString().lowercase()
            val results = FilterResults()
            val filteredList = mutableListOf<SearchHintMapper>()
            list.forEach { item ->
                if (item.name.lowercase().contains(filterString)) {
                    filteredList.add(item)
                }
            }
            if (filteredList.isEmpty())
                filteredList.add(SearchHintMapper().initEmpty(context.getString(R.string.no_result_found)))
            results.values = filteredList
            results.count = filteredList.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredList = results!!.values as MutableList<SearchHintMapper>
            notifyDataSetChanged()
        }

    }
}