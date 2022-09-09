package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.Module
import javax.inject.Inject

@Module
class ImagePickerModule @Inject constructor(
    private var fragment: Fragment?,
) {

    private lateinit var callback: (path: String) -> Unit
    private lateinit var activityResult: ActivityResultLauncher<Intent>
    fun init(activityResult: ActivityResultLauncher<Intent>, callback: (path: String) -> Unit) {
        this.callback = callback
        this.activityResult = activityResult
        ImagePicker.with(fragment!!).galleryOnly()
            .cropSquare()
            .compress(720)
            .maxResultSize(720, 960)
            .setImageProviderInterceptor { imageProvider -> //Intercept ImageProvider
                Log.d("ImagePicker", "Selected ImageProvider: " + imageProvider.name)
            }
            .galleryMimeTypes(  //Exclude gif images
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .createIntent {
                activityResult.launch(it)
            }
    }


}