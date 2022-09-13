package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import dagger.Module
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@Module
class CompressModule @Inject constructor(
    private var context: Context,
    private var fragment: Fragment?,
    private var utilityModule: UtilityModule,
    private var activity: Activity
) : PickiTCallbacks {

    private lateinit var uri: Uri
    private lateinit var path: String
    private lateinit var callBack: (bitmap: Bitmap) -> Unit
    private lateinit var pickiT: PickiT

    fun init(uri: Uri, path: String, callBack: (bitmap: Bitmap) -> Unit) {
        this.uri = uri
        this.path = path
        this.callBack = callBack
        pickiT = PickiT(context, this, activity)
        fragment!!.lifecycleScope.launch {
            val compressedFile = compressImage()
            pickiT.getPath(compressedFile.toUri(), Build.VERSION.SDK_INT)
        }
    }

    private suspend fun compressImage(): File {
        try {
            return Compressor.compress(context, File(path), Dispatchers.IO) {
                resolution(640, 960)
                quality(60)
                format(Bitmap.CompressFormat.PNG)

            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return File(path)
    }


    override fun PickiTonUriReturned() {
        TODO("Not yet implemented")
    }

    override fun PickiTonStartListener() {
        TODO("Not yet implemented")
    }

    override fun PickiTonProgressUpdate(progress: Int) {
        TODO("Not yet implemented")
    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        callBack(utilityModule.rotateBitmap(utilityModule.getBitmap(path!!), 0.0f))
    }

    override fun PickiTonMultipleCompleteListener(
        paths: ArrayList<String>?,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        TODO("Not yet implemented")
    }

}