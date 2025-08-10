package com.newton.communities.di;

import com.newton.core.data.remote.AboutClubService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class AboutUsApiModule_ProvideAboutUsApiFactory implements Factory<AboutClubService> {
  private final Provider<Retrofit> retrofitProvider;

  public AboutUsApiModule_ProvideAboutUsApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public AboutClubService get() {
    return provideAboutUsApi(retrofitProvider.get());
  }

  public static AboutUsApiModule_ProvideAboutUsApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new AboutUsApiModule_ProvideAboutUsApiFactory(retrofitProvider);
  }

  public static AboutClubService provideAboutUsApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AboutUsApiModule.INSTANCE.provideAboutUsApi(retrofit));
  }
}
