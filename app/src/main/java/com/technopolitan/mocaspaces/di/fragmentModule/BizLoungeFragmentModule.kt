package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import com.technopolitan.mocaspaces.data.home.BizLoungeAdapter
import com.technopolitan.mocaspaces.modules.GlideModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BizLoungeFragmentModule {

    @Singleton
    @Provides
    fun provideBizLoungeAdapter(
        glideModule: GlideModule,
        context: Context,
        spannableStringModule: SpannableStringModule
    ): BizLoungeAdapter = BizLoungeAdapter(glideModule, context, spannableStringModule)
}