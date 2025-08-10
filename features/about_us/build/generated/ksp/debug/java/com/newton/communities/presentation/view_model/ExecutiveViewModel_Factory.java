package com.newton.communities.presentation.view_model;

import com.newton.core.domain.repositories.ExecutiveRepository;
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
public final class ExecutiveViewModel_Factory implements Factory<ExecutiveViewModel> {
  private final Provider<ExecutiveRepository> repositoryProvider;

  public ExecutiveViewModel_Factory(Provider<ExecutiveRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ExecutiveViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ExecutiveViewModel_Factory create(
      Provider<ExecutiveRepository> repositoryProvider) {
    return new ExecutiveViewModel_Factory(repositoryProvider);
  }

  public static ExecutiveViewModel newInstance(ExecutiveRepository repository) {
    return new ExecutiveViewModel(repository);
  }
}
