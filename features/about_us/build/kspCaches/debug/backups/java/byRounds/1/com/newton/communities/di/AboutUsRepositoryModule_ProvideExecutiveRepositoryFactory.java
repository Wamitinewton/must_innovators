package com.newton.communities.di;

import com.newton.core.data.remote.AboutClubService;
import com.newton.core.domain.repositories.ExecutiveRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AboutUsRepositoryModule_ProvideExecutiveRepositoryFactory implements Factory<ExecutiveRepository> {
  private final Provider<AboutClubService> executiveApiProvider;

  public AboutUsRepositoryModule_ProvideExecutiveRepositoryFactory(
      Provider<AboutClubService> executiveApiProvider) {
    this.executiveApiProvider = executiveApiProvider;
  }

  @Override
  public ExecutiveRepository get() {
    return provideExecutiveRepository(executiveApiProvider.get());
  }

  public static AboutUsRepositoryModule_ProvideExecutiveRepositoryFactory create(
      Provider<AboutClubService> executiveApiProvider) {
    return new AboutUsRepositoryModule_ProvideExecutiveRepositoryFactory(executiveApiProvider);
  }

  public static ExecutiveRepository provideExecutiveRepository(AboutClubService executiveApi) {
    return Preconditions.checkNotNullFromProvides(AboutUsRepositoryModule.INSTANCE.provideExecutiveRepository(executiveApi));
  }
}
