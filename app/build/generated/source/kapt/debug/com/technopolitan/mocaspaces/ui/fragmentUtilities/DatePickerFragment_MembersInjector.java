// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.ui.fragmentUtilities;

import com.technopolitan.mocaspaces.modules.DateTimeModule;
import com.technopolitan.mocaspaces.modules.NavigationModule;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DatePickerFragment_MembersInjector implements MembersInjector<DatePickerFragment> {
  private final Provider<NavigationModule> navigationModuleProvider;

  private final Provider<DateTimeModule> dateTimeModuleProvider;

  public DatePickerFragment_MembersInjector(Provider<NavigationModule> navigationModuleProvider,
      Provider<DateTimeModule> dateTimeModuleProvider) {
    this.navigationModuleProvider = navigationModuleProvider;
    this.dateTimeModuleProvider = dateTimeModuleProvider;
  }

  public static MembersInjector<DatePickerFragment> create(
      Provider<NavigationModule> navigationModuleProvider,
      Provider<DateTimeModule> dateTimeModuleProvider) {
    return new DatePickerFragment_MembersInjector(navigationModuleProvider, dateTimeModuleProvider);
  }

  @Override
  public void injectMembers(DatePickerFragment instance) {
    injectNavigationModule(instance, navigationModuleProvider.get());
    injectDateTimeModule(instance, dateTimeModuleProvider.get());
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.fragmentUtilities.DatePickerFragment.navigationModule")
  public static void injectNavigationModule(DatePickerFragment instance,
      NavigationModule navigationModule) {
    instance.navigationModule = navigationModule;
  }

  @InjectedFieldSignature("com.technopolitan.mocaspaces.ui.fragmentUtilities.DatePickerFragment.dateTimeModule")
  public static void injectDateTimeModule(DatePickerFragment instance,
      DateTimeModule dateTimeModule) {
    instance.dateTimeModule = dateTimeModule;
  }
}