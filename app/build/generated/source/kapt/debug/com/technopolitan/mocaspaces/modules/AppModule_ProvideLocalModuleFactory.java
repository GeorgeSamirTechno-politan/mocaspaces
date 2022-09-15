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
public final class AppModule_ProvideLocalModuleFactory implements Factory<LocalModule> {
  private final AppModule module;

  private final Provider<Context> contextProvider;

  public AppModule_ProvideLocalModuleFactory(AppModule module, Provider<Context> contextProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
  }

  @Override
  public LocalModule get() {
    return provideLocalModule(module, contextProvider.get());
  }

  public static AppModule_ProvideLocalModuleFactory create(AppModule module,
      Provider<Context> contextProvider) {
    return new AppModule_ProvideLocalModuleFactory(module, contextProvider);
  }

  public static LocalModule provideLocalModule(AppModule instance, Context context) {
    return Preconditions.checkNotNullFromProvides(instance.provideLocalModule(context));
  }
}