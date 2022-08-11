package com.technopolitan.mocaspaces.ui.start

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentStartBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.NavigationModule
import javax.inject.Inject

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    @Inject
    lateinit var navigationModule: NavigationModule

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

        }
    }

    private fun onClickOnSignIn() {
        binding.signInBtn.setOnClickListener {
            navigationModule.navigateTo(R.id.action_start_to_login)
        }
    }


}