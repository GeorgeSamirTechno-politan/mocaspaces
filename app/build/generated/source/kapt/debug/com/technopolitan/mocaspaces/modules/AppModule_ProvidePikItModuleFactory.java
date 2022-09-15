// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.modules;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;
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
public final class AppModule_ProvidePikItModuleFactory implements Factory<PikItModule> {
  private final AppModule module;

  private final Provider<Context> contextProvider;

  private final Provider<Activity> activityProvider;

  private final Provider<Fragment> fragmentProvider;

  public AppModule_ProvidePikItModuleFactory(AppModule module, Provider<Context> contextProvider,
      Provider<Activity> activityProvider, Provider<Fragment> fragmentProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
    this.activityProvider = activityProvider;
    this.fragmentProvider = fragmentProvider;
  }

  @Override
  public PikItModule get() {
    return providePikItModule(module, contextProvider.get(), activityProvider.get(), fragmentProvider.get());
  }

  public static AppModule_ProvidePikItModuleFactory create(AppModule module,
      Provider<Context> contextProvider, Provider<Activity> activityProvider,
      Provider<Fragment> fragmentProvider) {
    return new AppModule_ProvidePikItModuleFactory(module, contextProvider, activityProvider, fragmentProvider);
  }

  public static PikItModule providePikItModule(AppModule instance, Context context,
      Activity activity, Fragment fragment) {
    return Preconditions.checkNotNullFromProvides(instance.providePikItModule(context, activity, fragment));
  }
}