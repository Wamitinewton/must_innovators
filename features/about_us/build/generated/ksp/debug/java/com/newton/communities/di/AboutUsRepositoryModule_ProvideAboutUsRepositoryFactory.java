package com.newton.communities.di;

import com.newton.core.data.remote.AboutClubService;
import com.newton.core.domain.repositories.CommunityRepository;
import com.newton.database.dao.ClubBioDao;
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
public final class AboutUsRepositoryModule_ProvideAboutUsRepositoryFactory implements Factory<CommunityRepository> {
  private final Provider<AboutClubService> aboutUsApiProvider;

  private final Provider<ClubBioDao> clubBioDaoProvider;

  public AboutUsRepositoryModule_ProvideAboutUsRepositoryFactory(
      Provider<AboutClubService> aboutUsApiProvider, Provider<ClubBioDao> clubBioDaoProvider) {
    this.aboutUsApiProvider = aboutUsApiProvider;
    this.clubBioDaoProvider = clubBioDaoProvider;
  }

  @Override
  public CommunityRepository get() {
    return provideAboutUsRepository(aboutUsApiProvider.get(), clubBioDaoProvider.get());
  }

  public static AboutUsRepositoryModule_ProvideAboutUsRepositoryFactory create(
      Provider<AboutClubService> aboutUsApiProvider, Provider<ClubBioDao> clubBioDaoProvider) {
    return new AboutUsRepositoryModule_ProvideAboutUsRepositoryFactory(aboutUsApiProvider, clubBioDaoProvider);
  }

  public static CommunityRepository provideAboutUsRepository(AboutClubService aboutUsApi,
      ClubBioDao clubBioDao) {
    return Preconditions.checkNotNullFromProvides(AboutUsRepositoryModule.INSTANCE.provideAboutUsRepository(aboutUsApi, clubBioDao));
  }
}
