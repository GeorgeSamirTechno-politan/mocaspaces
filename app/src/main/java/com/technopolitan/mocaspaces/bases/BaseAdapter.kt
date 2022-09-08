//package com.technopolitan.mocaspaces.bases
//
//import android.icu.lang.UCharacter.GraphemeClusterBreak.T
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//
//
//abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.ViewHolder>() {
//
//
//   private lateinit var  list: MutableList<T>
//    private lateinit var onClickCallBack: (entity: T) -> Unit
//
//    fun setList(list: MutableList<T>){
//        this.list = list
//        notifyDataSetChanged()
//    }
//
//    open fun insertItem(item: T) {
//        list.add(item)
//        notifyItemInserted(list.size)
//    }
//
//    open fun deleteItem(itemPosition: Int) {
//        list.removeAt(itemPosition)
//        notifyItemRemoved(itemPosition)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
//        return getView(parent, viewType);
//    }
//
//    abstract fun getView(parent: ViewGroup, viewType: Int): ViewHolder<T>
//
//    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
//        list[position]?.let { holder.bind(it) }
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//
//
//
//    abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView),
//        View.OnClickListener {
//        override fun onClick(v: View?) {
//
//        }
//
//        abstract fun bind(item: T)
//    }
//
//
//}