package com.technopolitan.mocaspaces.data.personalInfo

import android.content.Context
import android.graphics.Bitmap
import android.util.Patterns
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.DropDownMapper
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.memberType.MemberTypeAdapter
import com.technopolitan.mocaspaces.data.shared.CountDownModule
import com.technopolitan.mocaspaces.databinding.FragmentPersonalInfoBinding
import com.technopolitan.mocaspaces.modules.*
import dagger.Module
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

@Module
class PersonalInfoDataModule @Inject constructor(
    private var context: Context,
    private var rxModule: RXModule,
    private var pixModule: PixModule,
    private var validationModule: ValidationModule,
    private var countDownModule: CountDownModule,
    private var glideModule: GlideModule,
    private var memberTypeAdapter: MemberTypeAdapter,
    private var dialogModule: DialogModule,
    private var dateTimeModule: DateTimeModule,
    private var navigationModule: NavigationModule,
    private var fragment: Fragment?
) {

    private lateinit var binding: FragmentPersonalInfoBinding
    private var startAnime: Boolean = true
    private var stopAnime: Boolean = false
    private lateinit var startAnimation: Animation
    private lateinit var endAnimation: Animation
    private lateinit var countryMapper: CountryMapper
    private lateinit var mobileNumber: String
    private lateinit var createAccountCallBack: (entity: Boolean) -> Unit
    private lateinit var activityResultLauncher: ActivityResultLauncher<String>

    /// PUBLISHERS
    private var memberTypeItemPublisher: PublishSubject<DropDownMapper> = PublishSubject.create()
    private var imagePublisher: PublishSubject<Bitmap> = PublishSubject.create()
    private var genderPublisher: PublishSubject<String> = PublishSubject.create()
    private var dateOfBirthPublisher: PublishSubject<String> = PublishSubject.create()

    /// OBSERVABLE
    private lateinit var firstNameObservable: Observable<String>
    private lateinit var lastNameObservable: Observable<String>
    private lateinit var emailObservable: Observable<String>
    private lateinit var jobTitleObservable: Observable<String>
    private lateinit var companyObservable: Observable<String>


    fun init(
        binding: FragmentPersonalInfoBinding,
        createAccountCallBack: (entity: Boolean) -> Unit,
        mobileNumber: String,
        countryMapper: CountryMapper,
        activityResultLauncher: ActivityResultLauncher<String>
    ) {
        this.binding = binding
        this.createAccountCallBack = createAccountCallBack
        this.countryMapper = countryMapper
        this.mobileNumber = mobileNumber
        this.activityResultLauncher = activityResultLauncher
        genderPublisher.onNext(context.getString(R.string.male))
        enableButton(false)
        initAnimation()
        clickOnImage()
        clickBirthDate()
        initClickOnGender()
        initMobileView()
        initImageObservable()
        initObservables()

    }

    private fun initAnimation() {
        startAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        endAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        countDownModule.init()
        startAnim()

    }

    private fun startAnim() {
        startAnime = true
        binding.roundedUserImageView.startAnimation(startAnimation)
        binding.cameraImageView.startAnimation(startAnimation)
        listenForAnimeCountDown()
    }

    private fun endAnime() {
        startAnime = false
        binding.roundedUserImageView.startAnimation(endAnimation)
        binding.cameraImageView.startAnimation(endAnimation)
        listenForAnimeCountDown()
    }

    private fun listenForAnimeCountDown() {
        countDownModule.startCount(3) {
            if (it) {
                if (!stopAnime) {
                    when (startAnime) {
                        true -> endAnime()
                        else -> startAnim()
                    }
                }
            }
        }
    }

    private fun initClickOnGender() {
        binding.maleTextView.setOnClickListener {
            genderPublisher.onNext(binding.maleTextView.text.toString())
        }
        binding.femaleTextView.setOnClickListener {
            genderPublisher.onNext(binding.femaleTextView.text.toString())
        }
    }

    private fun clickOnImage() {
        binding.roundedUserImageView.setOnClickListener {
            initPixModule()
        }
    }

    private fun initPixModule() {
        pixModule.init(callBack = {
            imagePublisher.onNext(it)
        }, activityResultLauncher = activityResultLauncher)

    }

    private fun clickBirthDate() {
        binding.dateOfBirthTextInputLayout.setOnClickListener {
            showDatePickerDialog()
        }
        binding.dateOfBirthEditText.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        dialogModule.showDatePickerDialog { year, month, day ->
            run {
                if (dateTimeModule.diffInDates(
                        Calendar.getInstance().time,
                        Date(year, month, day),
                        DateTimeUnits.Years
                    ) >= 16
                ) {
                    val dateString = "$year/$month$day"
                    binding.dateOfBirthEditText.setText(dateString)
                    dateOfBirthPublisher.onNext(dateString)
                    binding.dateOfBirthTextInputLayout.error = null
                } else {
                    binding.dateOfBirthTextInputLayout.error =
                        context.getString(R.string.birth_date_error_message)
                }
            }
        }
    }


    private fun initMobileView() {
        binding.mobileIncludePersonalInfo.mobileIncludeProgress.spinKit.visibility = View.GONE
        binding.mobileIncludePersonalInfo.countryDropDownLayout.visibility = View.VISIBLE
        glideModule.loadImage(countryMapper.url, binding.mobileIncludePersonalInfo.countryImageView)
        binding.mobileIncludePersonalInfo.countryCodeTextView.text = countryMapper.code
        binding.mobileIncludePersonalInfo.arrowDownCountryImageView.visibility = View.INVISIBLE
        mobileNumber = mobileNumber.replace(countryMapper.code, "")
        binding.mobileIncludePersonalInfo.mobileNumberEditText.setText(mobileNumber)
        binding.mobileIncludePersonalInfo.mobileNumberEditText.isEnabled = false
    }

    private fun initImageObservable() {
        imagePublisher.subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribe {
            stopAnime = true
            binding.roundedUserImageView.setImageBitmap(it)
            binding.roundedUserImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun initObservables() {
        firstNameObservable = initEmptyObservableWithValidation(binding.firstNameTextInputLayout)
        lastNameObservable = initEmptyObservableWithValidation(binding.lastNameTextInputLayout)
        initEmailObservable()
        initMemberTypeObservable()
        initGenderObservable()
        jobTitleObservable = initEmptyObservableWithValidation(binding.jobTitleTextInputLayout)
        companyObservable = initEmptyObservableWithValidation(binding.companyTextInputLayout)
        validateObservable()
    }

    fun initMemberTypeAdapter(list: List<DropDownMapper>) {
        binding.memberTypeRecycler.adapter = memberTypeAdapter
        memberTypeItemPublisher.onNext(list[0])
        memberTypeAdapter.setList(list).setClickCallBack {
            memberTypeItemPublisher.onNext(it)

        }
    }


    private fun initEmptyObservableWithValidation(textInputLayout: TextInputLayout): Observable<String> {
        val observable = rxModule.getTextWatcherObservable(textInputLayout.editText!!)
            .subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            )
        observable.subscribe {
            validateEmpty(textInputLayout)
        }
        return observable
    }

    private fun initEmailObservable() {
        emailObservable =
            rxModule.getTextWatcherObservable(binding.emailEditText).subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
        emailObservable.subscribe {
            validateEmail(binding.emailTextInputLayout)
        }
    }

    private fun initMemberTypeObservable() {
        memberTypeItemPublisher.subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribe {
            when (it.id) {
                1 -> {
                    binding.jobTitleTextInputLayout.visibility = View.VISIBLE
                    binding.companyTextInputLayout.visibility = View.VISIBLE
                }
                2 -> {
                    binding.jobTitleTextInputLayout.visibility = View.VISIBLE
                    binding.companyTextInputLayout.visibility = View.GONE
                }
                3 -> {
                    binding.jobTitleTextInputLayout.visibility = View.GONE
                    binding.companyTextInputLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun initGenderObservable() {
        genderPublisher.subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe {
                when (it) {
                    context.getString(R.string.male) -> {
                        binding.maleTextView.setBackgroundColor(context.getColor(R.color.accent_color))
                        binding.femaleTextView.setBackgroundColor(context.getColor(R.color.dark_white_color))
                    }
                    else -> {
                        binding.maleTextView.setBackgroundColor(context.getColor(R.color.dark_white_color))
                        binding.femaleTextView.setBackgroundColor(context.getColor(R.color.accent_color))
                    }
                }
            }
    }

    private fun validateObservable() {
        Observable.combineLatest(
            firstNameObservable,
            lastNameObservable,
            emailObservable,
            memberTypeItemPublisher,
            jobTitleObservable,
            companyObservable,
            genderPublisher,
            dateOfBirthPublisher
        ) { _: String, _: String, _: String, memberType: DropDownMapper, _: String,
            _: String, _: String, _: String ->
            return@combineLatest validateAll(memberType.id)
        }.subscribe {
            enableButton(it)
            binding.createAccountButton.setOnClickListener {
                createAccountCallBack(true)
            }
        }
    }

    private fun validateAll(type: Int): Boolean {
        when (type) {
            1 -> {
                return validateEmpty(binding.firstNameTextInputLayout) && validateEmpty(binding.lastNameTextInputLayout)
                        && validateEmail(binding.emailTextInputLayout) && validateEmpty(binding.jobTitleTextInputLayout) &&
                        validateEmpty(binding.companyTextInputLayout)
                        && validateEmpty(binding.dateOfBirthTextInputLayout)
            }
            2 -> {
                return validateEmpty(binding.firstNameTextInputLayout) && validateEmpty(binding.lastNameTextInputLayout)
                        && validateEmail(binding.emailTextInputLayout) && validateEmpty(binding.jobTitleTextInputLayout)
                        && validateEmpty(binding.dateOfBirthTextInputLayout)
            }
            else -> {
                return validateEmpty(binding.firstNameTextInputLayout) && validateEmpty(binding.lastNameTextInputLayout)
                        && validateEmail(binding.emailTextInputLayout)
                        && validateEmpty(binding.dateOfBirthTextInputLayout)
            }
        }
    }

    private fun enableButton(enable: Boolean) {
        binding.createAccountButton.isEnabled = enable
        when (enable) {
            true -> {
                binding.createAccountButton.alpha = 1f
            }
            else -> {
                binding.createAccountButton.alpha = 0.5f
            }
        }

    }


    private fun validateEmpty(textInputLayout: TextInputLayout): Boolean =
        validationModule.validateEmpty(textInputLayout, context.getString(R.string.required), false)

    private fun validateEmail(textInputLayout: TextInputLayout): Boolean =
        validationModule.validateWithRegex(
            textInputLayout, Patterns.EMAIL_ADDRESS.pattern(), context.getString(
                R.string.email_required
            ), false
        )

//    fun updatePermissionResult(it: Boolean) {
//        pixModule.updatePermissionResult(it)
//    }
}