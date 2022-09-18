// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.modules;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;
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
public final class PikItModule_Factory implements Factory<PikItModule> {
  private final Provider<Fragment> fragmentProvider;

  private final Provider<Context> contextProvider;

  private final Provider<Activity> activityProvider;

  public PikItModule_Factory(Provider<Fragment> fragmentProvider, Provider<Context> contextProvider,
      Provider<Activity> activityProvider) {
    this.fragmentProvider = fragmentProvider;
    this.contextProvider = contextProvider;
    this.activityProvider = activityProvider;
  }

  @Override
  public PikItModule get() {
    return newInstance(fragmentProvider.get(), contextProvider.get(), activityProvider.get());
  }

  public static PikItModule_Factory create(Provider<Fragment> fragmentProvider,
      Provider<Context> contextProvider, Provider<Activity> activityProvider) {
    return new PikItModule_Factory(fragmentProvider, contextProvider, activityProvider);
  }

  public static PikItModule newInstance(Fragment fragment, Context context, Activity activity) {
    return new PikItModule(fragment, context, activity);
  }
}
