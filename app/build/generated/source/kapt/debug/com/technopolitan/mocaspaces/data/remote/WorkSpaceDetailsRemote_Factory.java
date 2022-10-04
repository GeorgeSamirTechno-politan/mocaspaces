// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.data.remote;

import android.content.Context;
import com.technopolitan.mocaspaces.modules.DateTimeModule;
import com.technopolitan.mocaspaces.modules.NetworkModule;
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
public final class WorkSpaceDetailsRemote_Factory implements Factory<WorkSpaceDetailsRemote> {
  private final Provider<NetworkModule> networkModuleProvider;

  private final Provider<Context> contextProvider;

  private final Provider<SpannableStringModule> spannableStringModuleProvider;

  private final Provider<DateTimeModule> dateTimeModuleProvider;

  public WorkSpaceDetailsRemote_Factory(Provider<NetworkModule> networkModuleProvider,
      Provider<Context> contextProvider,
      Provider<SpannableStringModule> spannableStringModuleProvider,
      Provider<DateTimeModule> dateTimeModuleProvider) {
    this.networkModuleProvider = networkModuleProvider;
    this.contextProvider = contextProvider;
    this.spannableStringModuleProvider = spannableStringModuleProvider;
    this.dateTimeModuleProvider = dateTimeModuleProvider;
  }

  @Override
  public WorkSpaceDetailsRemote get() {
    return newInstance(networkModuleProvider.get(), contextProvider.get(), spannableStringModuleProvider.get(), dateTimeModuleProvider.get());
  }

  public static WorkSpaceDetailsRemote_Factory create(Provider<NetworkModule> networkModuleProvider,
      Provider<Context> contextProvider,
      Provider<SpannableStringModule> spannableStringModuleProvider,
      Provider<DateTimeModule> dateTimeModuleProvider) {
    return new WorkSpaceDetailsRemote_Factory(networkModuleProvider, contextProvider, spannableStringModuleProvider, dateTimeModuleProvider);
  }

  public static WorkSpaceDetailsRemote newInstance(NetworkModule networkModule, Context context,
      SpannableStringModule spannableStringModule, DateTimeModule dateTimeModule) {
    return new WorkSpaceDetailsRemote(networkModule, context, spannableStringModule, dateTimeModule);
  }
}
