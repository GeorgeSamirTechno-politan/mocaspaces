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
public final class AppModule_ProvideSpannableStringModuleFactory implements Factory<SpannableStringModule> {
  private final AppModule module;

  private final Provider<Context> contextProvider;

  public AppModule_ProvideSpannableStringModuleFactory(AppModule module,
      Provider<Context> contextProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
  }

  @Override
  public SpannableStringModule get() {
    return provideSpannableStringModule(module, contextProvider.get());
  }

  public static AppModule_ProvideSpannableStringModuleFactory create(AppModule module,
      Provider<Context> contextProvider) {
    return new AppModule_ProvideSpannableStringModuleFactory(module, contextProvider);
  }

  public static SpannableStringModule provideSpannableStringModule(AppModule instance,
      Context context) {
    return Preconditions.checkNotNullFromProvides(instance.provideSpannableStringModule(context));
  }
}
