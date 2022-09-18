// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.fragmentModule;

import android.content.Context;
import androidx.fragment.app.Fragment;
import com.technopolitan.mocaspaces.data.home.HomeViewPagerAdapter;
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
public final class HomeFragmentModule_ProvideHomeViewPagerAdapterFactory implements Factory<HomeViewPagerAdapter> {
  private final HomeFragmentModule module;

  private final Provider<Context> contextProvider;

  private final Provider<Fragment> fragmentProvider;

  public HomeFragmentModule_ProvideHomeViewPagerAdapterFactory(HomeFragmentModule module,
      Provider<Context> contextProvider, Provider<Fragment> fragmentProvider) {
    this.module = module;
    this.contextProvider = contextProvider;
    this.fragmentProvider = fragmentProvider;
  }

  @Override
  public HomeViewPagerAdapter get() {
    return provideHomeViewPagerAdapter(module, contextProvider.get(), fragmentProvider.get());
  }

  public static HomeFragmentModule_ProvideHomeViewPagerAdapterFactory create(
      HomeFragmentModule module, Provider<Context> contextProvider,
      Provider<Fragment> fragmentProvider) {
    return new HomeFragmentModule_ProvideHomeViewPagerAdapterFactory(module, contextProvider, fragmentProvider);
  }

  public static HomeViewPagerAdapter provideHomeViewPagerAdapter(HomeFragmentModule instance,
      Context context, Fragment fragment) {
    return Preconditions.checkNotNullFromProvides(instance.provideHomeViewPagerAdapter(context, fragment));
  }
}
