package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.MediaPlayer
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.imageview.ShapeableImageView
import com.google.common.util.concurrent.ListenableFuture
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.utilities.Constants
import dagger.Module
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject


@Module
class CameraXModule @Inject constructor(
    private var context: Context, private var alertModule: CustomAlertModule,
    private var activity: Activity, private var pikItModule: PikItModule
) {

    private lateinit var processCameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var processCameraProvider: ProcessCameraProvider
    private lateinit var previewView: PreviewView
    private lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var captureImageView: ShapeableImageView
    private val fileNameFormat = "yyyy-MM-dd-HH-mm-ss-SSS"
    private lateinit var uri: Uri
    private lateinit var croppedView: View
    private lateinit var createdPath: String
    private lateinit var callBack: (path: String)-> Unit
    private lateinit var camera: Camera
    private lateinit var mPlayer: MediaPlayer

    fun create() {
        processCameraProviderFuture = ProcessCameraProvider.getInstance(context)
    }

    fun init(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
        captureImageView: ShapeableImageView,
        croppedView: View,
        callBack: (path: String)-> Unit
    ) {
        this.previewView = previewView
        this.lifecycleOwner = lifecycleOwner
        this.captureImageView = captureImageView
        this.croppedView = croppedView
        this.callBack =callBack
        mPlayer = MediaPlayer.create(context, R.raw.take_picture_sound)
        processCameraProviderFuture.addListener({
            processCameraProvider = processCameraProviderFuture.get()
            previewView.post { setupCamera() }
        }, ContextCompat.getMainExecutor(context))
    }

    fun destroy() {
        if (::processCameraProvider.isInitialized) {
            processCameraProvider.unbindAll()
        }
    }

    private fun setupCamera() {
        processCameraProvider.unbindAll()
        camera = processCameraProvider.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            buildPreviewUseCase(),
            buildImageCaptureUseCase(),
            buildImageAnalysisUseCase()
        )
//        camera.foc
    }

    private fun buildPreviewUseCase(): Preview {
        val display = previewView.display
        val metrics = DisplayMetrics().also { display.getMetrics(it) }
        return Preview.Builder()
            .setTargetRotation(display.rotation)
            .setTargetResolution(Size(metrics.widthPixels, metrics.heightPixels))
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
    }


    private fun buildImageCaptureUseCase(): ImageCapture {
        val display = previewView.display
        val metrics = DisplayMetrics().also { display.getMetrics(it) }
        val capture = ImageCapture.Builder()
            .setTargetRotation(display.rotation)
            .setTargetResolution(Size(metrics.widthPixels, metrics.heightPixels))
            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .build()
        capturePhoto(capture)
        return capture
    }

    private fun buildImageAnalysisUseCase(): ImageAnalysis {
        val display = previewView.display
        val metrics = DisplayMetrics().also { display.getMetrics(it) }
        val analysis = ImageAnalysis.Builder()
            .setTargetRotation(display.rotation)
            .setTargetResolution(Size(metrics.widthPixels, metrics.heightPixels))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_BLOCK_PRODUCER)
            .setImageQueueDepth(10)
            .build()
        analysis.setAnalyzer(
            Executors.newSingleThreadExecutor()
        ) { imageProxy ->
            Log.d("CameraFragment", "Image analysis result $imageProxy")
            imageProxy.close()
        }
        return analysis
    }

//    private fun setupTapForFocus(cameraControl: CameraControl) {
//        previewView.setOnTouchListener{_, event ->
//
//        }
//        previewView.setOnTouchListener { _, event ->
//
//        }
//    }


