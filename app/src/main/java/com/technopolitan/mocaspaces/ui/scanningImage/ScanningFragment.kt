package com.technopolitan.mocaspaces.ui.scanningImage

import android.R
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.technopolitan.mocaspaces.databinding.FragmentScanningBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.CompressModule
import com.technopolitan.mocaspaces.modules.FaceDetectionModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.modules.UtilityModule
import javax.inject.Inject


class ScanningFragment : DialogFragment() {

    private lateinit var binding: FragmentScanningBinding

    @Inject
    lateinit var faceDetectionModule: FaceDetectionModule

    @Inject
    lateinit var navigationModule: NavigationModule


    @Inject
    lateinit var compressModule: CompressModule

    @Inject
    lateinit var utilityModule: UtilityModule

    private lateinit var imagePath: String
    private lateinit var uri: Uri


    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(requireContext().getColor(R.color.transparent)))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        binding = FragmentScanningBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        getDataFromArguments()
        detectFaceAndCompressImage()
    }

    private fun detectFaceAndCompressImage() {
        faceDetectionModule.init(path = imagePath, uri, true) { uri: Uri, pathString: String ->
            compressModule.init(uri, path = pathString) {
                popBack(it)
            }
        }
    }

    private fun popBack(value: Bitmap) {
        navigationModule.savedStateHandler(com.technopolitan.mocaspaces.R.id.scanning_dialog)
            ?.set(AppKeys.Message.name, value)
        navigationModule.popBack()
    }

    private fun getDataFromArguments() {
        arguments?.let {
            if (it.containsKey(AppKeys.Message.name)) {
                imagePath = it.getString(AppKeys.Message.name)!!
                uri = it.getParcelable(AppKeys.ImageUri.name)!!
                binding.scanningImageView.setImageBitmap(getBitMap(imagePath))
            }
        }
    }


    private fun getBitMap(path: String): Bitmap =
        utilityModule.rotateBitmap(utilityModule.getBitmap(path), -90.0f)

}