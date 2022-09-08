package com.technopolitan.mocaspaces.ui.customCameraX

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Rational
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.Surface.*
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.technopolitan.mocaspaces.databinding.FragmentCustomCameraXBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.*
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject


class CustomCameraXFragment : Fragment() {

    @Inject
    lateinit var permissionModule: PermissionModule

    @Inject
    lateinit var alertModule: CustomAlertModule

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var pikItModule: PikItModule

//    @Inject
//    lateinit var bitmapModule: BitmapModule

    @Inject
    lateinit var cameraXModule: CameraXModule

    private lateinit var binding: FragmentCustomCameraXBinding
//    private lateinit var targetResolution: Size
//    private lateinit var rational: Rational
//
//    private lateinit var imageCapture: ImageCapture
//    private lateinit var cameraExecutor: ExecutorService
//    private lateinit var imageAnalysis: ImageAnalysis
//    private lateinit var viewPort: ViewPort
//    private lateinit var useCaseGroup: UseCaseGroup
//    private lateinit var metrics :DisplayMetrics


    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraXModule.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomCameraXBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPermissions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraXModule.destroy()
    }

//    private fun initView() {
//        metrics = DisplayMetrics().also { binding.root.display.getRealMetrics(it) }
//        targetResolution = Size(metrics.widthPixels, metrics.heightPixels)
//        rational = Rational(metrics.widthPixels, metrics.heightPixels)
//        binding.cameraXAppBar.appToolBar.title = getString(R.string.scan)
//        binding.cameraXAppBar.appToolBar.setNavigationOnClickListener {
//            navigationModule.popBack()
//        }
//        binding.scanImageView.setOnClickListener {
//            takePhoto()
//        }
//    }

    private fun initPermissions() {
        val permissionList = mutableListOf<String>()
        permissionList.add(android.Manifest.permission.CAMERA)
        permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        permissionModule.init(permissionList) {
            cameraXModule.init(
                binding.cameraPreview,
                viewLifecycleOwner,
                binding.scanImageView,
                binding.cameraPreviewCapture
            ) {

            }
//        }
    }




//    private fun init() {
//        initImageCapture()
//        initImageAnalysis()
//        initViewPort()
//        initUseCaseGroup()
//        startCamera()
//        cameraExecutor = Executors.newSingleThreadExecutor()
//    }
//
//    private fun initImageAnalysis() {
//        imageAnalysis = ImageAnalysis.Builder()
//            .setTargetResolution(targetResolution)
//            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
//            .build()
//
//    }
//
//    private fun initViewPort() {
//        viewPort = ViewPort.Builder(rational, ROTATION_0).build()
//    }
//
//    private fun initUseCaseGroup() {
//        useCaseGroup = UseCaseGroup.Builder()
//            .addUseCase(preview())
//            .addUseCase(imageAnalysis)
//            .addUseCase(imageCapture)
//            .setViewPort(viewPort)
//            .build()
//    }
//
//    private fun initImageCapture() {
//        imageCapture = ImageCapture.Builder()
//            .setFlashMode(FLASH_MODE_OFF)
//            .setTargetAspectRatio(RATIO_16_9)
//            .build()
//    }
//
//    private fun startCamera() {
//        processCameraProvider().addListener({
//            // Used to bind the lifecycle of cameras to the lifecycle owner
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//            val previewProvider: ProcessCameraProvider = processCameraProvider().get()
//            try {
//                // Unbind use cases before rebinding
//                previewProvider.unbindAll()
//                // Bind use cases to camera
//                previewProvider.bindToLifecycle(
//                    viewLifecycleOwner, cameraSelector, useCaseGroup
//                )
//            } catch (exc: Exception) {
//                Log.e(javaClass.name, "Use case binding failed", exc)
//            }
//        }, ContextCompat.getMainExecutor(requireContext()))
//    }
//
//
//    private fun preview(): Preview = Preview.Builder()
//        .build()
//        .also {
//            it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
//        }
//

//
//    private fun takePhoto() {
//        // Get a stable reference of the modifiable image capture use case
//        imageCapture.takePicture(
//            outputFileOptions(),
//            ContextCompat.getMainExecutor(requireContext()),
//            onImageCapture
//        )
//    }
//
//

//
//    private fun contentValue(): ContentValues = ContentValues().apply {
//        put(MediaStore.MediaColumns.DISPLAY_NAME, imageName())
//        put(MediaStore.MediaColumns.MIME_TYPE, imageType())
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
//            put(MediaStore.Images.Media.RELATIVE_PATH, Constants.mocaImagesPath)
//        }
//    }
//
//    private fun outputFileOptions(): ImageCapture.OutputFileOptions =
//        ImageCapture.OutputFileOptions.Builder(
//            requireContext().contentResolver,
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            contentValue()
//        ).build()
//
//    private val onImageCapture: ImageCapture.OnImageSavedCallback =
//        object : ImageCapture.OnImageSavedCallback {
//            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                val msg = "Photo capture succeeded: ${outputFileResults.savedUri}"
//                outputFileResults.savedUri?.let { uri ->
//                    pikItModule.init(uri) { path->
//                        bitmapModule.init(path).cropImage(metrics.widthPixels, metrics.heightPixels,binding.cameraPreviewCapture.m)
//                    }
//                }
//
//                Log.d(javaClass.name, msg)
//            }
//
//            override fun onError(exception: ImageCaptureException) {
//                exception.message?.let {
//                    alertModule.showSnakeBar(requireActivity().window.decorView as ViewGroup, it)
//                    Log.e(javaClass.name, "Photo capture failed: $it", exception)
//                }
//            }
//
//        }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        cameraExecutor.shutdown()
//    }


}