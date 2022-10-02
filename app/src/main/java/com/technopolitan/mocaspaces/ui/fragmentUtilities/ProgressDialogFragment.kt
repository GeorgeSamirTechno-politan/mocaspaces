package com.technopolitan.mocaspaces.ui.fragmentUtilities

import android.R
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.technopolitan.mocaspaces.databinding.FragmentProgressBinding


class ProgressDialogFragment : DialogFragment() {


    private lateinit var binding: FragmentProgressBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(requireContext().getColor(R.color.transparent)))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        binding = FragmentProgressBinding.inflate(inflater, container, false)
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


}