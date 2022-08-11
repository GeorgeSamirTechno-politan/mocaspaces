package com.technopolitan.mocaspaces.ui.fragmentUtilities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentTwoChooseDialogBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.NavigationModule
import javax.inject.Inject

class TwoChooseDialogFragment : BottomSheetDialogFragment() {

    private lateinit var twoChooseDialogBinding: FragmentTwoChooseDialogBinding

    @Inject
    lateinit var navigationModule: NavigationModule

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory()
            .buildDi(context, fragment = this, activity = requireActivity()).inject(this)
        super.onAttach(context)
    }

//    override fun onStart() {
//        super.onStart()
//        val dialog: Dialog? = dialog
//        if (dialog != null) {
//            val width = ViewGroup.LayoutParams.MATCH_PARENT
//            val height = ViewGroup.LayoutParams.WRAP_CONTENT
//            dialog.window?.setLayout(width, height)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        if (dialog != null && dialog?.window != null) {
//            dialog?.window?.setBackgroundDrawable(ColorDrawable(requireContext().getColor(android.R.color.transparent)))
//            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
//        }
        twoChooseDialogBinding =
            FragmentTwoChooseDialogBinding.inflate(layoutInflater, container, false)
        return twoChooseDialogBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        getDataFromArgument()
        twoChooseDialogBinding.positiveChooseBtn.setOnClickListener {
            popBack(true)
        }
        twoChooseDialogBinding.negativeChooseBtn.setOnClickListener {
            popBack(false)
        }
    }

    private fun popBack(value: Boolean) {
        navigationModule.savedStateHandler(R.id.two_choose_dialog)?.set(AppKeys.Message.name, value)
        navigationModule.popBack()
    }

    private fun getDataFromArgument() {
        arguments?.let {
            if (it.getString(AppKeys.PositiveBtnText.name) != null)
                twoChooseDialogBinding.positiveChooseBtn.text =
                    it.getString(AppKeys.PositiveBtnText.name)
            if (it.getString(AppKeys.NegativeBtnText.name) != null)
                twoChooseDialogBinding.negativeChooseBtn.text =
                    it.getString(AppKeys.NegativeBtnText.name)
            if (it.getString(AppKeys.Message.name) != null)
                twoChooseDialogBinding.messageTextView.text = it.getString(AppKeys.Message.name)
            if (it.getString(AppKeys.HeaderMessage.name) != null)
                twoChooseDialogBinding.headerDialogText.text =
                    it.getString(AppKeys.HeaderMessage.name)
            else twoChooseDialogBinding.headerDialogText.text = getString(R.string.app_name)
            if (it.getBoolean(AppKeys.SingleClick.name))
                twoChooseDialogBinding.negativeChooseBtn.visibility = View.GONE
        }

    }
}

