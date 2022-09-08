package com.technopolitan.mocaspaces.ui.start

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.shared.OtpBlockUserModule
import com.technopolitan.mocaspaces.databinding.FragmentStartBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.modules.UtilityModule
import javax.inject.Inject

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var appUtilityModule: UtilityModule

    @Inject
    lateinit var blockUserModule: OtpBlockUserModule

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory()
            .buildDi(context, fragment = this, activity = requireActivity()).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickOnSignIn()
        onClickOnSignUp()
    }

    private fun onClickOnSignUp() {
        binding.signUpBtn.setOnClickListener {
            if (blockUserModule.canAuthenticate(1))
                navigationModule.navigateTo(R.id.action_start_to_register)
            else blockUserModule.showBlockedDialog()
        }
    }

    private fun onClickOnSignIn() {
        binding.signInBtn.setOnClickListener {
            navigationModule.navigateTo(R.id.action_start_to_login)
        }
    }


}