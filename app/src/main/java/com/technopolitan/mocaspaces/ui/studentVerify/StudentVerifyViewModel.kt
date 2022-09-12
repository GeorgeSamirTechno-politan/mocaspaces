package com.technopolitan.mocaspaces.ui.studentVerify

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper
import com.technopolitan.mocaspaces.data.studentVerify.StudentVerifyDataModule
import com.technopolitan.mocaspaces.databinding.FragmentStudentVerifyBinding
import javax.inject.Inject

class StudentVerifyViewModel @Inject constructor(private var studentVerifyDataModule: StudentVerifyDataModule) :
    ViewModel() {

    fun init(
        binding: FragmentStudentVerifyBinding,
        viewLifecycleOwner: LifecycleOwner,
        registerRequestMapper: RegisterRequestMapper,
        callBack: (frontPath: String, backPath: String, expiryDate: String) -> Unit
    ) {
        studentVerifyDataModule.init(binding, viewLifecycleOwner, registerRequestMapper,callBack)
    }

}