package com.technopolitan.mocaspaces.ui.forgetPasswordMobile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.technopolitan.mocaspaces.R

class ForgetPasswordMobileFragment : Fragment() {

    companion object {
        fun newInstance() = ForgetPasswordMobileFragment()
    }

    private lateinit var viewModel: ForgetPasswordMobileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forget_password_mobile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForgetPasswordMobileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}