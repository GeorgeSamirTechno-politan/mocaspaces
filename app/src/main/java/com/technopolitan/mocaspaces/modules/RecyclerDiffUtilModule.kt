package com.technopolitan.mocaspaces.modules

import androidx.recyclerview.widget.DiffUtil
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.models.location.mappers.AmenityMapper
import com.technopolitan.mocaspaces.models.location.mappers.HomeSearchMapper
import com.technopolitan.mocaspaces.models.location.mappers.PricePagerMapper
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper

class RecyclerDiffUtilModule<T> constructor(
    private var oldList: MutableList<T>,
    private var newList: MutableList<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return when (oldItem) {
            is WorkSpaceMapper -> (oldItem as WorkSpaceMapper) == (newItem as WorkSpaceMapper)
            is DropDownMapper -> (oldItem as DropDownMapper) == (newItem as DropDownMapper)
            is HomeSearchMapper -> (oldItem as HomeSearchMapper) == (newItem as HomeSearchMapper)
            is MeetingRoomMapper -> (oldItem as MeetingRoomMapper) == (newItem as MeetingRoomMapper)
            is AmenityMapper -> (oldItem as AmenityMapper) == (newItem as AmenityMapper)
            is PricePagerMapper -> (oldItem as PricePagerMapper) == (newItem as PricePagerMapper)
            else -> (oldList[oldItemPosition] as WorkSpaceMapper) === newList[newItemPosition]
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return when (oldItem) {
            is WorkSpaceMapper -> (oldItem as WorkSpaceMapper).hashCode() == (newItem as WorkSpaceMapper).hashCode()
            is DropDownMapper -> (oldItem as DropDownMapper).hashCode() == (newItem as DropDownMapper).hashCode()
            is HomeSearchMapper -> (oldItem as HomeSearchMapper).hashCode() == (newItem as HomeSearchMapper).hashCode()
            is MeetingRoomMapper -> (oldItem as MeetingRoomMapper).hashCode() == (newItem as MeetingRoomMapper).hashCode()
            is AmenityMapper -> (oldItem as AmenityMapper).hashCode() == (newItem as AmenityMapper).hashCode()
            is PricePagerMapper -> (oldItem as PricePagerMapper).hashCode() == (newItem as PricePagerMapper).hashCode()
            else -> (oldList[oldItemPosition] as WorkSpaceMapper) == newList[newItemPosition]
        }
    }


}