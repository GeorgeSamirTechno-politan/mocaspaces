package com.technopolitan.mocaspaces.bases

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.technopolitan.mocaspaces.modules.RecyclerDiffUtilModule

open abstract class BaseRecyclerAdapter<T, K : ViewBinding> :
    RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder<K>>() {

    private var list: MutableList<T> = mutableListOf()


    lateinit var itemBinding: K
    lateinit var diffResult: DiffResult
    private lateinit var diffCallback: RecyclerDiffUtilModule<T>
    abstract fun bind(viewBinding: K, listType: T)

    fun setData(list: MutableList<T>) {
        diffCallback = RecyclerDiffUtilModule(this.list, list)
        diffResult = DiffUtil.calculateDiff(diffCallback)
        if (this.list.isNotEmpty())
            this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    abstract fun initBinding(parent: ViewGroup, viewType: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<K> {
        initBinding(parent, viewType)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder<K>, position: Int) {
        bind(holder.binding, list[position])
    }

    class ViewHolder<K : ViewBinding>(val binding: K) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = list.size

}