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
public final class SmsVerifyCatcherModule_Factory implements Factory<SmsVerifyCatcherModule> {
  private final Provider<Activity> activityProvider;

  private final Provider<Context> contextProvider;

  private final Provider<Fragment> fragmentProvider;

  public SmsVerifyCatcherModule_Factory(Provider<Activity> activityProvider,
      Provider<Context> contextProvider, Provider<Fragment> fragmentProvider) {
    this.activityProvider = activityProvider;
    this.contextProvider = contextProvider;
    this.fragmentProvider = fragmentProvider;
  }

  @Override
  public SmsVerifyCatcherModule get() {
    return newInstance(activityProvider.get(), contextProvider.get(), fragmentProvider.get());
  }

  public static SmsVerifyCatcherModule_Factory create(Provider<Activity> activityProvider,
      Provider<Context> contextProvider, Provider<Fragment> fragmentProvider) {
    return new SmsVerifyCatcherModule_Factory(activityProvider, contextProvider, fragmentProvider);
  }

  public static SmsVerifyCatcherModule newInstance(Activity activity, Context context,
      Fragment fragment) {
    return new SmsVerifyCatcherModule(activity, context, fragment);
  }
}