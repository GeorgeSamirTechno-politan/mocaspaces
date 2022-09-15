package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.MeetingRoomItemBinding
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import javax.inject.Inject

class MeetingRoomAdapter @Inject constructor(
    private var glideModule: GlideModule,
    private var priceViewPagerAdapter: PriceViewPagerAdapter,
    private var context: Context
) : RecyclerView.Adapter<MeetingRoomAdapter.ViewHolder>() {


    private val list: MutableList<MeetingRoomMapper> = mutableListOf()

    fun init(list: List<MeetingRoomMapper>) {
        val startPosition = this.list.size
        this.list.addAll(list)
        notifyItemRangeInserted(startPosition, itemCount)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            MeetingRoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val itemBinding: MeetingRoomItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(it: MeetingRoomMapper) {
            itemBinding.meetingRoomName.text = it.venueName
            itemBinding.meetingLocationNameTextView.text = it.locationName
            itemBinding.meetingLocationAddressTextView.text = it.address
            itemBinding.meetingRoomName.text = it.venueName
            itemBinding.capacityTextView.text = it.capacity.toString()
            itemBinding.meetingRoomViewPagerAdapter.adapter = priceViewPagerAdapter
            priceViewPagerAdapter.init(it.priceList)
            glideModule.loadImage(it.image, itemBinding.meetingRoomImageView)
            if(it.workTimeMapper.isOpen()){
               setOpen()
            }else{
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