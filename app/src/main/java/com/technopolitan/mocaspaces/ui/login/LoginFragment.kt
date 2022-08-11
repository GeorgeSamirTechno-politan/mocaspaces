package com.technopolitan.mocaspaces.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.databinding.FragmentLoginBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import javax.inject.Inject

class LoginFragment : Fragment() {
    @Inject
    lateinit var loginViewModel: LoginViewModel
    lateinit var fragmentLoginBinding: FragmentLoginBinding
    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory()
            .buildDi(context, fragment = this, activity = requireActivity()).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return fragmentLoginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickOnLogin()
    }

    private fun clickOnLogin() {
        fragmentLoginBinding.loginBtn.setOnClickListener {

        }
    }


}