package com.newton.meruinnovators.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AppModule_ProvideHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideHttpClientFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideHttpClient(contextProvider.get());
  }

  public static AppModule_ProvideHttpClientFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideHttpClientFactory(contextProvider);
  }

  public static OkHttpClient provideHttpClient(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideHttpClient(context));
  }
}
