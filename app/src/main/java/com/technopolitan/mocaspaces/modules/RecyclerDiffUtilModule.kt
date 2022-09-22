package com.technopolitan.mocaspaces.modules

import androidx.recyclerview.widget.DiffUtil

class RecyclerDiffUtilModule<T>  constructor(private var oldList: MutableList<T>,private var newList: MutableList<T>) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]

    }


}