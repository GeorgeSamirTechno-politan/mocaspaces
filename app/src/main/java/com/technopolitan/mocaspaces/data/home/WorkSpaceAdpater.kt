package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.bases.BaseRecyclerPaginationAdapter
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
) : BaseRecyclerPaginationAdapter<WorkSpaceMapper, WorkSpaceItemBinding>(), View.OnClickListener {

    private lateinit var favouriteCallbacks: (workspaceMapper: WorkSpaceMapper) -> Unit
    private lateinit var item: WorkSpaceMapper


    fun setFavouriteCallBack(favouriteCallbacks: (workspaceMapper: WorkSpaceMapper) -> Unit) {
        this.favouriteCallbacks = favouriteCallbacks
    }

    override fun initBinding(parent: ViewGroup, viewType: Int) {
        itemBinding =
            WorkSpaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        itemBinding.favouriteStatusImageView.setOnClickListener(this)
        itemBinding.workSpaceImageView.setOnClickListener(this)
        itemBinding.workSpaceBookBtn.setOnClickListener(this)
    }

    override fun bind(viewBinding: WorkSpaceItemBinding, listType: WorkSpaceMapper) {
        this.item = listType
        setPrice(listType)
        glideModule.loadImage(listType.image, itemBinding.workSpaceImageView)
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

    override fun onClick(v: View?) {
        if (v?.id == R.id.favourite_status_image_view) {
            favouriteCallbacks(item)
            item.isFavourite = !item.isFavourite
            diffResult.dispatchUpdatesTo(this)
        } else if (v?.id == R.id.work_space_image_view) {
            Log.d(javaClass.name, "onClick: ")
        }
    }
}