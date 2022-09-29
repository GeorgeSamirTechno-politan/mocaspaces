package com.technopolitan.mocaspaces.bases

import android.app.Activity
import android.util.DisplayMetrics
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
    protected var itemIndex = -1
    protected var defaultMaxItemCount: Int = 0

    companion object {
        const val normal = 1
        const val loading = 0
        const val noDataFound = 2
    }

    init {
        setHasStableIds(true)
    }


    override fun getItemCount(): Int =
        if (list.size > defaultMaxItemCount && defaultMaxItemCount > 0) defaultMaxItemCount else list.size

    fun setData(newList: MutableList<T?>) {
        val diffCallback = RecyclerDiffUtilModule(this.list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        if (this.list.isNotEmpty())
            this.list.clear()
        this.list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    protected fun getItemMinWidthWithMaxItemCount(): Int? {
        if (defaultMaxItemCount > 0) {
            val dm = DisplayMetrics()
            (itemBinding.root.context as Activity).windowManager.defaultDisplay.getMetrics(dm)
            return dm.widthPixels / defaultMaxItemCount + 4
        }
        return null
    }


    fun getItem(position: Int): T {
        return list[position]!!
    }

    fun clearAdapter() {
        if (this.list.isNotEmpty()) {
            val diffCallback = RecyclerDiffUtilModule(this.list, mutableListOf())
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.list.clear()
            this.itemIndex = -1
            diffResult.dispatchUpdatesTo(this)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (list.first() == null) noDataFound else if (list[position] == null) loading else normal
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


