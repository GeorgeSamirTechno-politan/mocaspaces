// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.modules;

import android.content.Context;
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
public final class ConnectionLiveDataModule_Factory implements Factory<ConnectionLiveDataModule> {
  private final Provider<Context> contextProvider;

  private final Provider<NetworkModule> networkModuleProvider;

  public ConnectionLiveDataModule_Factory(Provider<Context> contextProvider,
      Provider<NetworkModule> networkModuleProvider) {
    this.contextProvider = contextProvider;
    this.networkModuleProvider = networkModuleProvider;
  }

  @Override
  public ConnectionLiveDataModule get() {
    return newInstance(contextProvider.get(), networkModuleProvider.get());
  }

  public static ConnectionLiveDataModule_Factory create(Provider<Context> contextProvider,
      Provider<NetworkModule> networkModuleProvider) {
    return new ConnectionLiveDataModule_Factory(contextProvider, networkModuleProvider);
  }

  public static ConnectionLiveDataModule newInstance(Context context, NetworkModule networkModule) {
    return new ConnectionLiveDataModule(context, networkModule);
  }
}
