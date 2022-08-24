package com.technopolitan.mocaspaces.modules

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.darwin.viola.still.FaceDetectionListener
import com.darwin.viola.still.Viola
import com.darwin.viola.still.model.CropAlgorithm
import com.darwin.viola.still.model.FaceDetectionError
import com.darwin.viola.still.model.FaceOptions
import com.darwin.viola.still.model.Result
import com.technopolitan.mocaspaces.R
import dagger.Module
import java.io.File
import javax.inject.Inject

@Module
class ViolaFaceDetectionModule @Inject constructor(
    dialogModule: DialogModule,
    context: Context
) {
    private lateinit var callbacks: (entity: Bitmap) -> Unit
    private val listener: FaceDetectionListener = object : FaceDetectionListener {
        override fun onFaceDetected(result: Result) {
            when (result.faceCount) {
                1 -> {
                    callbacks(result.facePortraits[0].face)
                }
                else -> {
                    dialogModule.showMessageDialog(
                        context.getString(R.string.please_upload_a_clear_picture_of_your_face),
                        context.getString(R.string.error)
                    )
                }
            }
        }

        override fun onFaceDetectionFailed(error: FaceDetectionError, message: String) {
            dialogModule.showMessageDialog(message, error.name)
        }
    }

    private val faceOption =
        FaceOptions.Builder()
            .enableProminentFaceDetection()
            .cropAlgorithm(CropAlgorithm.THREE_BY_FOUR)
            .enableDebug()
            .build()

    private val viola = Viola(listener)

    fun init(file: File, callbacks: (entity: Bitmap) -> Unit) {
        this.callbacks = callbacks
        viola.detectFace(imageBitMap(file), faceOption)
    }

    private fun imageBitMap(file: File): Bitmap {
        return BitmapFactory.decodeFile(file.path)
    }


}