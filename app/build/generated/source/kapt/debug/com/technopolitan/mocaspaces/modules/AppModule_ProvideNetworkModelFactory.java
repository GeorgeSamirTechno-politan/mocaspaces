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
public final class AppModule_ProvideNetworkModelFactory implements Factory<NetworkModule> {
  private final AppModule module;

  private final Provider<Context> contextProvider;

  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<SharedPrefModule> sharedPrefModuleProvider;

  public AppModule_ProvideNetworkModelFactory(AppModule module, Provider<Context> contextProvider,
      Provider<OkHttpClient> okHttpClientProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
    this.okHttpClientProvider = okHttpClientProvider;
    this.sharedPrefModuleProvider = sharedPrefModuleProvider;
  }

  @Override
  public NetworkModule get() {
    return provideNetworkModel(module, contextProvider.get(), okHttpClientProvider.get(), sharedPrefModuleProvider.get());
  }

  public static AppModule_ProvideNetworkModelFactory create(AppModule module,
      Provider<Context> contextProvider, Provider<OkHttpClient> okHttpClientProvider,
      Provider<SharedPrefModule> sharedPrefModuleProvider) {
    return new AppModule_ProvideNetworkModelFactory(module, contextProvider, okHttpClientProvider, sharedPrefModuleProvider);
  }

  public static NetworkModule provideNetworkModel(AppModule instance, Context context,
      OkHttpClient okHttpClient, SharedPrefModule sharedPrefModule) {
    return Preconditions.checkNotNullFromProvides(instance.provideNetworkModel(context, okHttpClient, sharedPrefModule));
  }
}
