// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.di.fragmentModule;

import android.content.Context;
import com.technopolitan.mocaspaces.data.home.AmenityAdapter;
import com.technopolitan.mocaspaces.data.home.PriceAdapter;
import com.technopolitan.mocaspaces.data.home.WorkSpaceAdapter;
import com.technopolitan.mocaspaces.modules.GlideModule;
import com.technopolitan.mocaspaces.modules.SpannableStringModule;
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
public final class HomeFragmentModule_ProvideWorkSpaceAdapterFactory implements Factory<WorkSpaceAdapter> {
  private final HomeFragmentModule module;

  private final Provider<GlideModule> glideModuleProvider;

  private final Provider<Context> contextProvider;

  private final Provider<AmenityAdapter> amenityAdapterProvider;

  private final Provider<SpannableStringModule> spannableStringModuleProvider;

  private final Provider<PriceAdapter> priceAdapterProvider;

  public HomeFragmentModule_ProvideWorkSpaceAdapterFactory(HomeFragmentModule module,
      Provider<GlideModule> glideModuleProvider, Provider<Context> contextProvider,
      Provider<AmenityAdapter> amenityAdapterProvider,
      Provider<SpannableStringModule> spannableStringModuleProvider,
      Provider<PriceAdapter> priceAdapterProvider) {
    this.module = module;
    this.glideModuleProvider = glideModuleProvider;
    this.contextProvider = contextProvider;
    this.amenityAdapterProvider = amenityAdapterProvider;
    this.spannableStringModuleProvider = spannableStringModuleProvider;
    this.priceAdapterProvider = priceAdapterProvider;
  }

  @Override
  public WorkSpaceAdapter get() {
    return provideWorkSpaceAdapter(module, glideModuleProvider.get(), contextProvider.get(), amenityAdapterProvider.get(), spannableStringModuleProvider.get(), priceAdapterProvider.get());
  }

  public static HomeFragmentModule_ProvideWorkSpaceAdapterFactory create(HomeFragmentModule module,
      Provider<GlideModule> glideModuleProvider, Provider<Context> contextProvider,
      Provider<AmenityAdapter> amenityAdapterProvider,
      Provider<SpannableStringModule> spannableStringModuleProvider,
      Provider<PriceAdapter> priceAdapterProvider) {
    return new HomeFragmentModule_ProvideWorkSpaceAdapterFactory(module, glideModuleProvider, contextProvider, amenityAdapterProvider, spannableStringModuleProvider, priceAdapterProvider);
  }

  public static WorkSpaceAdapter provideWorkSpaceAdapter(HomeFragmentModule instance,
      GlideModule glideModule, Context context, AmenityAdapter amenityAdapter,
      SpannableStringModule spannableStringModule, PriceAdapter priceAdapter) {
    return Preconditions.checkNotNullFromProvides(instance.provideWorkSpaceAdapter(glideModule, context, amenityAdapter, spannableStringModule, priceAdapter));
  }
}
