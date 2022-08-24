package com.technopolitan.mocaspaces.data.mobileOTP

import com.technopolitan.mocaspaces.modules.NetworkModule
import dagger.Module
import javax.inject.Inject

@Module
class MobileOTPRemote @Inject constructor(private var networkModule: NetworkModule)