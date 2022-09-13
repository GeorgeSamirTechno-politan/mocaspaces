package com.technopolitan.mocaspaces.ui.resetPasswordOtp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.technopolitan.mocaspaces.R

class ResetPasswordOtpFragment : Fragment() {

    companion object {
        fun newInstance() = ResetPasswordOtpFragment()
    }

    private lateinit var viewModel: ResetPasswordOtpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reset_password_otp, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ResetPasswordOtpViewModel::class.java)
        // TODO: Use the ViewModel
    }

}