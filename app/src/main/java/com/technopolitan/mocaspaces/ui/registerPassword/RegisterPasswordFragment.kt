package com.technopolitan.mocaspaces.ui.registerPassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.technopolitan.mocaspaces.R

class RegisterPasswordFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterPasswordFragment()
    }

    private lateinit var viewModel: RegisterPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterPasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}