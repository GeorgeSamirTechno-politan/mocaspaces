// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.fragmentModule;

import com.technopolitan.mocaspaces.modules.NetworkModule;
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
public final class ForgetPasswordMobileFragmentModule_Factory implements Factory<ForgetPasswordMobileFragmentModule> {
  private final Provider<NetworkModule> networkModuleProvider;

  public ForgetPasswordMobileFragmentModule_Factory(Provider<NetworkModule> networkModuleProvider) {
    this.networkModuleProvider = networkModuleProvider;
  }

  @Override
  public ForgetPasswordMobileFragmentModule get() {
    return newInstance(networkModuleProvider.get());
  }

  public static ForgetPasswordMobileFragmentModule_Factory create(
      Provider<NetworkModule> networkModuleProvider) {
    return new ForgetPasswordMobileFragmentModule_Factory(networkModuleProvider);
  }

  public static ForgetPasswordMobileFragmentModule newInstance(NetworkModule networkModule) {
    return new ForgetPasswordMobileFragmentModule(networkModule);
  }
}