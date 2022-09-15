// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.fragmentModule;

import com.technopolitan.mocaspaces.data.remote.CountryRemote;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class CheckMobileFragmentModule_ProvideCountryRemoteFactory implements Factory<CountryRemote> {
  private final CheckMobileFragmentModule module;

  public CheckMobileFragmentModule_ProvideCountryRemoteFactory(CheckMobileFragmentModule module) {
    this.module = module;
  }

  @Override
  public CountryRemote get() {
    return provideCountryRemote(module);
  }

  public static CheckMobileFragmentModule_ProvideCountryRemoteFactory create(
      CheckMobileFragmentModule module) {
    return new CheckMobileFragmentModule_ProvideCountryRemoteFactory(module);
  }

  public static CountryRemote provideCountryRemote(CheckMobileFragmentModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideCountryRemote());
  }
}