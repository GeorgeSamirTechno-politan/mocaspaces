// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.remote;

import com.technopolitan.mocaspaces.modules.NetworkModule;
import com.technopolitan.mocaspaces.modules.SharedPrefModule;
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
public final class LoginRemote_Factory implements Factory<LoginRemote> {
  private final Provider<NetworkModule> networkModelProvider;

  private final Provider<SharedPrefModule> sharedPrefModuleProvider;

  public LoginRemote_Factory(Provider<NetworkModule> networkModelProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider) {
    this.networkModelProvider = networkModelProvider;
    this.sharedPrefModuleProvider = sharedPrefModuleProvider;
  }

  @Override
  public LoginRemote get() {
    return newInstance(networkModelProvider.get(), sharedPrefModuleProvider.get());
  }

  public static LoginRemote_Factory create(Provider<NetworkModule> networkModelProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider) {
    return new LoginRemote_Factory(networkModelProvider, sharedPrefModuleProvider);
  }

  public static LoginRemote newInstance(NetworkModule networkModel,
      SharedPrefModule sharedPrefModule) {
    return new LoginRemote(networkModel, sharedPrefModule);
  }
}