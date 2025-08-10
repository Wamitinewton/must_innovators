package com.newton.communities.data.repository;

import com.newton.core.data.remote.AboutClubService;
import com.newton.database.dao.ClubBioDao;
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
public final class CommunityRepositoryImpl_Factory implements Factory<CommunityRepositoryImpl> {
  private final Provider<AboutClubService> aboutUsApiProvider;

  private final Provider<ClubBioDao> clubBioDaoProvider;

  public CommunityRepositoryImpl_Factory(Provider<AboutClubService> aboutUsApiProvider,
      Provider<ClubBioDao> clubBioDaoProvider) {
    this.aboutUsApiProvider = aboutUsApiProvider;
    this.clubBioDaoProvider = clubBioDaoProvider;
  }

  @Override
  public CommunityRepositoryImpl get() {
    return newInstance(aboutUsApiProvider.get(), clubBioDaoProvider.get());
  }

  public static CommunityRepositoryImpl_Factory create(
      Provider<AboutClubService> aboutUsApiProvider, Provider<ClubBioDao> clubBioDaoProvider) {
    return new CommunityRepositoryImpl_Factory(aboutUsApiProvider, clubBioDaoProvider);
  }

  public static CommunityRepositoryImpl newInstance(AboutClubService aboutUsApi,
      ClubBioDao clubBioDao) {
    return new CommunityRepositoryImpl(aboutUsApi, clubBioDao);
  }
}
