package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.MeetingRoomItemBinding
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import javax.inject.Inject

class MeetingRoomAdapter @Inject constructor(
    private var glideModule: GlideModule,
    private var context: Context
) : BaseRecyclerAdapter<MeetingRoomMapper, MeetingRoomItemBinding>() {


    override fun itemBinding(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            MeetingRoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeetingViewHolder(itemBinding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: MeetingRoomMapper) {
        (holder as MeetingViewHolder).bind(item)
    }

    private inner class MeetingViewHolder(private val itemBinding: MeetingRoomItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(it: MeetingRoomMapper) {
            itemBinding.meetingRoomName.text = it.venueName
            itemBinding.meetingLocationNameTextView.text = it.locationName
            itemBinding.meetingLocationAddressTextView.text = it.address
            itemBinding.meetingRoomName.text = it.venueName
            itemBinding.capacityTextView.text = it.capacity.toString()
            val priceAdapter = PriceAdapter()
            itemBinding.meetingRoomViewPagerAdapter.adapter = priceAdapter
            priceAdapter.setData(it.priceList.toMutableList(), false)
            glideModule.loadImage(it.image, itemBinding.meetingRoomImageView)
            if (it.workTimeMapper.isOpen()) {
                setOpen()
            } else {
                setClose()
            }
        }

        private fun setOpen(){
            itemBinding.meetingRoomStatusTextView.background = AppCompatResources.getDrawable(context, R.drawable.green_shape)
            itemBinding.meetingRoomStatusTextView.text = context.getText(R.string.open)
        }

        private fun setClose(){
            itemBinding.meetingRoomStatusTextView.background = AppCompatResources.getDrawable(context, R.drawable.red_shape)
            itemBinding.meetingRoomStatusTextView.text = context.getText(R.string.close)
        }
    }


}