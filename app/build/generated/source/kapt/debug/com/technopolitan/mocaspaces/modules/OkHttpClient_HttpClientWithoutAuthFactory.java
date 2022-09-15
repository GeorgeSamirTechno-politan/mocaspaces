// Generated by Dagger (https://dagger.dev).
package com.technopolitan.mocaspaces.modules;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("javax.inject.Named")
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class OkHttpClient_HttpClientWithoutAuthFactory implements Factory<okhttp3.OkHttpClient> {
  private final OkHttpClient module;

  public OkHttpClient_HttpClientWithoutAuthFactory(OkHttpClient module) {
    this.module = module;
  }

  @Override
  public okhttp3.OkHttpClient get() {
    return httpClientWithoutAuth(module);
  }

  public static OkHttpClient_HttpClientWithoutAuthFactory create(OkHttpClient module) {
    return new OkHttpClient_HttpClientWithoutAuthFactory(module);
  }

  public static okhttp3.OkHttpClient httpClientWithoutAuth(OkHttpClient instance) {
    return Preconditions.checkNotNullFromProvides(instance.httpClientWithoutAuth());
  }
}