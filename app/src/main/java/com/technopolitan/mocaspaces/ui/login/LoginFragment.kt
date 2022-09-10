package com.technopolitan.mocaspaces.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.login.LoginMapper
import com.technopolitan.mocaspaces.databinding.FragmentLoginBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.modules.ValidationModule
import javax.inject.Inject

class LoginFragment : Fragment() {
    @Inject
    lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var apiResponseModule: ApiResponseModule<LoginMapper>

    @Inject
    lateinit var sharedPrefModule: SharedPrefModule

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var validationModule: ValidationModule
    private lateinit var binding: FragmentLoginBinding
    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory()
            .buildDi(context, fragment = this, activity = requireActivity()).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        configLoginDataModule()
    }

    private fun initView(){
//        binding.forgetPasswordTextView.setOnClickListener{
//            navigationModule.navigateTo(R.id.action_login_to_custom_camera_x)
//        }
    }

    private fun configLoginDataModule() {
        loginViewModel.configLoginData(
            binding.emailTextInputLayout,
            binding.passwordTextInputLayout,
            binding.loginBtn,
            binding.biometricLoginLayout,
            binding.signUpText,
            binding.orSignInWithTextView,
        ) {
            handleLoginApi()
        }
    }

    private fun handleLoginApi() {
        loginViewModel.login(
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
        loginViewModel.handleLoginApiCall().observe(viewLifecycleOwner) {
            apiResponseModule.handleResponse(it, binding.loginBtn) {

            }
        }
    }


}