// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.fragmentModule;

import com.technopolitan.mocaspaces.data.remote.CheckMobileRemote;
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
public final class CheckMobileFragmentModule_ProvideCheckMobileRemoteFactory implements Factory<CheckMobileRemote> {
  private final CheckMobileFragmentModule module;

  public CheckMobileFragmentModule_ProvideCheckMobileRemoteFactory(
      CheckMobileFragmentModule module) {
    this.module = module;
  }

  @Override
  public CheckMobileRemote get() {
    return provideCheckMobileRemote(module);
  }

  public static CheckMobileFragmentModule_ProvideCheckMobileRemoteFactory create(
      CheckMobileFragmentModule module) {
    return new CheckMobileFragmentModule_ProvideCheckMobileRemoteFactory(module);
  }

  public static CheckMobileRemote provideCheckMobileRemote(CheckMobileFragmentModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideCheckMobileRemote());
  }
}