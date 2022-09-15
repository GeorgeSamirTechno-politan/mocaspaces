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
public final class AppModule_ProvideAppDefaultModuleFactory implements Factory<AppDefaultModule> {
  private final AppModule module;

  private final Provider<Context> contextProvider;

  public AppModule_ProvideAppDefaultModuleFactory(AppModule module,
      Provider<Context> contextProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
  }

  @Override
  public AppDefaultModule get() {
    return provideAppDefaultModule(module, contextProvider.get());
  }

  public static AppModule_ProvideAppDefaultModuleFactory create(AppModule module,
      Provider<Context> contextProvider) {
    return new AppModule_ProvideAppDefaultModuleFactory(module, contextProvider);
  }

  public static AppDefaultModule provideAppDefaultModule(AppModule instance, Context context) {
    return Preconditions.checkNotNullFromProvides(instance.provideAppDefaultModule(context));
  }
}
