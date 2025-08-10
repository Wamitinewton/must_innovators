package com.newton.meruinnovators.di;

import com.newton.account.navigation.AccountNavigationApi;
import com.newton.admin.navigation.AdminNavigationApi;
import com.newton.auth.navigation.AuthNavigationApi;
import com.newton.blogs.navigation.BlogsNavigationApi;
import com.newton.communities.navigation.AboutUsNavigationApi;
import com.newton.communities.navigation.CommunityNavigationApi;
import com.newton.events.navigation.EventsNavigationApi;
import com.newton.feedback.navigation.FeedbackNavigationApi;
import com.newton.home.navigation.HomeNavigationApi;
import com.newton.meruinnovators.navigation.NavigationSubGraphs;
import com.newton.partners.navigation.PartnersNavigationApi;
import com.newton.settings.navigation.SettingNavigationApi;
import com.newton.testimonials.navigation.TestimonialsNavigationApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideNavigationSubGraphsFactory implements Factory<NavigationSubGraphs> {
  private final Provider<AuthNavigationApi> authNavigationApiProvider;

  private final Provider<EventsNavigationApi> eventsNavigationApiProvider;

  private final Provider<HomeNavigationApi> homeNavigationApiProvider;

  private final Provider<BlogsNavigationApi> blogsNavigationApiProvider;

  private final Provider<AccountNavigationApi> accountNavigationApiProvider;

  private final Provider<AdminNavigationApi> adminNavigationApiProvider;

  private final Provider<AboutUsNavigationApi> aboutUsNavigationApiProvider;

  private final Provider<FeedbackNavigationApi> feedbackNavigationApiProvider;

  private final Provider<SettingNavigationApi> settingNavigationApiProvider;

  private final Provider<CommunityNavigationApi> communityNavigationApiProvider;

  private final Provider<TestimonialsNavigationApi> testimonialsNavigationApiProvider;

  private final Provider<PartnersNavigationApi> partnersNavigationApiProvider;

  public AppModule_ProvideNavigationSubGraphsFactory(
      Provider<AuthNavigationApi> authNavigationApiProvider,
      Provider<EventsNavigationApi> eventsNavigationApiProvider,
      Provider<HomeNavigationApi> homeNavigationApiProvider,
      Provider<BlogsNavigationApi> blogsNavigationApiProvider,
      Provider<AccountNavigationApi> accountNavigationApiProvider,
      Provider<AdminNavigationApi> adminNavigationApiProvider,
      Provider<AboutUsNavigationApi> aboutUsNavigationApiProvider,
      Provider<FeedbackNavigationApi> feedbackNavigationApiProvider,
      Provider<SettingNavigationApi> settingNavigationApiProvider,
      Provider<CommunityNavigationApi> communityNavigationApiProvider,
      Provider<TestimonialsNavigationApi> testimonialsNavigationApiProvider,
      Provider<PartnersNavigationApi> partnersNavigationApiProvider) {
    this.authNavigationApiProvider = authNavigationApiProvider;
    this.eventsNavigationApiProvider = eventsNavigationApiProvider;
    this.homeNavigationApiProvider = homeNavigationApiProvider;
    this.blogsNavigationApiProvider = blogsNavigationApiProvider;
    this.accountNavigationApiProvider = accountNavigationApiProvider;
    this.adminNavigationApiProvider = adminNavigationApiProvider;
    this.aboutUsNavigationApiProvider = aboutUsNavigationApiProvider;
    this.feedbackNavigationApiProvider = feedbackNavigationApiProvider;
    this.settingNavigationApiProvider = settingNavigationApiProvider;
    this.communityNavigationApiProvider = communityNavigationApiProvider;
    this.testimonialsNavigationApiProvider = testimonialsNavigationApiProvider;
    this.partnersNavigationApiProvider = partnersNavigationApiProvider;
  }

  @Override
  public NavigationSubGraphs get() {
    return provideNavigationSubGraphs(authNavigationApiProvider.get(), eventsNavigationApiProvider.get(), homeNavigationApiProvider.get(), blogsNavigationApiProvider.get(), accountNavigationApiProvider.get(), adminNavigationApiProvider.get(), aboutUsNavigationApiProvider.get(), feedbackNavigationApiProvider.get(), settingNavigationApiProvider.get(), communityNavigationApiProvider.get(), testimonialsNavigationApiProvider.get(), partnersNavigationApiProvider.get());
  }

  public static AppModule_ProvideNavigationSubGraphsFactory create(
      Provider<AuthNavigationApi> authNavigationApiProvider,
      Provider<EventsNavigationApi> eventsNavigationApiProvider,
      Provider<HomeNavigationApi> homeNavigationApiProvider,
      Provider<BlogsNavigationApi> blogsNavigationApiProvider,
      Provider<AccountNavigationApi> accountNavigationApiProvider,
      Provider<AdminNavigationApi> adminNavigationApiProvider,
      Provider<AboutUsNavigationApi> aboutUsNavigationApiProvider,
      Provider<FeedbackNavigationApi> feedbackNavigationApiProvider,
      Provider<SettingNavigationApi> settingNavigationApiProvider,
      Provider<CommunityNavigationApi> communityNavigationApiProvider,
      Provider<TestimonialsNavigationApi> testimonialsNavigationApiProvider,
      Provider<PartnersNavigationApi> partnersNavigationApiProvider) {
    return new AppModule_ProvideNavigationSubGraphsFactory(authNavigationApiProvider, eventsNavigationApiProvider, homeNavigationApiProvider, blogsNavigationApiProvider, accountNavigationApiProvider, adminNavigationApiProvider, aboutUsNavigationApiProvider, feedbackNavigationApiProvider, settingNavigationApiProvider, communityNavigationApiProvider, testimonialsNavigationApiProvider, partnersNavigationApiProvider);
  }

  public static NavigationSubGraphs provideNavigationSubGraphs(AuthNavigationApi authNavigationApi,
      EventsNavigationApi eventsNavigationApi, HomeNavigationApi homeNavigationApi,
      BlogsNavigationApi blogsNavigationApi, AccountNavigationApi accountNavigationApi,
      AdminNavigationApi adminNavigationApi, AboutUsNavigationApi aboutUsNavigationApi,
      FeedbackNavigationApi feedbackNavigationApi, SettingNavigationApi settingNavigationApi,
      CommunityNavigationApi communityNavigationApi,
      TestimonialsNavigationApi testimonialsNavigationApi,
      PartnersNavigationApi partnersNavigationApi) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNavigationSubGraphs(authNavigationApi, eventsNavigationApi, homeNavigationApi, blogsNavigationApi, accountNavigationApi, adminNavigationApi, aboutUsNavigationApi, feedbackNavigationApi, settingNavigationApi, communityNavigationApi, testimonialsNavigationApi, partnersNavigationApi));
  }
}
