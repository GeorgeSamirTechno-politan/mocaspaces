// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.home;

import android.content.Context;
import com.technopolitan.mocaspaces.modules.GlideModule;
import com.technopolitan.mocaspaces.modules.SpannableStringModule;
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
public final class WorkSpaceAdapter_Factory implements Factory<WorkSpaceAdapter> {
  private final Provider<GlideModule> glideModuleProvider;

  private final Provider<Context> contextProvider;

  private final Provider<AmenityAdapter> amenityAdapterProvider;

  private final Provider<SpannableStringModule> spannableStringModuleProvider;

  private final Provider<PriceAdapter> priceAdapterProvider;

  public WorkSpaceAdapter_Factory(Provider<GlideModule> glideModuleProvider,
      Provider<Context> contextProvider, Provider<AmenityAdapter> amenityAdapterProvider,
      Provider<SpannableStringModule> spannableStringModuleProvider,
      Provider<PriceAdapter> priceAdapterProvider) {
    this.glideModuleProvider = glideModuleProvider;
    this.contextProvider = contextProvider;
    this.amenityAdapterProvider = amenityAdapterProvider;
    this.spannableStringModuleProvider = spannableStringModuleProvider;
    this.priceAdapterProvider = priceAdapterProvider;
  }

  @Override
  public WorkSpaceAdapter get() {
    return newInstance(glideModuleProvider.get(), contextProvider.get(), amenityAdapterProvider.get(), spannableStringModuleProvider.get(), priceAdapterProvider.get());
  }

  public static WorkSpaceAdapter_Factory create(Provider<GlideModule> glideModuleProvider,
      Provider<Context> contextProvider, Provider<AmenityAdapter> amenityAdapterProvider,
      Provider<SpannableStringModule> spannableStringModuleProvider,
      Provider<PriceAdapter> priceAdapterProvider) {
    return new WorkSpaceAdapter_Factory(glideModuleProvider, contextProvider, amenityAdapterProvider, spannableStringModuleProvider, priceAdapterProvider);
  }

  public static WorkSpaceAdapter newInstance(GlideModule glideModule, Context context,
      AmenityAdapter amenityAdapter, SpannableStringModule spannableStringModule,
      PriceAdapter priceAdapter) {
    return new WorkSpaceAdapter(glideModule, context, amenityAdapter, spannableStringModule, priceAdapter);
  }
}