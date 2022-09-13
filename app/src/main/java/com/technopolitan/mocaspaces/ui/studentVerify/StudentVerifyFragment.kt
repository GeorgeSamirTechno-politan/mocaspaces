package com.technopolitan.mocaspaces.ui.studentVerify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentStudentVerifyBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.register.RegisterViewModel
import javax.inject.Inject

class StudentVerifyFragment : Fragment() {

    private lateinit var binding: FragmentStudentVerifyBinding

    @Inject
    lateinit var registerViewModel: RegisterViewModel

    @Inject
    lateinit var viewModel: StudentVerifyViewModel

    @Inject
    lateinit var navigationModule: NavigationModule


    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentVerifyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataModule()
        clickOnSkip()
    }

    private fun initDataModule() {
        viewModel.init(
            binding,
            viewLifecycleOwner,
            registerViewModel.getRegisterRequestMapper()
        ) { frontPath, backPath, expiryDate ->
            registerViewModel.getRegisterRequestMapper().studentFrontCardPath = frontPath
            registerViewModel.getRegisterRequestMapper().studentBackCardPath = backPath
            registerViewModel.getRegisterRequestMapper().studentCardExpiryDate = expiryDate
            navigate()
        }
    }

    private fun clickOnSkip() {
        binding.skipTextView.setOnClickListener {
            navigate()
        }
    }

    private fun navigate() {
        navigationModule.navigateTo(R.id.action_student_verification_to_check_email)
    }


}