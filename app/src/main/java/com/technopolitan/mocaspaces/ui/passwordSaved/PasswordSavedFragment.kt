package com.technopolitan.mocaspaces.ui.passwordSaved

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentPasswordSavedBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.NavigationModule
import javax.inject.Inject

class PasswordSavedFragment : Fragment() {

    private lateinit var binding: FragmentPasswordSavedBinding

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this).inject(this)
        super.onAttach(context)
    }

    @Inject
    lateinit var navigationModule: NavigationModule

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordSavedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goToLoginBtn.setOnClickListener{
            navigationModule.navigateTo(R.id.action_password_change_successfully_fragment_to_login_fragment)
        }
    }

}