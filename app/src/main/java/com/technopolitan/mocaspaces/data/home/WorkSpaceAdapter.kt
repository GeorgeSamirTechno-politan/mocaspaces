package com.technopolitan.mocaspaces.data.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.shared.CountDownModule
import com.technopolitan.mocaspaces.databinding.WorkSpaceItemBinding
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import com.technopolitan.mocaspaces.modules.GlideModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import javax.inject.Inject

class WorkSpaceAdapter @Inject constructor(
    private var glideModule: GlideModule,
    private var context: Context,
    private var amenityAdapter: AmenityAdapter,
    private var countDownModule: CountDownModule,
    private var spannableStringModule: SpannableStringModule,
    private var priceViewPagerAdapter: PriceViewPagerAdapter,
) : RecyclerView.Adapter<WorkSpaceAdapter.ViewHolder>() {

    private val list: MutableList<WorkSpaceMapper> = mutableListOf()
    private lateinit var textAnimation: Animation

    fun init(list: MutableList<WorkSpaceMapper>) {
        val startPosition = this.list.size
        this.list.addAll(list)
        notifyItemRangeInserted(startPosition, itemCount)
        textAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
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

    inner class ViewHolder(private val itemBinding: WorkSpaceItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        lateinit var item: WorkSpaceMapper
        private var priceIndex = 0

        override fun onClick(v: View?) {

        }

        fun bind(it: WorkSpaceMapper) {
            this.item = it
            it.run {
                this.initPriceList(context)
                glideModule.loadImage(image, itemBinding.workSpaceImageView)
                if (isFavourite)
                    setFavourite()
                else
                    setUnFavourite()
                itemBinding.workSpaceNameTextView.text = locationName
                itemBinding.workSpaceAddressTextView.text = address

                itemBinding.priceViewPager.adapter = priceViewPagerAdapter
                priceViewPagerAdapter.init(priceList)
                amenityAdapter.init(amenityList)
                itemBinding.amenityRecycler.adapter = amenityAdapter
                itemBinding.workingHourTextView.text =
                    getOpenHourText(context, spannableStringModule)
            }
            updatePrice()
//            itemBinding.priceTextView.animation.setAnimationListener(this)
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


        private fun updatePrice() {
            itemBinding.priceViewPager.startAnimation(textAnimation)
            countDownModule.init()
            countDownModule.startCount(5) {
                priceIndex = if(priceIndex == 4) 0 else priceIndex+1
                itemBinding.priceViewPager.currentItem = priceIndex
                updatePrice()
//                notifyItemChanged(bindingAdapterPosition)
            }
        }


    }
}