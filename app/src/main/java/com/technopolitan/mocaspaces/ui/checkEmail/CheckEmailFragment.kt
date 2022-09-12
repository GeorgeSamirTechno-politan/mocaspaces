package com.technopolitan.mocaspaces.ui.checkEmail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.R

class CheckEmailFragment : Fragment() {

    companion object {
        fun newInstance() = CheckEmailFragment()
    }

    private lateinit var viewModel: CheckEmailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CheckEmailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}