//    private val onTouchListener: View.OnTouchListener = object : View.OnTouchListener{
//        override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
//            if (event!!.action != MotionEvent.ACTION_UP) {
//                return@onTouchListener true
//            }
//
////            val textureView = previewView.getChildAt(0) as? TextureView
////                ?: return@setOnTouchListener true
////            val factory = TextureViewMeteringPointFactory(textureView)
//
////            val point = factory.createPoint(event.x, event.y)
//            val action = FocusMeteringAction.Builder(point).build()
//            cameraControl.startFocusAndMetering(action)
//            return@setOnTouchListener true
//        }
//
//    }

    private fun capturePhoto(capture: ImageCapture) {
        val executor = Executors.newSingleThreadExecutor()
        captureImageView.setOnClickListener {
            mPlayer.start()
            capture.takePicture(
                outputFileOptions(),
                executor,
                onImageCapture
            )
        }
    }

    private fun contentValue(): ContentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, imageName())
        put(MediaStore.MediaColumns.MIME_TYPE, imageType())
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, Constants.mocaImagesPath)
        }
    }

    private fun imageName(): String = SimpleDateFormat(fileNameFormat, Locale.US)
        .format(System.currentTimeMillis())

    private fun imageType(): String = "image/png"

    private fun outputFileOptions(): ImageCapture.OutputFileOptions =
        ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValue()
        ).build()

    private val onImageCapture: ImageCapture.OnImageSavedCallback =
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val msg = "Photo capture succeeded: ${outputFileResults.savedUri}"
                outputFileResults.savedUri?.let {
                    uri = it
                    pikItModule.init(it) { path ->
                        createdPath = path
                        val bitmap = BitmapFactory.decodeFile(path)
                        val rotatedBitmap = bitmap.rotate(90)
                        val croppedImage = cropImage(rotatedBitmap, previewView, croppedView)
                        createdPath = saveImage(croppedImage)
                        callBack(createdPath)
                    }
                }
                Log.d(javaClass.name, msg)
            }

            override fun onError(exception: ImageCaptureException) {
                exception.message?.let {
                    alertModule.showSnakeBar(activity.window.decorView as ViewGroup, it)
                    Log.e(javaClass.name, "Photo capture failed: $it", exception)
                }
            }

        }

    private fun cropImage(bitmap: Bitmap, frame: View, reference: View): ByteArray {
        val heightOriginal = frame.height
        val widthOriginal = frame.width
        val heightFrame = reference.height
        val widthFrame = reference.width
        val leftFrame = reference.left
        val topFrame = reference.top
        val heightReal = bitmap.height
        val widthReal = bitmap.width
        val widthFinal = widthFrame * widthReal / widthOriginal
        val heightFinal = heightFrame * heightReal / heightOriginal
        val leftFinal = leftFrame * widthReal / widthOriginal
        val topFinal = topFrame * heightReal / heightOriginal
        val bitmapFinal = Bitmap.createBitmap(
            bitmap,
            leftFinal, topFinal, widthFinal, heightFinal
        )
        val stream = ByteArrayOutputStream()
        bitmapFinal.compress(
            Bitmap.CompressFormat.PNG,
            100,
            stream
        ) //100 is the best quality possible
        return stream.toByteArray()
    }

    private fun saveImage(bytes: ByteArray): String {
        val outStream: FileOutputStream
        try {
            val file = File(createdPath)
            outStream = FileOutputStream(file)
            outStream.write(bytes)
            MediaScannerConnection.scanFile(
                context,
                arrayOf(file.path),
                arrayOf("image/png"), null
            )
            outStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return createdPath
    }

    private fun Bitmap.rotate(degree: Int): Bitmap {
        // Initialize a new matrix
        val matrix = Matrix()

        // Rotate the bitmap
        matrix.postRotate(degree.toFloat())

        // Resize the bitmap
        val scaledBitmap = Bitmap.createScaledBitmap(
            this,
            width,
            height,
            true
        )

        // Create and return the rotated bitmap
        return Bitmap.createBitmap(
            scaledBitmap,
            0,
            0,
            scaledBitmap.width,
            scaledBitmap.height,
            matrix,
            true
        )
    }


}