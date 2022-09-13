package com.technopolitan.mocaspaces.ui.fragmentUtilities

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentSuccessRegisterBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.NavigationModule
import javax.inject.Inject

class SuccessRegisterFragment : DialogFragment() {

    private lateinit var binding: FragmentSuccessRegisterBinding

    @Inject
    lateinit var navigationModule: NavigationModule
    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(requireContext().getColor(android.R.color.transparent)))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        binding = FragmentSuccessRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            navigationModule.navigateTo(
                R.id.action_register_to_login,
                navHostId = R.id.nav_host_fragment
            )
            dismiss()

        }, 3000)
    }

}