package com.technopolitan.mocaspaces.modules

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.technopolitan.mocaspaces.R
import dagger.Module
import javax.inject.Inject


@Module
class GlideModule @Inject constructor(private var context: Context) {


    fun loadImage(
        url: String?,
        imageView: ImageView,
        errorImage: Int = R.drawable.ic_no_image_found
    ) {
        try {
            if (url != null) {
                Glide.with(context).load(url)
                    .placeholder(getCircularProgressDrawable(context))
                    .error(AppCompatResources.getDrawable(context,errorImage))
                    .centerCrop()
                    .into(imageView)
            }
        } catch (e: Exception) {
            Log.e(javaClass.name, "error while loading image", e)
            Glide.with(context).load(errorImage).into(imageView)
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