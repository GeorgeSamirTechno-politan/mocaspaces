package com.technopolitan.mocaspaces.ui.fragmentUtilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentCustomCameraXBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.*
import javax.inject.Inject


class CustomCameraXFragment : Fragment() {

    @Inject
    lateinit var permissionModule: PermissionModule

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var imagePickerModule: ImagePickerModule

    @Inject
    lateinit var pikItModule: PikItModule

    @Inject
    lateinit var cameraXModule: CameraXModule

    private lateinit var binding: FragmentCustomCameraXBinding
    private var isFront: Boolean = true

    private val startForImagePickerResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                pikItModule.init(fileUri) {
                    handleImage(it)
                }
            }
        }


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
        getArgument()
        initView()
        initPermissions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraXModule.destroy()
    }

    private fun initView() {
        binding.cameraXAppBar.appToolBar.title = getString(R.string.scan)
        binding.cameraXAppBar.appToolBar.setNavigationOnClickListener {
            navigationModule.popBack()
        }
        binding.addManuallyBtn.setOnClickListener {
            imagePickerModule.init(startForImagePickerResult) {
                handleImage(it)
            }
        }
    }

    private fun initPermissions() {
        val permissionList = mutableListOf<String>()
        permissionList.add(android.Manifest.permission.CAMERA)
        permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        permissionList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionModule.init(permissionList) {
            cameraXModule.init(
                binding.cameraPreview,
                viewLifecycleOwner,
                binding.scanImageView,
                binding.cameraPreviewCapture
            ) {
                handleImage(it)
            }
        }
    }

    private fun handleImage(path: String) {
        Log.d(javaClass.name, "handleImage: $path")
        navigationModule.savedStateHandler(R.id.action_student_verification_to_camera_x)
            ?.set(if (isFront) AppKeys.FrontCardPath.name else AppKeys.BackCardPath.name, path)
        navigationModule.popBack()
    }

    private fun getArgument() {
        arguments?.let {
            if (it.containsKey(AppKeys.FrontCardPath.name))
                isFront = it.getBoolean(AppKeys.FrontCardPath.name)
        }
    }


}


