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
public final class StartFragmentModule_Factory implements Factory<StartFragmentModule> {
  private final Provider<NetworkModule> networkModuleProvider;

  public StartFragmentModule_Factory(Provider<NetworkModule> networkModuleProvider) {
    this.networkModuleProvider = networkModuleProvider;
  }

  @Override
  public StartFragmentModule get() {
    return newInstance(networkModuleProvider.get());
  }

  public static StartFragmentModule_Factory create(Provider<NetworkModule> networkModuleProvider) {
    return new StartFragmentModule_Factory(networkModuleProvider);
  }

  public static StartFragmentModule newInstance(NetworkModule networkModule) {
    return new StartFragmentModule(networkModule);
  }
}
