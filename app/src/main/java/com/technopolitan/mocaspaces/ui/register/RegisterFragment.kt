package com.technopolitan.mocaspaces.ui.register


//import com.technopolitan.mocaspaces.di.AppViewModelFactory
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
import com.technopolitan.mocaspaces.modules.UtilityModule
import javax.inject.Inject

class RegisterFragment : Fragment() {


    @Inject
    lateinit var navigationModel: NavigationModule

    private lateinit var binding: FragmentRegisterBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory

//    @Inject
//    lateinit var providerFactory: ViewViewModelProviderFactory

    @Inject
    lateinit var utilityModule: UtilityModule

    @Inject
    lateinit var  registerViewModel: RegisterViewModel

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
        NavController.OnDestinationChangedListener { _, destination, _ ->
            run {
                when (destination.id) {
                    R.id.verify_mobile_fragment -> {
                        updateRegisterUiWhite()
                        updateView(
                            25,
                            R.string.sign_up,
                            View.VISIBLE,
                            null
                        )
                    }
                    R.id.mobile_otp_fragment -> {
                        updateRegisterUiWhite()
                        updateView(
                            50,
                            R.string.verify_mobile,
                            View.VISIBLE,
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.ic_mobile
                            )
                        )
                    }
                    R.id.personal_info_fragment -> {
                        updateRegisterUiWhite()
                        updateView(
                            75,
                            R.string.personal_info,
                            View.VISIBLE,
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.ic_person_round_icon
                            )
                        )
                    }
                    R.id.student_verification_fragment -> {
                        updateRegisterUiWhite()
                        updateView(
                            85,
                            R.string.verify_student,
                            View.VISIBLE,
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.ic_student_verification
                            )
                        )
                    }
                    R.id.check_email_fragment -> {
                        updateRegisterUiAcceent()
                        hideView()
                    }
                    R.id.fragment_password -> {
                        updateRegisterUiWhite()
                        updateView(
                            100,
                            R.string.password,
                            View.VISIBLE,
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.ic_password
                            )
                        )
                    }

                }
            }
        }

    private fun updateRegisterUiAcceent() {
        binding.root.setBackgroundColor(requireContext().getColor(R.color.accent_color))
        utilityModule.setStatusBar(R.color.accent_color)
    }

    private fun updateRegisterUiWhite() {
        binding.root.setBackgroundColor(requireContext().getColor(R.color.white))
        utilityModule.setStatusBar(R.color.white)
    }

    private fun updateView(progressCount: Int, textId: Int, visibility: Int, drawable: Drawable?) {
        binding.progressCountRegister.visibility = View.VISIBLE
        binding.stepImageView.visibility = View.VISIBLE
        binding.stepNameTextView.visibility = View.VISIBLE
        binding.mocaCheckMailImageView.visibility = View.VISIBLE
        binding.progressCountRegister.setProgressCompat(progressCount, true)
        binding.stepNameTextView.text = getString(textId)
        binding.stepImageView.visibility = visibility
        if (drawable == null)
            binding.stepImageView.setImageDrawable(null)
        else {
            binding.stepImageView.setImageDrawable(drawable)
            binding.stepImageView.setColorFilter(requireContext().getColor(R.color.accent_color))
        }
    }

    private fun hideView() {
        binding.progressCountRegister.visibility = View.GONE
        binding.stepImageView.visibility = View.GONE
        binding.stepNameTextView.visibility = View.GONE
        binding.mocaCheckMailImageView.visibility = View.GONE
    }


}