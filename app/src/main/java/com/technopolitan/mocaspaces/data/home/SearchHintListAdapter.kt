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
        clearAll()
        filteredList.addAll(list)
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearAll() {
        if (this.list.isNotEmpty())
            this.list.clear()
        if (this.filteredList.isEmpty())
            this.filteredList.clear()
    }


    override fun getCount(): Int = filteredList.size

    override fun getItem(position: Int): SearchHintMapper = filteredList[position]

    override fun getItemId(position: Int): Long {
        return filteredList[position].id?.toLong() ?: 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = filteredList[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        itemBinding = HomeSearchItemHintBinding.inflate(inflater, parent, false)
        if (filteredList[position].id == null && count == 1) {
            itemBinding.homeSearchNoResultTextView.visibility = View.VISIBLE
        } else {
            itemBinding.homeSearchNoResultTextView.visibility = View.GONE
            itemBinding.searchItem.text = getTypedAndUnTypedText(item)
        }
        return itemBinding.root
    }


    private fun getTypedAndUnTypedText(hintMapper: SearchHintMapper): SpannableString {
        spannableStringModule.newString()
        val otherWord = hintMapper.name.split(typedText, ignoreCase = true)
        val typedWord = this.typedText.split(hintMapper.name, ignoreCase = true)
//        var position: Int = 0
//        for (typedChar in typedText){
//            if(typedChar.lowercaseChar() == name[position].lowercaseChar()){
//                typedWork += name[position]
//                name = name.removeRange(0,1)
//                position +=1
//            }else if(typedChar.lowercaseChar() != name[position].lowercaseChar()){
//                break
//            }
//        }
        if (typedWord[0].isEmpty() && typedWord[1].isEmpty() && otherWord[0].isEmpty() && otherWord[1].isEmpty()) {
            spannableStringModule.addString(hintMapper.name).init(R.color.accent_color)
        } else {
            spannableStringModule.addString(typedWord[0]).init(R.color.accent_color_60)
            spannableStringModule.addString(otherWord[1]).init(R.color.accent_color)
        }
        return spannableStringModule.getSpannableString()
    }


    fun updateTypedText(typedText: String) {
        this.typedText = typedText
//        filterList()
    }

    override fun getFilter(): Filter = mFilter

    inner class ItemFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            val filteredList = mutableListOf<SearchHintMapper>()
            list.forEach { item ->
                if (item.name.contains(typedText, ignoreCase = true)) {
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