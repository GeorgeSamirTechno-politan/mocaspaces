// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.fragmentModule;

import android.app.Activity;
import com.technopolitan.mocaspaces.data.home.PriceViewPagerAdapter;
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
public final class HomeFragmentModule_ProvidePriceViewPagerFactory implements Factory<PriceViewPagerAdapter> {
  private final HomeFragmentModule module;

  private final Provider<Activity> activityProvider;

  public HomeFragmentModule_ProvidePriceViewPagerFactory(HomeFragmentModule module,
      Provider<Activity> activityProvider) {
    this.module = module;
    this.activityProvider = activityProvider;
  }

  @Override
  public PriceViewPagerAdapter get() {
    return providePriceViewPager(module, activityProvider.get());
  }

  public static HomeFragmentModule_ProvidePriceViewPagerFactory create(HomeFragmentModule module,
      Provider<Activity> activityProvider) {
    return new HomeFragmentModule_ProvidePriceViewPagerFactory(module, activityProvider);
  }

  public static PriceViewPagerAdapter providePriceViewPager(HomeFragmentModule instance,
      Activity activity) {
    return Preconditions.checkNotNullFromProvides(instance.providePriceViewPager(activity));
  }
}