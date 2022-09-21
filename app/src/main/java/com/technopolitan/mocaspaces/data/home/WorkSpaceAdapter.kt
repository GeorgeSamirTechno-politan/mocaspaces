package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.WorkSpaceItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import com.technopolitan.mocaspaces.modules.RecyclerDiffUtilModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.utilities.autoScroll
import javax.inject.Inject

class WorkSpaceAdapter @Inject constructor(
    private var glideModule: GlideModule,
    private var context: Context,
    private var amenityAdapter: AmenityAdapter,
    private var spannableStringModule: SpannableStringModule,
    private var priceAdapter: PriceAdapter,
) : RecyclerView.Adapter<WorkSpaceAdapter.ViewHolder>() {

    private val list: MutableList<WorkSpaceMapper> = mutableListOf()
//    private lateinit var textAnimation: Animation

    fun addList(list: MutableList<WorkSpaceMapper>) {
        val startPosition = this.list.size
        this.list.addAll(list)
        notifyItemRangeInserted(startPosition, itemCount)
//        textAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
    }

    fun init(list: MutableList<WorkSpaceMapper>) {
        val diffCallback = RecyclerDiffUtilModule(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        if (this.list.isNotEmpty())
            this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)

//        val startPosition = 0
//        if (this.list.isNotEmpty())
//            this.list.clear()
//        this.list.addAll(list)
//        notifyDataSetChanged()
//        textAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            WorkSpaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun clearList() {
        var endPosition = 0
        if (this.list.isNotEmpty()) {
            endPosition = itemCount
            this.list.clear()
        }
        notifyItemRangeRemoved(0, endPosition)
    }

    inner class ViewHolder(private val itemBinding: WorkSpaceItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        lateinit var item: WorkSpaceMapper
        override fun onClick(v: View?) {

        }

        fun bind(it: WorkSpaceMapper) {
            this.item = it
            setPrice(it)
            glideModule.loadImage(it.image, itemBinding.workSpaceImageView)
            setFavouriteAndUnFavourite(item)
            itemBinding.workSpaceNameTextView.text = item.locationName
            itemBinding.workSpaceAddressTextView.text = item.address
            itemBinding.locationDistanceTextView.text = item.distance
            setAmenities(item)
            itemBinding.workingHourTextView.text =
                item.getOpenHourText(context, spannableStringModule)
            setOpenClose(item)
        }

        private fun setOpenClose(item: WorkSpaceMapper) {
            if (item.workTimeMapper.isOpen()) {
                setOpen()
            } else {
                setClose()
            }
        }

        private fun setAmenities(item: WorkSpaceMapper) {
            amenityAdapter.init(item.amenityList)
            itemBinding.amenityRecycler.adapter = amenityAdapter
        }

        private fun setFavouriteAndUnFavourite(item: WorkSpaceMapper) {
            if (item.isFavourite)
                setFavourite()
            else
                setUnFavourite()
        }

        private fun setPrice(item: WorkSpaceMapper) {
            item.initPriceList(context)
            priceAdapter.init(item.priceList)
            itemBinding.priceViewPager.adapter = priceAdapter
            itemBinding.priceViewPager.autoScroll(5000)
        }

        private fun setOpen() {
            itemBinding.workSpaceStatusTextView.background =
                AppCompatResources.getDrawable(context, R.drawable.green_shape)
            itemBinding.workSpaceStatusTextView.text = context.getText(R.string.open)
        }

        private fun setClose() {
            itemBinding.workSpaceStatusTextView.background =
                AppCompatResources.getDrawable(context, R.drawable.red_shape)
            itemBinding.workSpaceStatusTextView.text = context.getText(R.string.close)
        }

        private fun setFavourite() {
            itemBinding.favouriteStatusImageView.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_favourite
                )
            )
        }

        private fun setUnFavourite() {
            itemBinding.favouriteStatusImageView.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.ic_un_favourite
                )
            )
        }
    }
}