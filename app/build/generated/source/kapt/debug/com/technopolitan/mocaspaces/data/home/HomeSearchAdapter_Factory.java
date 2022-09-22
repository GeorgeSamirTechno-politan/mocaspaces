// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.home;

import android.content.Context;
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
public final class HomeSearchAdapter_Factory implements Factory<HomeSearchAdapter> {
  private final Provider<Context> contextProvider;

  private final Provider<SpannableStringModule> spannableStringModuleProvider;

  private final Provider<SearchHintListAdapter> searchHintAdapterProvider;

  public HomeSearchAdapter_Factory(Provider<Context> contextProvider,
      Provider<SpannableStringModule> spannableStringModuleProvider,
      Provider<SearchHintListAdapter> searchHintAdapterProvider) {
    this.contextProvider = contextProvider;
    this.spannableStringModuleProvider = spannableStringModuleProvider;
    this.searchHintAdapterProvider = searchHintAdapterProvider;
  }

  @Override
  public HomeSearchAdapter get() {
    return newInstance(contextProvider.get(), spannableStringModuleProvider.get(), searchHintAdapterProvider.get());
  }

  public static HomeSearchAdapter_Factory create(Provider<Context> contextProvider,
      Provider<SpannableStringModule> spannableStringModuleProvider,
      Provider<SearchHintListAdapter> searchHintAdapterProvider) {
    return new HomeSearchAdapter_Factory(contextProvider, spannableStringModuleProvider, searchHintAdapterProvider);
  }

  public static HomeSearchAdapter newInstance(Context context,
      SpannableStringModule spannableStringModule, SearchHintListAdapter searchHintAdapter) {
    return new HomeSearchAdapter(context, spannableStringModule, searchHintAdapter);
  }
}
