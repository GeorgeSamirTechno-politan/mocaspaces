package com.technopolitan.mocaspaces.ui.register


import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentRegisterBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.NavigationModule
import javax.inject.Inject

class RegisterFragment : Fragment() {


    @Inject
    lateinit var checkMobileViewModel: RegisterViewModel

    @Inject
    lateinit var navigationModel: NavigationModule
    private lateinit var binding: FragmentRegisterBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNavController()
    }

    private fun setUpNavController() {
        navHostFragment =
            childFragmentManager.findFragmentById(R.id.register_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(listener)
    }

    private val listener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            run {
                when (destination.id) {
                    R.id.verify_fragment -> updateView(25, R.string.sign_up, View.VISIBLE, null)
                    R.id.mobile_otp_fragment -> updateView(
                        50, R.string.verify_mobile, View.VISIBLE, AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_mobile
                        )
                    )
                }
            }
        }

    private fun updateView(progressCount: Int, textId: Int, visibility: Int, drawable: Drawable?) {
        binding.progressCountRegister.setProgressCompat(progressCount, true)
        binding.stepNameTextView.text = getString(textId)
        binding.stepImageView.visibility = visibility
        if (drawable == null)
            binding.stepImageView.setImageDrawable(null)
        else binding.stepImageView.setImageDrawable(drawable)
    }


}