// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.bookingShared;

import android.content.Context;
import com.technopolitan.mocaspaces.modules.DateTimeModule;
import com.technopolitan.mocaspaces.modules.SpannableStringModule;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MonthDayDataModule_Factory implements Factory<MonthDayDataModule> {
  private final Provider<Context> contextProvider;

  private final Provider<SpannableStringModule> spannableStringModuleProvider;

  private final Provider<DateTimeModule> dateTimeModuleProvider;

  public MonthDayDataModule_Factory(Provider<Context> contextProvider,
      Provider<SpannableStringModule> spannableStringModuleProvider,
      Provider<DateTimeModule> dateTimeModuleProvider) {
    this.contextProvider = contextProvider;
    this.spannableStringModuleProvider = spannableStringModuleProvider;
    this.dateTimeModuleProvider = dateTimeModuleProvider;
  }

  @Override
  public MonthDayDataModule get() {
    return newInstance(contextProvider.get(), spannableStringModuleProvider.get(), dateTimeModuleProvider.get());
  }

  public static MonthDayDataModule_Factory create(Provider<Context> contextProvider,
      Provider<SpannableStringModule> spannableStringModuleProvider,
      Provider<DateTimeModule> dateTimeModuleProvider) {
    return new MonthDayDataModule_Factory(contextProvider, spannableStringModuleProvider, dateTimeModuleProvider);
  }

  public static MonthDayDataModule newInstance(Context context,
      SpannableStringModule spannableStringModule, DateTimeModule dateTimeModule) {
    return new MonthDayDataModule(context, spannableStringModule, dateTimeModule);
  }
}