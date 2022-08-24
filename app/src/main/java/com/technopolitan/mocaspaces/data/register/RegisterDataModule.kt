package com.technopolitan.mocaspaces.data.register

import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.modules.RXModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.modules.ValidationModule
import dagger.Module
import javax.inject.Inject

@Module
class RegisterDataModule @Inject constructor(
    private var context: Context,
    private var validationModule: ValidationModule,
    private var rxModule: RXModule,
    private var sharedPrefModule: SharedPrefModule,
    private var fragment: Fragment?
)