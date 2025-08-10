package com.newton.communities.di;

import com.newton.communities.navigation.CommunityNavigationApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class CommunityNavigationModule_ProvideCommunityApiFactory implements Factory<CommunityNavigationApi> {
  @Override
  public CommunityNavigationApi get() {
    return provideCommunityApi();
  }

  public static CommunityNavigationModule_ProvideCommunityApiFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CommunityNavigationApi provideCommunityApi() {
    return Preconditions.checkNotNullFromProvides(CommunityNavigationModule.INSTANCE.provideCommunityApi());
  }

  private static final class InstanceHolder {
    private static final CommunityNavigationModule_ProvideCommunityApiFactory INSTANCE = new CommunityNavigationModule_ProvideCommunityApiFactory();
  }
}
