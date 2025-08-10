package com.newton.communities.data.repository;

import com.newton.core.data.remote.AboutClubService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ExecutiveRepositoryImpl_Factory implements Factory<ExecutiveRepositoryImpl> {
  private final Provider<AboutClubService> executiveApiProvider;

  public ExecutiveRepositoryImpl_Factory(Provider<AboutClubService> executiveApiProvider) {
    this.executiveApiProvider = executiveApiProvider;
  }

  @Override
  public ExecutiveRepositoryImpl get() {
    return newInstance(executiveApiProvider.get());
  }

  public static ExecutiveRepositoryImpl_Factory create(
      Provider<AboutClubService> executiveApiProvider) {
    return new ExecutiveRepositoryImpl_Factory(executiveApiProvider);
  }

  public static ExecutiveRepositoryImpl newInstance(AboutClubService executiveApi) {
    return new ExecutiveRepositoryImpl(executiveApi);
  }
}
