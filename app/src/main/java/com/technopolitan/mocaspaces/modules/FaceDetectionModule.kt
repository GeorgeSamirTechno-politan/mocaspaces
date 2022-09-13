package com.technopolitan.mocaspaces.modules

//import com.technopolitan.mocaspaces.R
//import java.io.File

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bumptech.glide.load.resource.bitmap.Rotate
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.technopolitan.mocaspaces.R
import dagger.Module
import javax.inject.Inject


@Module
class FaceDetectionModule @Inject constructor(
    private var dialogModule: DialogModule,
    private var context: Context,
    private var pixModule: PixModule,
    private var navigationModule: NavigationModule

//    private var cropFaceModule: CropFaceModule
) {
    private lateinit var callbacks: (uri: Uri, path: String) -> Unit
    private lateinit var callbacksBitmap: (bitmap: Bitmap) -> Unit

    //    private lateinit var faceOptions: FaceOptions
    private val highAccuracyOpts = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()
    private lateinit var inputImage: InputImage
    private lateinit var uri: Uri
    private var useCropAlgorithm: Boolean = false
    private lateinit var path: String
    private var useBitmap: Boolean = false
    private lateinit var bitmap: Bitmap

//    private val orientations = SparseIntArray()
//
//    init {
//        orientations.append(Surface.ROTATION_0, 0)
//        orientations.append(Surface.ROTATION_90, 90)
//        orientations.append(Surface.ROTATION_180, 180)
//        orientations.append(Surface.ROTATION_270, 270)
//    }

//    private fun getRotationCompensation(
//        cameraId: String,
//        activity: Activity,
//        isFrontFacing: Boolean
//    ): Int {
//        // Get the device's current rotation relative to its "native" orientation.
//        // Then, from the ORIENTATIONS table, look up the angle the image must be
//        // rotated to compensate for the device's rotation.
//        val deviceRotation = activity.windowManager.defaultDisplay.rotation
//        var rotationCompensation = orientations.get(deviceRotation)
//
//        // Get the device's sensor orientation.
//        val cameraManager = activity.getSystemService(CAMERA_SERVICE) as CameraManager
//        val sensorOrientation = cameraManager
//            .getCameraCharacteristics(cameraId)
//            .get(CameraCharacteristics.SENSOR_ORIENTATION)!!
//
//        if (isFrontFacing) {
//            rotationCompensation = (sensorOrientation + rotationCompensation) % 360
//        } else { // back-facing
//            rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360
//        }
//        return rotationCompensation
//    }


//    private val listener: FaceDetectionListener = object : FaceDetectionListener {
//        override fun onFaceDetected(result: Result) {
//            when (result.faceCount) {
//                1 -> {
//                    callbacks(result.facePortraits[0].face)
//                }
//                else -> {
//                    showError("")
//                }
//            }
//        }
//
//
//        override fun onFaceDetectionFailed(error: FaceDetectionError, message: String) {
//            Log.d(javaClass.name, message)
//            showError(error.message)
//        }
//    }

    //    private lateinit var faceOption: FaceOptions
//
//
//    private val viola = Viola(listener)

    fun init(
        path: String,
        uri: Uri,
        useCropAlgorithm: Boolean = true,
        callbacks: (uri: Uri, path: String) -> Unit
    ) {
        this.callbacks = callbacks
        inputImage = InputImage.fromFilePath(context, uri)
        this.path = path
        this.uri = uri
        this.useCropAlgorithm = useCropAlgorithm
        detectFace()
    }

    fun init(
        bitmap: Bitmap,
        useCropAlgorithm: Boolean = true,
        callbacks: (bitmap: Bitmap) -> Unit
    ) {
        useBitmap = true
        this.bitmap = bitmap
        this.callbacksBitmap = callbacks
        inputImage = InputImage.fromBitmap(bitmap, 0)
        this.useCropAlgorithm = useCropAlgorithm
        detectFace()
    }

    private fun detectFace() {
        try {
            FaceDetection.getClient(highAccuracyOpts).process(inputImage).addOnCompleteListener {
                if (it.isSuccessful && it.result?.size == 1)
                    handleImage()
                else {
                    it.exception?.let {exp->
                        Log.e(javaClass.name, "detectFace: ", exp)
                    }

                    showError()
                }

            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun handleImage() {
        if(useBitmap)
            callbacksBitmap(bitmap)
        else
        callbacks(uri, path)
    }


    private fun showError() {
        navigationModule.popBack()
        Handler(Looper.getMainLooper()).postDelayed({
            dialogModule.showMessageDialog(
                context.getString(R.string.please_upload_a_clear_picture_of_your_face),
                btnText = context.getString(R.string.try_again)
            ) {
                Handler(Looper.getMainLooper()).postDelayed({pixModule.showPix()}, 500)
            }
        }, 500)
    }


}