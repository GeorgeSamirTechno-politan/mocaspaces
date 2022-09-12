package com.technopolitan.mocaspaces.di.fragmentModule

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.memberType.MemberTypeAdapter
import com.technopolitan.mocaspaces.data.personalInfo.PersonalInfoDataModule
import com.technopolitan.mocaspaces.data.remote.MemberTypeRemote
import com.technopolitan.mocaspaces.data.shared.CountDownModule
import com.technopolitan.mocaspaces.modules.*
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class PersonalInfoFragmentModule @Inject constructor(private val networkModule: NetworkModule) {


    @Singleton
    @Provides
    fun provideMemberTypeAdapter(glideModule: GlideModule, context: Context): MemberTypeAdapter =
        MemberTypeAdapter(glideModule, context)

    @Singleton
    @Provides
    fun provideMemberTypeRemote(): MemberTypeRemote = MemberTypeRemote(networkModule)


    @Singleton
    @Provides
    fun providePersonalInfoDataModule(
        context: Context,
        rxModule: RXModule,
        pixModule: PixModule,
        validationModule: ValidationModule,
        countDownModule: CountDownModule,
        glideModule: GlideModule,
        memberTypeAdapter: MemberTypeAdapter,
        dialogModule: DialogModule,
        dateTimeModule: DateTimeModule,
        utilityModule: UtilityModule
    ): PersonalInfoDataModule = PersonalInfoDataModule(
        context, rxModule, pixModule, validationModule,
        countDownModule, glideModule, memberTypeAdapter, dialogModule, dateTimeModule,
        utilityModule
    )

    @Singleton
    @Provides
    fun provideFaceDetectionModule(
        dialogModule: DialogModule,
        context: Context,
        pixModule: PixModule,
        navigationModule: NavigationModule,
    ): FaceDetectionModule = FaceDetectionModule(
        dialogModule, context,
        pixModule,
        navigationModule
    )

    @Singleton
    @Provides
    fun providePixModule(
        permissionModule: PermissionModule,
        navigationModule: NavigationModule, activity: Activity,
        fragment: Fragment?,
        pikItModule: PikItModule,
    ): PixModule =
        PixModule(permissionModule, navigationModule, activity, fragment, pikItModule)
}