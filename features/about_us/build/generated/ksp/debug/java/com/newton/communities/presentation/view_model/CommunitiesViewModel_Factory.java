package com.newton.communities.presentation.view_model;

import com.newton.core.domain.repositories.CommunityRepository;
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
public final class CommunitiesViewModel_Factory implements Factory<CommunitiesViewModel> {
  private final Provider<CommunityRepository> repositoryProvider;

  public CommunitiesViewModel_Factory(Provider<CommunityRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CommunitiesViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static CommunitiesViewModel_Factory create(
      Provider<CommunityRepository> repositoryProvider) {
    return new CommunitiesViewModel_Factory(repositoryProvider);
  }

  public static CommunitiesViewModel newInstance(CommunityRepository repository) {
    return new CommunitiesViewModel(repository);
  }
}
