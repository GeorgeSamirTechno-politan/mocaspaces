package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.bases.BaseRecyclerAdapter
import com.technopolitan.mocaspaces.databinding.WorkSpaceItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.utilities.autoScroll
import javax.inject.Inject

class WorkSpaceAdapter @Inject constructor(
    private var glideModule: GlideModule,
    private var context: Context,
    private var amenityAdapter: AmenityAdapter,
    private var spannableStringModule: SpannableStringModule,
    private var priceAdapter: PriceAdapter
) : BaseRecyclerAdapter<WorkSpaceMapper, WorkSpaceItemBinding>() {

    private lateinit var favouriteCallbacks: (workspaceMapper: WorkSpaceMapper) -> Unit


    fun setFavouriteCallBack(favouriteCallbacks: (workspaceMapper: WorkSpaceMapper) -> Unit) {
        this.favouriteCallbacks = favouriteCallbacks
    }

    override fun itemBinding(parent: ViewGroup, viewType: Int): ItemViewHolder {
        itemBinding =
            WorkSpaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun initItemWithBinding(holder: RecyclerView.ViewHolder, item: WorkSpaceMapper) {
        (holder as ItemViewHolder).bind(item)
    }

    inner class ItemViewHolder(private val itemBind: WorkSpaceItemBinding) :
        RecyclerView.ViewHolder(itemBind.root), View.OnClickListener {

        init {
            itemBind.workSpaceImageView.setOnClickListener(this)
            itemBind.favouriteStatusImageView.setOnClickListener(this)
            itemBind.shareImageButton.setOnClickListener(this)
            itemBind.workSpaceBookBtn.setOnClickListener(this)
        }

        fun bind(item: WorkSpaceMapper) {
            setPrice(item)
            glideModule.loadImage(item.image, itemBinding.workSpaceImageView)
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
            amenityAdapter.setData(item.amenityList.toMutableList(), false)
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
            priceAdapter.setData(item.priceList.toMutableList(), false)
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

        override fun onClick(v: View?) {
            if (v?.id == itemBind.favouriteStatusImageView.id) {
                val item = getItem(bindingAdapterPosition)
                favouriteCallbacks(item)
                item.isFavourite = item.isFavourite.not()
                updateItem(item, bindingAdapterPosition)
            } else if (v?.id == itemBind.workSpaceImageView.id) {
                Log.d(javaClass.name, "onClick: ")
            }
        }

    }
}