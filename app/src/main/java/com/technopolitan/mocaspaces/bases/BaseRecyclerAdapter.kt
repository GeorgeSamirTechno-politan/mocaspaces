package com.technopolitan.mocaspaces.bases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.technopolitan.mocaspaces.databinding.NoDataFoundBinding
import com.technopolitan.mocaspaces.databinding.ProgressLayoutBinding
import com.technopolitan.mocaspaces.modules.RecyclerDiffUtilModule


abstract class BaseRecyclerAdapter<T, K : ViewBinding> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var itemBinding: K
    protected var list: MutableList<T?> = mutableListOf()

    companion object {
        const val normal = 1
        const val loading = 0
        const val noDataFound = 2
    }


    override fun getItemCount(): Int = list.size

    fun setData(newList: MutableList<T?>, hasMoreData: Boolean) {
        val startPosition = itemCount
        if (newList.isEmpty() && hasMoreData) {
            newList.add(null)
        }
        this.list.addAll(newList)
        notifyItemRangeInserted(startPosition, itemCount)
//        diffList(this.list, newList)
    }

    fun getItem(position: Int): T {
        return list[position]!!
    }

    fun clearAdapter(){
        if(this.list.isNotEmpty()){
            this.list.clear()
            notifyItemRangeRemoved(0, itemCount)
        }
    }

    fun updateItem(item: T, position: Int) {
       notifyItemChanged(position)
    }

    private fun diffList(oldList: MutableList<T?>, newList: MutableList<T?>) {
        val diffCallback = RecyclerDiffUtilModule(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

//    fun clearList() {
//        differ.submitList(mutableListOf())
//    }


    override fun getItemViewType(position: Int): Int {
        return if (list.isEmpty()) noDataFound else if (list[position] == null) loading else normal
    }

    private fun getLoadingBindingInit(parent: ViewGroup): ProgressLayoutBinding =
        ProgressLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    private fun getNoDataFoundBindingInit(parent: ViewGroup): NoDataFoundBinding =
        NoDataFoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    abstract fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoDataFoundViewHolder -> {
                holder.bindNoDataFound()
            }
            is LoadingViewHolder -> {
                holder.bindLoading()
            }
            else -> {
                list[position]?.let {
                    initItemWithBinding(holder, it)
                }

            }
        }
    }

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    abstract fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            normal -> itemBinding(parent, viewType)
            noDataFound -> NoDataFoundViewHolder(getNoDataFoundBindingInit(parent))
            else -> LoadingViewHolder(getLoadingBindingInit(parent))
        }
    }


    private class LoadingViewHolder(private val loadingBinding: ProgressLayoutBinding) :
        RecyclerView.ViewHolder(loadingBinding.root) {

        fun bindLoading() {
            loadingBinding.progressView.visibility = View.VISIBLE
        }
    }

    private class NoDataFoundViewHolder(private val loadingBinding: NoDataFoundBinding) :
        RecyclerView.ViewHolder(loadingBinding.root) {

        fun bindNoDataFound() {
            loadingBinding.noDataFoundTextView.visibility = View.VISIBLE
        }
    }

}


