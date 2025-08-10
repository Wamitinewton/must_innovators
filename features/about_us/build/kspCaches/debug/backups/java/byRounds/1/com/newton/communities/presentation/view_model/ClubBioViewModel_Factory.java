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
public final class ClubBioViewModel_Factory implements Factory<ClubBioViewModel> {
  private final Provider<CommunityRepository> communityRepositoryProvider;

  public ClubBioViewModel_Factory(Provider<CommunityRepository> communityRepositoryProvider) {
    this.communityRepositoryProvider = communityRepositoryProvider;
  }

  @Override
  public ClubBioViewModel get() {
    return newInstance(communityRepositoryProvider.get());
  }

  public static ClubBioViewModel_Factory create(
      Provider<CommunityRepository> communityRepositoryProvider) {
    return new ClubBioViewModel_Factory(communityRepositoryProvider);
  }

  public static ClubBioViewModel newInstance(CommunityRepository communityRepository) {
    return new ClubBioViewModel(communityRepository);
  }
}
