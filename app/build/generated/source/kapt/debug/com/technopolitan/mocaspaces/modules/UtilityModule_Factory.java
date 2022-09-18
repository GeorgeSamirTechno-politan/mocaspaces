// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.modules;

import android.app.Activity;
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
public final class UtilityModule_Factory implements Factory<UtilityModule> {
  private final Provider<Context> contextProvider;

  private final Provider<Activity> activityProvider;

  public UtilityModule_Factory(Provider<Context> contextProvider,
      Provider<Activity> activityProvider) {
    this.contextProvider = contextProvider;
    this.activityProvider = activityProvider;
  }

  @Override
  public UtilityModule get() {
    return newInstance(contextProvider.get(), activityProvider.get());
  }

  public static UtilityModule_Factory create(Provider<Context> contextProvider,
      Provider<Activity> activityProvider) {
    return new UtilityModule_Factory(contextProvider, activityProvider);
  }

  public static UtilityModule newInstance(Context context, Activity activity) {
    return new UtilityModule(context, activity);
  }
}
