// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.modules;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppModule_ProvideConnectionStateLiveDataModuleFactory implements Factory<ConnectionLiveDataModule> {
  private final AppModule module;

  private final Provider<Context> contextProvider;

  private final Provider<NetworkModule> networkModuleProvider;

  public AppModule_ProvideConnectionStateLiveDataModuleFactory(AppModule module,
      Provider<Context> contextProvider, Provider<NetworkModule> networkModuleProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
    this.networkModuleProvider = networkModuleProvider;
  }

  @Override
  public ConnectionLiveDataModule get() {
    return provideConnectionStateLiveDataModule(module, contextProvider.get(), networkModuleProvider.get());
  }

  public static AppModule_ProvideConnectionStateLiveDataModuleFactory create(AppModule module,
      Provider<Context> contextProvider, Provider<NetworkModule> networkModuleProvider) {
    return new AppModule_ProvideConnectionStateLiveDataModuleFactory(module, contextProvider, networkModuleProvider);
  }

  public static ConnectionLiveDataModule provideConnectionStateLiveDataModule(AppModule instance,
      Context context, NetworkModule networkModule) {
    return Preconditions.checkNotNullFromProvides(instance.provideConnectionStateLiveDataModule(context, networkModule));
  }
}