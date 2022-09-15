package com.technopolitan.mocaspaces.modules

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.toBitmap
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.utilities.BitmapExtension.createBitmapWithBorder
import dagger.Module
import javax.inject.Inject


@Module
class GlideModule @Inject constructor(private var context: Context) {


    fun loadImage(
        url: String?,
        imageView: ImageView,
        errorImage: Int = R.drawable.ic_no_image_found,
    ) {
        try {
            if (url != null) {
                Glide.with(context).load(url)
                    .placeholder(getCircularProgressDrawable(context))
                    .error(AppCompatResources.getDrawable(context, errorImage))
                    .optionalFitCenter()
                    .into(imageView)
            }
        } catch (e: Exception) {
            Log.e(javaClass.name, "error while loading image", e)
            Glide.with(context).load(errorImage).into(imageView)
        }
    }

    fun loadImageBitmap(
        url: String?,
        res: Resources,
        borderSize: Float = 2f,
        borderColor: Int = R.color.accent_color,
        errorImage: Int = R.drawable.ic_no_image_found,
        width: Int = 50,
        height: Int = 50,
        callback: (bitmap: Bitmap) -> Unit
    ) {
        try {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .placeholder(getCircularProgressDrawable(context))
                .error(errorImage)
                .apply(
                    RequestOptions.circleCropTransform().placeholder(R.drawable.ic_persons)
                )
                .into(object :
                    CustomTarget<Bitmap>(width, height) {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        resource.run {
                            callback(RoundedBitmapDrawableFactory.create(
                                res,
                                if (borderSize > 0) {
                                    createBitmapWithBorder(2f, borderColor)
                                } else {
                                    this
                                }
                            ).apply {
                                isCircular = true
                            }.toBitmap())
                        }
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        errorDrawable?.let { callback(it.toBitmap()) }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        placeholder?.let { callback(it.toBitmap()) }
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(
            context.resources.getColor(
                R.color.accent_color,
                context.theme
            )
        )
        circularProgressDrawable.start()
        return circularProgressDrawable
    }
}