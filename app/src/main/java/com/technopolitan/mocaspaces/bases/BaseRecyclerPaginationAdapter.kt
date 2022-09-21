package com.technopolitan.mocaspaces.bases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.technopolitan.mocaspaces.databinding.ProgressLayoutBinding
import com.technopolitan.mocaspaces.modules.RecyclerDiffUtilModule


open abstract class BaseRecyclerPaginationAdapter<T, K : ViewBinding> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var itemBinding: K
    lateinit var diffResult: DiffUtil.DiffResult
    private lateinit var diffCallback: RecyclerDiffUtilModule<T?>
    abstract fun bind(viewBinding: K, listType: T)

    companion object {
        const val normal = 1
        const val loading = 0
    }

    private var list: MutableList<T?> = mutableListOf()

    override fun getItemCount(): Int = list.size

    fun setData(list: MutableList<T?>, hasMoreData: Boolean) {
        diffCallback = RecyclerDiffUtilModule(this.list, list)
        diffResult = DiffUtil.calculateDiff(diffCallback)
        if (this.list.isNotEmpty())
            this.list.clear()
        this.list.addAll(list)
        if (hasMoreData)
            this.list.add(null)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addMoreDate(list: MutableList<T?>, hasMoreData: Boolean) {
        if (this.list[this.list.size - 1] == null)
            this.list.removeAt(this.list.size - 1)
        diffCallback = RecyclerDiffUtilModule(this.list, list)
        diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list.addAll(list)
        if (hasMoreData)
            this.list.add(null)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) loading else normal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val loadingBinding: ProgressLayoutBinding =
            ProgressLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return if (viewType == normal) {
            initBinding(parent, viewType)
            ItemViewHolder(itemBinding)
        } else LoadingViewHolder(loadingBinding)
    }

    abstract fun initBinding(parent: ViewGroup, viewType: Int)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoadingViewHolder) {
            holder.bindLoading()
        } else {
            list[position]?.let {
                bind((holder as ItemViewHolder<K>).binding, it)
            }

        }
    }

    class LoadingViewHolder(private val loadingBinding: ProgressLayoutBinding) :
        RecyclerView.ViewHolder(loadingBinding.root) {

        fun bindLoading() {
            loadingBinding.progressView.visibility = View.VISIBLE
        }
    }

    class ItemViewHolder<K : ViewBinding>(val binding: K) : RecyclerView.ViewHolder(binding.root)

}


