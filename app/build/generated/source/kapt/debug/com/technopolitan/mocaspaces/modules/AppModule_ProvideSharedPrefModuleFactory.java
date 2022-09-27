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
public final class AppModule_ProvideSharedPrefModuleFactory implements Factory<SharedPrefModule> {
  private final AppModule module;

  private final Provider<Context> contextProvider;

  public AppModule_ProvideSharedPrefModuleFactory(AppModule module,
      Provider<Context> contextProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
  }

  @Override
  public SharedPrefModule get() {
    return provideSharedPrefModule(module, contextProvider.get());
  }

  public static AppModule_ProvideSharedPrefModuleFactory create(AppModule module,
      Provider<Context> contextProvider) {
    return new AppModule_ProvideSharedPrefModuleFactory(module, contextProvider);
  }

  public static SharedPrefModule provideSharedPrefModule(AppModule instance, Context context) {
    return Preconditions.checkNotNullFromProvides(instance.provideSharedPrefModule(context));
  }
}