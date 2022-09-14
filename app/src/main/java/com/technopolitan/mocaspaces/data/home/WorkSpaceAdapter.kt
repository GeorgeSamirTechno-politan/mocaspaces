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
    private var spannableStringModule: SpannableStringModule
) : RecyclerView.Adapter<WorkSpaceAdapter.ViewHolder>() {

    private lateinit var list: MutableList<WorkSpaceMapper>
    private lateinit var textAnimation: Animation

    fun init(list: MutableList<WorkSpaceMapper>) {
        this.list = list
        textAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
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
        return if (::list.isInitialized)
            list.size
        else 0
    }

    inner class ViewHolder(private val itemBinding: WorkSpaceItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener,
        Animation.AnimationListener {
        lateinit var item: WorkSpaceMapper
        private var priceIndex = 0

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        override fun onAnimationStart(animation: Animation?) {
            TODO("Not yet implemented")
        }

        override fun onAnimationEnd(animation: Animation?) {
//            if(item.dayPassShowed && !item.hourlyShowed){
//                getPriceForHourly(item.hourlyPrice, item.currency)
//                item.hourlyShowed = true
//            }else if(item.dayPassShowed && item.hourlyShowed && !item.tailoredShowed){
//                getPriceForTailored(item.tailoredPrice, item.currency)
//                item.tailoredShowed = true
//            }else if(item.dayPassShowed && item.hourlyShowed && item.tailoredShowed && !item.bundlePassShowed){
//                getPriceForBundle(item.bundlePrice, item.currency)
//                item.bundlePassShowed = true
//            }else{
//                getPriceForHourly(item.hourlyPrice, item.currency)
//                item.hourlyShowed = false
//                item.bundlePassShowed = false
//                item.tailoredShowed = false
//            }
//
//                notifyItemChanged(bindingAdapterPosition)
            priceIndex += 1
            notifyItemChanged(bindingAdapterPosition)
        }

        override fun onAnimationRepeat(animation: Animation?) {
            TODO("Not yet implemented")
        }

        fun bind(it: WorkSpaceMapper) {
            this.item = it
            it.run {
                initPriceList(context, spannableStringModule)
                glideModule.loadImage(image, itemBinding.workSpaceImageView)
                if (isFavourite)
                    setFavourite()
                else
                    setUnFavourite()
                itemBinding.workSpaceNameTextView.text = locationName
                itemBinding.workSpaceAddressTextView.text = address
                itemBinding.priceTextView.setFactory {
                    return@setFactory TextView(context)
                }
                itemBinding.priceTextView.setText(priceList[priceIndex])
            }
            itemBinding.priceTextView.animation.setAnimationListener(this)

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
            countDownModule.init()
            countDownModule.startCount(3) {
                itemBinding.priceTextView.startAnimation(textAnimation)
                textAnimation.setAnimationListener(this)
            }
        }


    }
}