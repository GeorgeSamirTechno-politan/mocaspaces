package com.technopolitan.mocaspaces.ui.fragmentUtilities

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentSingleButtonDialogBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.NavigationModule
import javax.inject.Inject

class SingleButtonDialogFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var navigationModule: NavigationModule

    private lateinit var binding: FragmentSingleButtonDialogBinding

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory()
            .buildDi(context, fragment = this, activity = requireActivity()).inject(this)
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { setupBottomSheet(it) }
        return dialog
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet
        )
            ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentSingleButtonDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        getDataFromArgument()
        binding.singleButton.setOnClickListener {
            popBack()
        }
    }

    private fun popBack() {
        navigationModule.savedStateHandler(R.id.single_button_dialog)
            ?.set(AppKeys.Message.name, true)
        navigationModule.popBack()
    }

    private fun getDataFromArgument() {
        arguments?.let {
            if (it.getString(AppKeys.PositiveBtnText.name) != null)
                binding.singleButton.text =
                    it.getString(AppKeys.PositiveBtnText.name)
            if (it.getString(AppKeys.Message.name) != null)
                binding.singleMessageTextView.text = it.getString(AppKeys.Message.name)

        }

    }
}