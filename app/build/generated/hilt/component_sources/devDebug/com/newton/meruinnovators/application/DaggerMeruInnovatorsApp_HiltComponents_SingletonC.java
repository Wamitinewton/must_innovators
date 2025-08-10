package com.newton.meruinnovators.application;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import coil3.ImageLoader;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.newton.account.di.AccountNavigationModule_ProvideEventNavApiFactory;
import com.newton.account.di.AccountRepositoryModule_ProvideAccountRepositoryFactory;
import com.newton.account.navigation.AccountNavigationApi;
import com.newton.account.presentation.viewmodel.AccountManagementViewModel;
import com.newton.account.presentation.viewmodel.AccountManagementViewModel_HiltModules;
import com.newton.account.presentation.viewmodel.ProfileFormValidator;
import com.newton.account.presentation.viewmodel.ProfileStateHolder;
import com.newton.account.presentation.viewmodel.ProfileStateReducer;
import com.newton.account.presentation.viewmodel.UpdateAccountViewModel;
import com.newton.account.presentation.viewmodel.UpdateAccountViewModel_HiltModules;
import com.newton.admin.di.AdminModule_ProvideEventNavApiFactory;
import com.newton.admin.di.AdminModule_ProvideEventRepositoryFactory;
import com.newton.admin.di.AdminModule_ProvidesEventApiFactory;
import com.newton.admin.navigation.AdminNavigationApi;
import com.newton.admin.presentation.club.viewmodel.ClubViewModel;
import com.newton.admin.presentation.club.viewmodel.ClubViewModel_HiltModules;
import com.newton.admin.presentation.community.viewmodels.CommunityViewModel;
import com.newton.admin.presentation.community.viewmodels.CommunityViewModel_HiltModules;
import com.newton.admin.presentation.community.viewmodels.UpdateCommunityViewModel;
import com.newton.admin.presentation.community.viewmodels.UpdateCommunityViewModel_HiltModules;
import com.newton.admin.presentation.events.viewmodel.AddEventViewModel;
import com.newton.admin.presentation.events.viewmodel.AddEventViewModel_HiltModules;
import com.newton.admin.presentation.events.viewmodel.EventsViewModel;
import com.newton.admin.presentation.events.viewmodel.EventsViewModel_HiltModules;
import com.newton.admin.presentation.events.viewmodel.UpdateEventsViewModel;
import com.newton.admin.presentation.events.viewmodel.UpdateEventsViewModel_HiltModules;
import com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel;
import com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel_HiltModules;
import com.newton.admin.presentation.notification.viewmodel.NotificationsViewModel;
import com.newton.admin.presentation.notification.viewmodel.NotificationsViewModel_HiltModules;
import com.newton.admin.presentation.partners.viewModel.PartnersViewModel_HiltModules;
import com.newton.admin.presentation.roleManagement.executives.viewModel.ExecutiveViewModel_HiltModules;
import com.newton.auth.data.dataStore.SessionManager;
import com.newton.auth.data.workManager.TokenRefreshWorkerFactory;
import com.newton.auth.di.AuthNavModule_ProvideAuthNavApiFactory;
import com.newton.auth.di.AuthRepositoryModule_ProvideAuthRepositoryFactory;
import com.newton.auth.presentation.login.viewModel.GetUserDataViewModel;
import com.newton.auth.presentation.login.viewModel.GetUserDataViewModel_HiltModules;
import com.newton.auth.presentation.login.viewModel.LoginFormValidator;
import com.newton.auth.presentation.login.viewModel.LoginStateHolder;
import com.newton.auth.presentation.login.viewModel.LoginViewModel;
import com.newton.auth.presentation.login.viewModel.LoginViewModel_HiltModules;
import com.newton.auth.presentation.resetPassword.viewModel.ForgotPasswordViewModel;
import com.newton.auth.presentation.resetPassword.viewModel.ForgotPasswordViewModel_HiltModules;
import com.newton.auth.presentation.signUp.viewmodel.SignupFormValidator;
import com.newton.auth.presentation.signUp.viewmodel.SignupStateHolder;
import com.newton.auth.presentation.signUp.viewmodel.SignupViewModel;
import com.newton.auth.presentation.signUp.viewmodel.SignupViewModel_HiltModules;
import com.newton.blogs.navigation.BlogsNavigationApi;
import com.newton.communities.di.AboutUsNavigationModule_ProvideCommunityApiFactory;
import com.newton.communities.di.AboutUsRepositoryModule_ProvideClubBioRepositoryFactory;
import com.newton.communities.di.AboutUsRepositoryModule_ProvideExecutiveRepositoryFactory;
import com.newton.communities.di.CommunityNavigationModule_ProvideCommunityNavigationApiFactory;
import com.newton.communities.di.CommunityRepositoryModule_ProvideAboutUsRepositoryFactory;
import com.newton.communities.navigation.AboutUsNavigationApi;
import com.newton.communities.navigation.CommunityNavigationApi;
import com.newton.communities.presentation.viewModel.ClubBioViewModel;
import com.newton.communities.presentation.viewModel.ClubBioViewModel_HiltModules;
import com.newton.communities.presentation.viewModel.CommunitiesViewModel;
import com.newton.communities.presentation.viewModel.CommunitiesViewModel_HiltModules;
import com.newton.communities.presentation.viewModel.CommunitySharedViewModel;
import com.newton.communities.presentation.viewModel.CommunitySharedViewModel_HiltModules;
import com.newton.communities.presentation.viewModel.ExecutiveViewModel;
import com.newton.database.dao.ClubBioDao;
import com.newton.database.dao.EventDao;
import com.newton.database.dao.EventsFeedbackDao;
import com.newton.database.dao.PartnersDao;
import com.newton.database.dao.TestimonialsDao;
import com.newton.database.dao.TicketDao;
import com.newton.database.dao.UserDao;
import com.newton.database.dao.UserFeedbackDao;
import com.newton.database.db.AppDatabase;
import com.newton.database.dbManager.CacheManager;
import com.newton.database.dbManager.RoomDataManager;
import com.newton.database.di.AppDatabaseModule_ProvideAppDatabaseFactory;
import com.newton.database.di.AppDatabaseModule_ProvideClubBioDaoFactory;
import com.newton.database.di.AppDatabaseModule_ProvideEventDaoFactory;
import com.newton.database.di.AppDatabaseModule_ProvideEventsFeedbackDaoFactory;
import com.newton.database.di.AppDatabaseModule_ProvidePartnersDaoFactory;
import com.newton.database.di.AppDatabaseModule_ProvideTestimonialsDaoFactory;
import com.newton.database.di.AppDatabaseModule_ProvideTicketDaoFactory;
import com.newton.database.di.AppDatabaseModule_ProvideUserDaoFactory;
import com.newton.database.di.AppDatabaseModule_ProvideUserFeedbackDaoFactory;
import com.newton.events.di.EventNavigationModule_ProvideEventNavApiFactory;
import com.newton.events.di.EventRepositoryModule_ProvideEventRepositoryFactory;
import com.newton.events.navigation.EventsNavigationApi;
import com.newton.events.presentation.viewmodel.EventRegistrationStateHolder;
import com.newton.events.presentation.viewmodel.EventRegistrationValidator;
import com.newton.events.presentation.viewmodel.EventRsvpViewmodel;
import com.newton.events.presentation.viewmodel.EventRsvpViewmodel_HiltModules;
import com.newton.events.presentation.viewmodel.EventViewModel;
import com.newton.events.presentation.viewmodel.EventViewModel_HiltModules;
import com.newton.events.presentation.viewmodel.EventsSharedViewModel;
import com.newton.events.presentation.viewmodel.EventsSharedViewModel_HiltModules;
import com.newton.events.presentation.viewmodel.RsvpSharedViewModel;
import com.newton.events.presentation.viewmodel.RsvpSharedViewModel_HiltModules;
import com.newton.events.presentation.viewmodel.TicketStateBus;
import com.newton.events.presentation.viewmodel.UserTicketsViewModel;
import com.newton.events.presentation.viewmodel.UserTicketsViewModel_HiltModules;
import com.newton.feedback.di.UserFeedbackNavigationModule_ProvideUserFeedbackNavigationApiFactory;
import com.newton.feedback.navigation.FeedbackNavigationApi;
import com.newton.feedback.presentation.viewmodel.UserFeedbackViewModel;
import com.newton.feedback.presentation.viewmodel.UserFeedbackViewModel_HiltModules;
import com.newton.home.di.BlogsModule_ProvideEventNavApiFactory;
import com.newton.home.di.HomeNavigationModule_ProvideHomeNavigationApiFactory;
import com.newton.home.navigation.HomeNavigationApi;
import com.newton.meruinnovators.activity.MainActivity;
import com.newton.meruinnovators.activity.MainActivity_MembersInjector;
import com.newton.meruinnovators.di.AppModule_ProvideContextFactory;
import com.newton.meruinnovators.di.AppModule_ProvideHttpClientFactory;
import com.newton.meruinnovators.di.AppModule_ProvideMeruInnovatorsApiFactory;
import com.newton.meruinnovators.di.AppModule_ProvideNavigationSubGraphsFactory;
import com.newton.meruinnovators.di.CoilModule_ProvideImageLoaderFactory;
import com.newton.meruinnovators.navigation.NavigationSubGraphs;
import com.newton.network.data.remote.AboutClubService;
import com.newton.network.data.remote.AdminApi;
import com.newton.network.data.remote.AuthService;
import com.newton.network.data.remote.CommunitiesApiService;
import com.newton.network.data.remote.EventService;
import com.newton.network.data.remote.PartnersService;
import com.newton.network.data.remote.TestimonialsService;
import com.newton.network.di.AboutUsApiModule_ProvideAboutUsApiFactory;
import com.newton.network.di.AuthServiceModule_ProvideAuthServiceFactory;
import com.newton.network.di.CommunityApiModule_ProvideAboutUsApiFactory;
import com.newton.network.di.EventApiModule_ProvidesEventApiFactory;
import com.newton.network.di.PartnersServiceModule_ProvidePartnersApiServiceFactory;
import com.newton.network.di.TestimonialsApiModule_ProvideTestimonialApiFactory;
import com.newton.network.domain.repositories.AdminRepository;
import com.newton.network.domain.repositories.AuthRepository;
import com.newton.network.domain.repositories.ClubBioRepository;
import com.newton.network.domain.repositories.CommunityRepository;
import com.newton.network.domain.repositories.EventRepository;
import com.newton.network.domain.repositories.ExecutiveRepository;
import com.newton.network.domain.repositories.PartnersRepository;
import com.newton.network.domain.repositories.TestimonialsRepository;
import com.newton.network.domain.repositories.UpdateUserRepository;
import com.newton.notifications.data.remote.NotificationApiService;
import com.newton.notifications.data.repository.FCMTokenRepository;
import com.newton.notifications.di.NotificationModule_ProvideNotificationApiServiceFactory;
import com.newton.notifications.manager.NotificationsManager;
import com.newton.notifications.services.FCMService;
import com.newton.notifications.services.FCMService_MembersInjector;
import com.newton.notifications.services.NotificationService;
import com.newton.partners.di.PartnersNavigationModule_ProvidePartnersNavigationApiFactory;
import com.newton.partners.di.PartnersRepositoryModule_ProvidePartnersRepositoryFactory;
import com.newton.partners.navigation.PartnersNavigationApi;
import com.newton.partners.presentation.viewModel.PartnersSharedViewModel;
import com.newton.partners.presentation.viewModel.PartnersSharedViewModel_HiltModules;
import com.newton.partners.presentation.viewModel.PartnersViewModel;
import com.newton.settings.di.SettingsNavigationModule_ProvideSettingsNavigationApiFactory;
import com.newton.settings.navigation.SettingNavigationApi;
import com.newton.settings.presentation.viewModel.CacheManagerViewModel;
import com.newton.settings.presentation.viewModel.CacheManagerViewModel_HiltModules;
import com.newton.shared.sharedBus.EventBus;
import com.newton.sharedprefs.di.PrefsModule_ProvidePrefsManagerFactory;
import com.newton.sharedprefs.di.PrefsModule_ProvidePrefsRepositoryFactory;
import com.newton.sharedprefs.domain.PrefsRepository;
import com.newton.sharedprefs.prefsManager.PreferenceManager;
import com.newton.sharedprefs.viewModel.ThemeViewModel;
import com.newton.sharedprefs.viewModel.ThemeViewModel_HiltModules;
import com.newton.testimonials.di.TestimonialsNavigationModule_ProvideTestimonialsNavigationApiFactory;
import com.newton.testimonials.di.TestimonialsRepositoryModule_ProvideTestimonialsRepositoryFactory;
import com.newton.testimonials.navigation.TestimonialsNavigationApi;
import com.newton.testimonials.presentation.viewModel.CreateTestimonialViewModel;
import com.newton.testimonials.presentation.viewModel.CreateTestimonialViewModel_HiltModules;
import com.newton.testimonials.presentation.viewModel.GetTestimonialsViewModel;
import com.newton.testimonials.presentation.viewModel.GetTestimonialsViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideApplicationFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerMeruInnovatorsApp_HiltComponents_SingletonC {
  private DaggerMeruInnovatorsApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public MeruInnovatorsApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements MeruInnovatorsApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public MeruInnovatorsApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements MeruInnovatorsApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public MeruInnovatorsApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements MeruInnovatorsApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public MeruInnovatorsApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements MeruInnovatorsApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MeruInnovatorsApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements MeruInnovatorsApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public MeruInnovatorsApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements MeruInnovatorsApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public MeruInnovatorsApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements MeruInnovatorsApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public MeruInnovatorsApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends MeruInnovatorsApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends MeruInnovatorsApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends MeruInnovatorsApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends MeruInnovatorsApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
      injectMainActivity2(arg0);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>builderWithExpectedSize(32).put(LazyClassKeyProvider.com_newton_account_presentation_viewmodel_AccountManagementViewModel, AccountManagementViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_admin_presentation_events_viewmodel_AddEventViewModel, AddEventViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_admin_presentation_feedbacks_viewmodel_AdminFeedbackViewModel, AdminFeedbackViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_settings_presentation_viewModel_CacheManagerViewModel, CacheManagerViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_communities_presentation_viewModel_ClubBioViewModel, ClubBioViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_admin_presentation_club_viewmodel_ClubViewModel, ClubViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_communities_presentation_viewModel_CommunitiesViewModel, CommunitiesViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_communities_presentation_viewModel_CommunitySharedViewModel, CommunitySharedViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_admin_presentation_community_viewmodels_CommunityViewModel, CommunityViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_testimonials_presentation_viewModel_CreateTestimonialViewModel, CreateTestimonialViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_events_presentation_viewmodel_EventRsvpViewmodel, EventRsvpViewmodel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_events_presentation_viewmodel_EventViewModel, EventViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_events_presentation_viewmodel_EventsSharedViewModel, EventsSharedViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_admin_presentation_events_viewmodel_EventsViewModel, EventsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_admin_presentation_roleManagement_executives_viewModel_ExecutiveViewModel, ExecutiveViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_communities_presentation_viewModel_ExecutiveViewModel, com.newton.communities.presentation.viewModel.ExecutiveViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_auth_presentation_resetPassword_viewModel_ForgotPasswordViewModel, ForgotPasswordViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_testimonials_presentation_viewModel_GetTestimonialsViewModel, GetTestimonialsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_auth_presentation_login_viewModel_GetUserDataViewModel, GetUserDataViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_auth_presentation_login_viewModel_LoginViewModel, LoginViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_admin_presentation_notification_viewmodel_NotificationsViewModel, NotificationsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_partners_presentation_viewModel_PartnersSharedViewModel, PartnersSharedViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_admin_presentation_partners_viewModel_PartnersViewModel, PartnersViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_partners_presentation_viewModel_PartnersViewModel, com.newton.partners.presentation.viewModel.PartnersViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_events_presentation_viewmodel_RsvpSharedViewModel, RsvpSharedViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_auth_presentation_signUp_viewmodel_SignupViewModel, SignupViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_sharedprefs_viewModel_ThemeViewModel, ThemeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_account_presentation_viewmodel_UpdateAccountViewModel, UpdateAccountViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_admin_presentation_community_viewmodels_UpdateCommunityViewModel, UpdateCommunityViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_admin_presentation_events_viewmodel_UpdateEventsViewModel, UpdateEventsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_feedback_presentation_viewmodel_UserFeedbackViewModel, UserFeedbackViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_newton_events_presentation_viewmodel_UserTicketsViewModel, UserTicketsViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectNavigationSubGraphs(instance, singletonCImpl.navigationSubGraphs());
      MainActivity_MembersInjector.injectNotificationsManager(instance, singletonCImpl.notificationsManagerProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_newton_events_presentation_viewmodel_EventRsvpViewmodel = "com.newton.events.presentation.viewmodel.EventRsvpViewmodel";

      static String com_newton_communities_presentation_viewModel_CommunitySharedViewModel = "com.newton.communities.presentation.viewModel.CommunitySharedViewModel";

      static String com_newton_communities_presentation_viewModel_ExecutiveViewModel = "com.newton.communities.presentation.viewModel.ExecutiveViewModel";

      static String com_newton_admin_presentation_community_viewmodels_UpdateCommunityViewModel = "com.newton.admin.presentation.community.viewmodels.UpdateCommunityViewModel";

      static String com_newton_admin_presentation_events_viewmodel_EventsViewModel = "com.newton.admin.presentation.events.viewmodel.EventsViewModel";

      static String com_newton_testimonials_presentation_viewModel_CreateTestimonialViewModel = "com.newton.testimonials.presentation.viewModel.CreateTestimonialViewModel";

      static String com_newton_account_presentation_viewmodel_AccountManagementViewModel = "com.newton.account.presentation.viewmodel.AccountManagementViewModel";

      static String com_newton_account_presentation_viewmodel_UpdateAccountViewModel = "com.newton.account.presentation.viewmodel.UpdateAccountViewModel";

      static String com_newton_testimonials_presentation_viewModel_GetTestimonialsViewModel = "com.newton.testimonials.presentation.viewModel.GetTestimonialsViewModel";

      static String com_newton_partners_presentation_viewModel_PartnersViewModel = "com.newton.partners.presentation.viewModel.PartnersViewModel";

      static String com_newton_auth_presentation_resetPassword_viewModel_ForgotPasswordViewModel = "com.newton.auth.presentation.resetPassword.viewModel.ForgotPasswordViewModel";

      static String com_newton_admin_presentation_feedbacks_viewmodel_AdminFeedbackViewModel = "com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel";

      static String com_newton_auth_presentation_login_viewModel_GetUserDataViewModel = "com.newton.auth.presentation.login.viewModel.GetUserDataViewModel";

      static String com_newton_admin_presentation_community_viewmodels_CommunityViewModel = "com.newton.admin.presentation.community.viewmodels.CommunityViewModel";

      static String com_newton_admin_presentation_roleManagement_executives_viewModel_ExecutiveViewModel = "com.newton.admin.presentation.roleManagement.executives.viewModel.ExecutiveViewModel";

      static String com_newton_admin_presentation_notification_viewmodel_NotificationsViewModel = "com.newton.admin.presentation.notification.viewmodel.NotificationsViewModel";

      static String com_newton_sharedprefs_viewModel_ThemeViewModel = "com.newton.sharedprefs.viewModel.ThemeViewModel";

      static String com_newton_auth_presentation_signUp_viewmodel_SignupViewModel = "com.newton.auth.presentation.signUp.viewmodel.SignupViewModel";

      static String com_newton_events_presentation_viewmodel_EventsSharedViewModel = "com.newton.events.presentation.viewmodel.EventsSharedViewModel";

      static String com_newton_auth_presentation_login_viewModel_LoginViewModel = "com.newton.auth.presentation.login.viewModel.LoginViewModel";

      static String com_newton_admin_presentation_events_viewmodel_UpdateEventsViewModel = "com.newton.admin.presentation.events.viewmodel.UpdateEventsViewModel";

      static String com_newton_settings_presentation_viewModel_CacheManagerViewModel = "com.newton.settings.presentation.viewModel.CacheManagerViewModel";

      static String com_newton_admin_presentation_partners_viewModel_PartnersViewModel = "com.newton.admin.presentation.partners.viewModel.PartnersViewModel";

      static String com_newton_admin_presentation_club_viewmodel_ClubViewModel = "com.newton.admin.presentation.club.viewmodel.ClubViewModel";

      static String com_newton_events_presentation_viewmodel_EventViewModel = "com.newton.events.presentation.viewmodel.EventViewModel";

      static String com_newton_feedback_presentation_viewmodel_UserFeedbackViewModel = "com.newton.feedback.presentation.viewmodel.UserFeedbackViewModel";

      static String com_newton_admin_presentation_events_viewmodel_AddEventViewModel = "com.newton.admin.presentation.events.viewmodel.AddEventViewModel";

      static String com_newton_partners_presentation_viewModel_PartnersSharedViewModel = "com.newton.partners.presentation.viewModel.PartnersSharedViewModel";

      static String com_newton_events_presentation_viewmodel_RsvpSharedViewModel = "com.newton.events.presentation.viewmodel.RsvpSharedViewModel";

      static String com_newton_communities_presentation_viewModel_CommunitiesViewModel = "com.newton.communities.presentation.viewModel.CommunitiesViewModel";

      static String com_newton_communities_presentation_viewModel_ClubBioViewModel = "com.newton.communities.presentation.viewModel.ClubBioViewModel";

      static String com_newton_events_presentation_viewmodel_UserTicketsViewModel = "com.newton.events.presentation.viewmodel.UserTicketsViewModel";

      @KeepFieldType
      EventRsvpViewmodel com_newton_events_presentation_viewmodel_EventRsvpViewmodel2;

      @KeepFieldType
      CommunitySharedViewModel com_newton_communities_presentation_viewModel_CommunitySharedViewModel2;

      @KeepFieldType
      ExecutiveViewModel com_newton_communities_presentation_viewModel_ExecutiveViewModel2;

      @KeepFieldType
      UpdateCommunityViewModel com_newton_admin_presentation_community_viewmodels_UpdateCommunityViewModel2;

      @KeepFieldType
      EventsViewModel com_newton_admin_presentation_events_viewmodel_EventsViewModel2;

      @KeepFieldType
      CreateTestimonialViewModel com_newton_testimonials_presentation_viewModel_CreateTestimonialViewModel2;

      @KeepFieldType
      AccountManagementViewModel com_newton_account_presentation_viewmodel_AccountManagementViewModel2;

      @KeepFieldType
      UpdateAccountViewModel com_newton_account_presentation_viewmodel_UpdateAccountViewModel2;

      @KeepFieldType
      GetTestimonialsViewModel com_newton_testimonials_presentation_viewModel_GetTestimonialsViewModel2;

      @KeepFieldType
      PartnersViewModel com_newton_partners_presentation_viewModel_PartnersViewModel2;

      @KeepFieldType
      ForgotPasswordViewModel com_newton_auth_presentation_resetPassword_viewModel_ForgotPasswordViewModel2;

      @KeepFieldType
      AdminFeedbackViewModel com_newton_admin_presentation_feedbacks_viewmodel_AdminFeedbackViewModel2;

      @KeepFieldType
      GetUserDataViewModel com_newton_auth_presentation_login_viewModel_GetUserDataViewModel2;

      @KeepFieldType
      CommunityViewModel com_newton_admin_presentation_community_viewmodels_CommunityViewModel2;

      @KeepFieldType
      com.newton.admin.presentation.roleManagement.executives.viewModel.ExecutiveViewModel com_newton_admin_presentation_roleManagement_executives_viewModel_ExecutiveViewModel2;

      @KeepFieldType
      NotificationsViewModel com_newton_admin_presentation_notification_viewmodel_NotificationsViewModel2;

      @KeepFieldType
      ThemeViewModel com_newton_sharedprefs_viewModel_ThemeViewModel2;

      @KeepFieldType
      SignupViewModel com_newton_auth_presentation_signUp_viewmodel_SignupViewModel2;

      @KeepFieldType
      EventsSharedViewModel com_newton_events_presentation_viewmodel_EventsSharedViewModel2;

      @KeepFieldType
      LoginViewModel com_newton_auth_presentation_login_viewModel_LoginViewModel2;

      @KeepFieldType
      UpdateEventsViewModel com_newton_admin_presentation_events_viewmodel_UpdateEventsViewModel2;

      @KeepFieldType
      CacheManagerViewModel com_newton_settings_presentation_viewModel_CacheManagerViewModel2;

      @KeepFieldType
      com.newton.admin.presentation.partners.viewModel.PartnersViewModel com_newton_admin_presentation_partners_viewModel_PartnersViewModel2;

      @KeepFieldType
      ClubViewModel com_newton_admin_presentation_club_viewmodel_ClubViewModel2;

      @KeepFieldType
      EventViewModel com_newton_events_presentation_viewmodel_EventViewModel2;

      @KeepFieldType
      UserFeedbackViewModel com_newton_feedback_presentation_viewmodel_UserFeedbackViewModel2;

      @KeepFieldType
      AddEventViewModel com_newton_admin_presentation_events_viewmodel_AddEventViewModel2;

      @KeepFieldType
      PartnersSharedViewModel com_newton_partners_presentation_viewModel_PartnersSharedViewModel2;

      @KeepFieldType
      RsvpSharedViewModel com_newton_events_presentation_viewmodel_RsvpSharedViewModel2;

      @KeepFieldType
      CommunitiesViewModel com_newton_communities_presentation_viewModel_CommunitiesViewModel2;

      @KeepFieldType
      ClubBioViewModel com_newton_communities_presentation_viewModel_ClubBioViewModel2;

      @KeepFieldType
      UserTicketsViewModel com_newton_events_presentation_viewmodel_UserTicketsViewModel2;
    }
  }

  private static final class ViewModelCImpl extends MeruInnovatorsApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AccountManagementViewModel> accountManagementViewModelProvider;

    private Provider<AddEventViewModel> addEventViewModelProvider;

    private Provider<AdminFeedbackViewModel> adminFeedbackViewModelProvider;

    private Provider<CacheManagerViewModel> cacheManagerViewModelProvider;

    private Provider<ClubBioViewModel> clubBioViewModelProvider;

    private Provider<ClubViewModel> clubViewModelProvider;

    private Provider<CommunitiesViewModel> communitiesViewModelProvider;

    private Provider<CommunitySharedViewModel> communitySharedViewModelProvider;

    private Provider<CommunityViewModel> communityViewModelProvider;

    private Provider<CreateTestimonialViewModel> createTestimonialViewModelProvider;

    private Provider<EventRsvpViewmodel> eventRsvpViewmodelProvider;

    private Provider<EventViewModel> eventViewModelProvider;

    private Provider<EventsSharedViewModel> eventsSharedViewModelProvider;

    private Provider<EventsViewModel> eventsViewModelProvider;

    private Provider<com.newton.admin.presentation.roleManagement.executives.viewModel.ExecutiveViewModel> executiveViewModelProvider;

    private Provider<ExecutiveViewModel> executiveViewModelProvider2;

    private Provider<ForgotPasswordViewModel> forgotPasswordViewModelProvider;

    private Provider<GetTestimonialsViewModel> getTestimonialsViewModelProvider;

    private Provider<GetUserDataViewModel> getUserDataViewModelProvider;

    private Provider<LoginViewModel> loginViewModelProvider;

    private Provider<NotificationsViewModel> notificationsViewModelProvider;

    private Provider<PartnersSharedViewModel> partnersSharedViewModelProvider;

    private Provider<com.newton.admin.presentation.partners.viewModel.PartnersViewModel> partnersViewModelProvider;

    private Provider<PartnersViewModel> partnersViewModelProvider2;

    private Provider<RsvpSharedViewModel> rsvpSharedViewModelProvider;

    private Provider<SignupViewModel> signupViewModelProvider;

    private Provider<ThemeViewModel> themeViewModelProvider;

    private Provider<UpdateAccountViewModel> updateAccountViewModelProvider;

    private Provider<UpdateCommunityViewModel> updateCommunityViewModelProvider;

    private Provider<UpdateEventsViewModel> updateEventsViewModelProvider;

    private Provider<UserFeedbackViewModel> userFeedbackViewModelProvider;

    private Provider<UserTicketsViewModel> userTicketsViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private EventRegistrationStateHolder eventRegistrationStateHolder() {
      return new EventRegistrationStateHolder(new EventRegistrationValidator());
    }

    private LoginStateHolder loginStateHolder() {
      return new LoginStateHolder(new LoginFormValidator());
    }

    private SignupStateHolder signupStateHolder() {
      return new SignupStateHolder(new SignupFormValidator());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.accountManagementViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.addEventViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.adminFeedbackViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.cacheManagerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.clubBioViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.clubViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.communitiesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.communitySharedViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.communityViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.createTestimonialViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.eventRsvpViewmodelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.eventViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.eventsSharedViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.eventsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
      this.executiveViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 14);
      this.executiveViewModelProvider2 = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 15);
      this.forgotPasswordViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 16);
      this.getTestimonialsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 17);
      this.getUserDataViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 18);
      this.loginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 19);
      this.notificationsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 20);
      this.partnersSharedViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 21);
      this.partnersViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 22);
      this.partnersViewModelProvider2 = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 23);
      this.rsvpSharedViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 24);
      this.signupViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 25);
      this.themeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 26);
      this.updateAccountViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 27);
      this.updateCommunityViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 28);
      this.updateEventsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 29);
      this.userFeedbackViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 30);
      this.userTicketsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 31);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(32).put(LazyClassKeyProvider.com_newton_account_presentation_viewmodel_AccountManagementViewModel, ((Provider) accountManagementViewModelProvider)).put(LazyClassKeyProvider.com_newton_admin_presentation_events_viewmodel_AddEventViewModel, ((Provider) addEventViewModelProvider)).put(LazyClassKeyProvider.com_newton_admin_presentation_feedbacks_viewmodel_AdminFeedbackViewModel, ((Provider) adminFeedbackViewModelProvider)).put(LazyClassKeyProvider.com_newton_settings_presentation_viewModel_CacheManagerViewModel, ((Provider) cacheManagerViewModelProvider)).put(LazyClassKeyProvider.com_newton_communities_presentation_viewModel_ClubBioViewModel, ((Provider) clubBioViewModelProvider)).put(LazyClassKeyProvider.com_newton_admin_presentation_club_viewmodel_ClubViewModel, ((Provider) clubViewModelProvider)).put(LazyClassKeyProvider.com_newton_communities_presentation_viewModel_CommunitiesViewModel, ((Provider) communitiesViewModelProvider)).put(LazyClassKeyProvider.com_newton_communities_presentation_viewModel_CommunitySharedViewModel, ((Provider) communitySharedViewModelProvider)).put(LazyClassKeyProvider.com_newton_admin_presentation_community_viewmodels_CommunityViewModel, ((Provider) communityViewModelProvider)).put(LazyClassKeyProvider.com_newton_testimonials_presentation_viewModel_CreateTestimonialViewModel, ((Provider) createTestimonialViewModelProvider)).put(LazyClassKeyProvider.com_newton_events_presentation_viewmodel_EventRsvpViewmodel, ((Provider) eventRsvpViewmodelProvider)).put(LazyClassKeyProvider.com_newton_events_presentation_viewmodel_EventViewModel, ((Provider) eventViewModelProvider)).put(LazyClassKeyProvider.com_newton_events_presentation_viewmodel_EventsSharedViewModel, ((Provider) eventsSharedViewModelProvider)).put(LazyClassKeyProvider.com_newton_admin_presentation_events_viewmodel_EventsViewModel, ((Provider) eventsViewModelProvider)).put(LazyClassKeyProvider.com_newton_admin_presentation_roleManagement_executives_viewModel_ExecutiveViewModel, ((Provider) executiveViewModelProvider)).put(LazyClassKeyProvider.com_newton_communities_presentation_viewModel_ExecutiveViewModel, ((Provider) executiveViewModelProvider2)).put(LazyClassKeyProvider.com_newton_auth_presentation_resetPassword_viewModel_ForgotPasswordViewModel, ((Provider) forgotPasswordViewModelProvider)).put(LazyClassKeyProvider.com_newton_testimonials_presentation_viewModel_GetTestimonialsViewModel, ((Provider) getTestimonialsViewModelProvider)).put(LazyClassKeyProvider.com_newton_auth_presentation_login_viewModel_GetUserDataViewModel, ((Provider) getUserDataViewModelProvider)).put(LazyClassKeyProvider.com_newton_auth_presentation_login_viewModel_LoginViewModel, ((Provider) loginViewModelProvider)).put(LazyClassKeyProvider.com_newton_admin_presentation_notification_viewmodel_NotificationsViewModel, ((Provider) notificationsViewModelProvider)).put(LazyClassKeyProvider.com_newton_partners_presentation_viewModel_PartnersSharedViewModel, ((Provider) partnersSharedViewModelProvider)).put(LazyClassKeyProvider.com_newton_admin_presentation_partners_viewModel_PartnersViewModel, ((Provider) partnersViewModelProvider)).put(LazyClassKeyProvider.com_newton_partners_presentation_viewModel_PartnersViewModel, ((Provider) partnersViewModelProvider2)).put(LazyClassKeyProvider.com_newton_events_presentation_viewmodel_RsvpSharedViewModel, ((Provider) rsvpSharedViewModelProvider)).put(LazyClassKeyProvider.com_newton_auth_presentation_signUp_viewmodel_SignupViewModel, ((Provider) signupViewModelProvider)).put(LazyClassKeyProvider.com_newton_sharedprefs_viewModel_ThemeViewModel, ((Provider) themeViewModelProvider)).put(LazyClassKeyProvider.com_newton_account_presentation_viewmodel_UpdateAccountViewModel, ((Provider) updateAccountViewModelProvider)).put(LazyClassKeyProvider.com_newton_admin_presentation_community_viewmodels_UpdateCommunityViewModel, ((Provider) updateCommunityViewModelProvider)).put(LazyClassKeyProvider.com_newton_admin_presentation_events_viewmodel_UpdateEventsViewModel, ((Provider) updateEventsViewModelProvider)).put(LazyClassKeyProvider.com_newton_feedback_presentation_viewmodel_UserFeedbackViewModel, ((Provider) userFeedbackViewModelProvider)).put(LazyClassKeyProvider.com_newton_events_presentation_viewmodel_UserTicketsViewModel, ((Provider) userTicketsViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_newton_auth_presentation_login_viewModel_LoginViewModel = "com.newton.auth.presentation.login.viewModel.LoginViewModel";

      static String com_newton_admin_presentation_events_viewmodel_AddEventViewModel = "com.newton.admin.presentation.events.viewmodel.AddEventViewModel";

      static String com_newton_partners_presentation_viewModel_PartnersSharedViewModel = "com.newton.partners.presentation.viewModel.PartnersSharedViewModel";

      static String com_newton_testimonials_presentation_viewModel_CreateTestimonialViewModel = "com.newton.testimonials.presentation.viewModel.CreateTestimonialViewModel";

      static String com_newton_admin_presentation_events_viewmodel_EventsViewModel = "com.newton.admin.presentation.events.viewmodel.EventsViewModel";

      static String com_newton_auth_presentation_resetPassword_viewModel_ForgotPasswordViewModel = "com.newton.auth.presentation.resetPassword.viewModel.ForgotPasswordViewModel";

      static String com_newton_admin_presentation_partners_viewModel_PartnersViewModel = "com.newton.admin.presentation.partners.viewModel.PartnersViewModel";

      static String com_newton_account_presentation_viewmodel_AccountManagementViewModel = "com.newton.account.presentation.viewmodel.AccountManagementViewModel";

      static String com_newton_auth_presentation_login_viewModel_GetUserDataViewModel = "com.newton.auth.presentation.login.viewModel.GetUserDataViewModel";

      static String com_newton_auth_presentation_signUp_viewmodel_SignupViewModel = "com.newton.auth.presentation.signUp.viewmodel.SignupViewModel";

      static String com_newton_admin_presentation_notification_viewmodel_NotificationsViewModel = "com.newton.admin.presentation.notification.viewmodel.NotificationsViewModel";

      static String com_newton_events_presentation_viewmodel_UserTicketsViewModel = "com.newton.events.presentation.viewmodel.UserTicketsViewModel";

      static String com_newton_settings_presentation_viewModel_CacheManagerViewModel = "com.newton.settings.presentation.viewModel.CacheManagerViewModel";

      static String com_newton_admin_presentation_roleManagement_executives_viewModel_ExecutiveViewModel = "com.newton.admin.presentation.roleManagement.executives.viewModel.ExecutiveViewModel";

      static String com_newton_events_presentation_viewmodel_EventViewModel = "com.newton.events.presentation.viewmodel.EventViewModel";

      static String com_newton_admin_presentation_community_viewmodels_UpdateCommunityViewModel = "com.newton.admin.presentation.community.viewmodels.UpdateCommunityViewModel";

      static String com_newton_communities_presentation_viewModel_ExecutiveViewModel = "com.newton.communities.presentation.viewModel.ExecutiveViewModel";

      static String com_newton_communities_presentation_viewModel_ClubBioViewModel = "com.newton.communities.presentation.viewModel.ClubBioViewModel";

      static String com_newton_communities_presentation_viewModel_CommunitiesViewModel = "com.newton.communities.presentation.viewModel.CommunitiesViewModel";

      static String com_newton_sharedprefs_viewModel_ThemeViewModel = "com.newton.sharedprefs.viewModel.ThemeViewModel";

      static String com_newton_events_presentation_viewmodel_RsvpSharedViewModel = "com.newton.events.presentation.viewmodel.RsvpSharedViewModel";

      static String com_newton_admin_presentation_events_viewmodel_UpdateEventsViewModel = "com.newton.admin.presentation.events.viewmodel.UpdateEventsViewModel";

      static String com_newton_admin_presentation_club_viewmodel_ClubViewModel = "com.newton.admin.presentation.club.viewmodel.ClubViewModel";

      static String com_newton_communities_presentation_viewModel_CommunitySharedViewModel = "com.newton.communities.presentation.viewModel.CommunitySharedViewModel";

      static String com_newton_events_presentation_viewmodel_EventsSharedViewModel = "com.newton.events.presentation.viewmodel.EventsSharedViewModel";

      static String com_newton_testimonials_presentation_viewModel_GetTestimonialsViewModel = "com.newton.testimonials.presentation.viewModel.GetTestimonialsViewModel";

      static String com_newton_admin_presentation_community_viewmodels_CommunityViewModel = "com.newton.admin.presentation.community.viewmodels.CommunityViewModel";

      static String com_newton_account_presentation_viewmodel_UpdateAccountViewModel = "com.newton.account.presentation.viewmodel.UpdateAccountViewModel";

      static String com_newton_events_presentation_viewmodel_EventRsvpViewmodel = "com.newton.events.presentation.viewmodel.EventRsvpViewmodel";

      static String com_newton_partners_presentation_viewModel_PartnersViewModel = "com.newton.partners.presentation.viewModel.PartnersViewModel";

      static String com_newton_admin_presentation_feedbacks_viewmodel_AdminFeedbackViewModel = "com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel";

      static String com_newton_feedback_presentation_viewmodel_UserFeedbackViewModel = "com.newton.feedback.presentation.viewmodel.UserFeedbackViewModel";

      @KeepFieldType
      LoginViewModel com_newton_auth_presentation_login_viewModel_LoginViewModel2;

      @KeepFieldType
      AddEventViewModel com_newton_admin_presentation_events_viewmodel_AddEventViewModel2;

      @KeepFieldType
      PartnersSharedViewModel com_newton_partners_presentation_viewModel_PartnersSharedViewModel2;

      @KeepFieldType
      CreateTestimonialViewModel com_newton_testimonials_presentation_viewModel_CreateTestimonialViewModel2;

      @KeepFieldType
      EventsViewModel com_newton_admin_presentation_events_viewmodel_EventsViewModel2;

      @KeepFieldType
      ForgotPasswordViewModel com_newton_auth_presentation_resetPassword_viewModel_ForgotPasswordViewModel2;

      @KeepFieldType
      com.newton.admin.presentation.partners.viewModel.PartnersViewModel com_newton_admin_presentation_partners_viewModel_PartnersViewModel2;

      @KeepFieldType
      AccountManagementViewModel com_newton_account_presentation_viewmodel_AccountManagementViewModel2;

      @KeepFieldType
      GetUserDataViewModel com_newton_auth_presentation_login_viewModel_GetUserDataViewModel2;

      @KeepFieldType
      SignupViewModel com_newton_auth_presentation_signUp_viewmodel_SignupViewModel2;

      @KeepFieldType
      NotificationsViewModel com_newton_admin_presentation_notification_viewmodel_NotificationsViewModel2;

      @KeepFieldType
      UserTicketsViewModel com_newton_events_presentation_viewmodel_UserTicketsViewModel2;

      @KeepFieldType
      CacheManagerViewModel com_newton_settings_presentation_viewModel_CacheManagerViewModel2;

      @KeepFieldType
      com.newton.admin.presentation.roleManagement.executives.viewModel.ExecutiveViewModel com_newton_admin_presentation_roleManagement_executives_viewModel_ExecutiveViewModel2;

      @KeepFieldType
      EventViewModel com_newton_events_presentation_viewmodel_EventViewModel2;

      @KeepFieldType
      UpdateCommunityViewModel com_newton_admin_presentation_community_viewmodels_UpdateCommunityViewModel2;

      @KeepFieldType
      ExecutiveViewModel com_newton_communities_presentation_viewModel_ExecutiveViewModel2;

      @KeepFieldType
      ClubBioViewModel com_newton_communities_presentation_viewModel_ClubBioViewModel2;

      @KeepFieldType
      CommunitiesViewModel com_newton_communities_presentation_viewModel_CommunitiesViewModel2;

      @KeepFieldType
      ThemeViewModel com_newton_sharedprefs_viewModel_ThemeViewModel2;

      @KeepFieldType
      RsvpSharedViewModel com_newton_events_presentation_viewmodel_RsvpSharedViewModel2;

      @KeepFieldType
      UpdateEventsViewModel com_newton_admin_presentation_events_viewmodel_UpdateEventsViewModel2;

      @KeepFieldType
      ClubViewModel com_newton_admin_presentation_club_viewmodel_ClubViewModel2;

      @KeepFieldType
      CommunitySharedViewModel com_newton_communities_presentation_viewModel_CommunitySharedViewModel2;

      @KeepFieldType
      EventsSharedViewModel com_newton_events_presentation_viewmodel_EventsSharedViewModel2;

      @KeepFieldType
      GetTestimonialsViewModel com_newton_testimonials_presentation_viewModel_GetTestimonialsViewModel2;

      @KeepFieldType
      CommunityViewModel com_newton_admin_presentation_community_viewmodels_CommunityViewModel2;

      @KeepFieldType
      UpdateAccountViewModel com_newton_account_presentation_viewmodel_UpdateAccountViewModel2;

      @KeepFieldType
      EventRsvpViewmodel com_newton_events_presentation_viewmodel_EventRsvpViewmodel2;

      @KeepFieldType
      PartnersViewModel com_newton_partners_presentation_viewModel_PartnersViewModel2;

      @KeepFieldType
      AdminFeedbackViewModel com_newton_admin_presentation_feedbacks_viewmodel_AdminFeedbackViewModel2;

      @KeepFieldType
      UserFeedbackViewModel com_newton_feedback_presentation_viewmodel_UserFeedbackViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.newton.account.presentation.viewmodel.AccountManagementViewModel 
          return (T) new AccountManagementViewModel(singletonCImpl.provideAuthRepositoryProvider.get());

          case 1: // com.newton.admin.presentation.events.viewmodel.AddEventViewModel 
          return (T) new AddEventViewModel(singletonCImpl.provideEventRepositoryProvider.get());

          case 2: // com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel 
          return (T) new AdminFeedbackViewModel(singletonCImpl.provideEventRepositoryProvider.get());

          case 3: // com.newton.settings.presentation.viewModel.CacheManagerViewModel 
          return (T) new CacheManagerViewModel(singletonCImpl.cacheManagerProvider.get());

          case 4: // com.newton.communities.presentation.viewModel.ClubBioViewModel 
          return (T) new ClubBioViewModel(singletonCImpl.provideClubBioRepositoryProvider.get());

          case 5: // com.newton.admin.presentation.club.viewmodel.ClubViewModel 
          return (T) new ClubViewModel(singletonCImpl.provideEventRepositoryProvider.get(), singletonCImpl.provideClubBioRepositoryProvider.get());

          case 6: // com.newton.communities.presentation.viewModel.CommunitiesViewModel 
          return (T) new CommunitiesViewModel(singletonCImpl.provideAboutUsRepositoryProvider.get());

          case 7: // com.newton.communities.presentation.viewModel.CommunitySharedViewModel 
          return (T) new CommunitySharedViewModel();

          case 8: // com.newton.admin.presentation.community.viewmodels.CommunityViewModel 
          return (T) new CommunityViewModel(singletonCImpl.provideEventRepositoryProvider.get());

          case 9: // com.newton.testimonials.presentation.viewModel.CreateTestimonialViewModel 
          return (T) new CreateTestimonialViewModel(singletonCImpl.provideTestimonialsRepositoryProvider.get(), singletonCImpl.eventBusProvider.get());

          case 10: // com.newton.events.presentation.viewmodel.EventRsvpViewmodel 
          return (T) new EventRsvpViewmodel(singletonCImpl.provideEventRepositoryProvider2.get(), viewModelCImpl.eventRegistrationStateHolder(), activityRetainedCImpl.ticketStateBusProvider.get());

          case 11: // com.newton.events.presentation.viewmodel.EventViewModel 
          return (T) new EventViewModel(singletonCImpl.provideEventRepositoryProvider2.get());

          case 12: // com.newton.events.presentation.viewmodel.EventsSharedViewModel 
          return (T) new EventsSharedViewModel();

          case 13: // com.newton.admin.presentation.events.viewmodel.EventsViewModel 
          return (T) new EventsViewModel(singletonCImpl.provideEventRepositoryProvider.get(), singletonCImpl.provideEventRepositoryProvider2.get());

          case 14: // com.newton.admin.presentation.roleManagement.executives.viewModel.ExecutiveViewModel 
          return (T) new com.newton.admin.presentation.roleManagement.executives.viewModel.ExecutiveViewModel(singletonCImpl.provideEventRepositoryProvider.get());

          case 15: // com.newton.communities.presentation.viewModel.ExecutiveViewModel 
          return (T) new ExecutiveViewModel(singletonCImpl.provideExecutiveRepositoryProvider.get());

          case 16: // com.newton.auth.presentation.resetPassword.viewModel.ForgotPasswordViewModel 
          return (T) new ForgotPasswordViewModel(singletonCImpl.provideAuthRepositoryProvider.get());

          case 17: // com.newton.testimonials.presentation.viewModel.GetTestimonialsViewModel 
          return (T) new GetTestimonialsViewModel(singletonCImpl.provideTestimonialsRepositoryProvider.get(), singletonCImpl.eventBusProvider.get());

          case 18: // com.newton.auth.presentation.login.viewModel.GetUserDataViewModel 
          return (T) new GetUserDataViewModel(singletonCImpl.provideAuthRepositoryProvider.get());

          case 19: // com.newton.auth.presentation.login.viewModel.LoginViewModel 
          return (T) new LoginViewModel(viewModelCImpl.loginStateHolder(), singletonCImpl.provideAuthRepositoryProvider.get());

          case 20: // com.newton.admin.presentation.notification.viewmodel.NotificationsViewModel 
          return (T) new NotificationsViewModel(singletonCImpl.provideEventRepositoryProvider.get());

          case 21: // com.newton.partners.presentation.viewModel.PartnersSharedViewModel 
          return (T) new PartnersSharedViewModel();

          case 22: // com.newton.admin.presentation.partners.viewModel.PartnersViewModel 
          return (T) new com.newton.admin.presentation.partners.viewModel.PartnersViewModel(singletonCImpl.provideEventRepositoryProvider.get());

          case 23: // com.newton.partners.presentation.viewModel.PartnersViewModel 
          return (T) new PartnersViewModel(singletonCImpl.providePartnersRepositoryProvider.get());

          case 24: // com.newton.events.presentation.viewmodel.RsvpSharedViewModel 
          return (T) new RsvpSharedViewModel(activityRetainedCImpl.ticketStateBusProvider.get());

          case 25: // com.newton.auth.presentation.signUp.viewmodel.SignupViewModel 
          return (T) new SignupViewModel(viewModelCImpl.signupStateHolder(), singletonCImpl.provideAuthRepositoryProvider.get(), singletonCImpl.providePrefsRepositoryProvider.get());

          case 26: // com.newton.sharedprefs.viewModel.ThemeViewModel 
          return (T) new ThemeViewModel(singletonCImpl.providePrefsRepositoryProvider.get());

          case 27: // com.newton.account.presentation.viewmodel.UpdateAccountViewModel 
          return (T) new UpdateAccountViewModel(singletonCImpl.provideAuthRepositoryProvider.get(), singletonCImpl.provideAccountRepositoryProvider.get(), singletonCImpl.profileStateHolderProvider.get(), singletonCImpl.profileStateReducerProvider.get());

          case 28: // com.newton.admin.presentation.community.viewmodels.UpdateCommunityViewModel 
          return (T) new UpdateCommunityViewModel(singletonCImpl.provideEventRepositoryProvider.get(), singletonCImpl.provideAboutUsRepositoryProvider.get());

          case 29: // com.newton.admin.presentation.events.viewmodel.UpdateEventsViewModel 
          return (T) new UpdateEventsViewModel(singletonCImpl.provideEventRepositoryProvider.get());

          case 30: // com.newton.feedback.presentation.viewmodel.UserFeedbackViewModel 
          return (T) new UserFeedbackViewModel();

          case 31: // com.newton.events.presentation.viewmodel.UserTicketsViewModel 
          return (T) new UserTicketsViewModel(singletonCImpl.provideEventRepositoryProvider2.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends MeruInnovatorsApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private Provider<TicketStateBus> ticketStateBusProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
      this.ticketStateBusProvider = DoubleCheck.provider(new SwitchingProvider<TicketStateBus>(singletonCImpl, activityRetainedCImpl, 1));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          case 1: // com.newton.events.presentation.viewmodel.TicketStateBus 
          return (T) new TicketStateBus();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends MeruInnovatorsApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectFCMService(FCMService arg0) {
      injectFCMService2(arg0);
    }

    @CanIgnoreReturnValue
    private FCMService injectFCMService2(FCMService instance) {
      FCMService_MembersInjector.injectNotificationsManager(instance, singletonCImpl.notificationsManagerProvider.get());
      FCMService_MembersInjector.injectFcmTokenRepository(instance, singletonCImpl.fCMTokenRepository());
      return instance;
    }
  }

  private static final class SingletonCImpl extends MeruInnovatorsApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<OkHttpClient> provideHttpClientProvider;

    private Provider<Retrofit> provideMeruInnovatorsApiProvider;

    private Provider<AuthService> provideAuthServiceProvider;

    private Provider<AppDatabase> provideAppDatabaseProvider;

    private Provider<UserDao> provideUserDaoProvider;

    private Provider<AuthRepository> provideAuthRepositoryProvider;

    private Provider<ImageLoader> provideImageLoaderProvider;

    private Provider<Context> provideContextProvider;

    private Provider<NotificationApiService> provideNotificationApiServiceProvider;

    private Provider<NotificationsManager> notificationsManagerProvider;

    private Provider<EventsNavigationApi> provideEventNavApiProvider;

    private Provider<HomeNavigationApi> provideHomeNavigationApiProvider;

    private Provider<BlogsNavigationApi> provideEventNavApiProvider2;

    private Provider<AccountNavigationApi> provideEventNavApiProvider3;

    private Provider<AdminNavigationApi> provideEventNavApiProvider4;

    private Provider<AboutUsNavigationApi> provideCommunityApiProvider;

    private Provider<FeedbackNavigationApi> provideUserFeedbackNavigationApiProvider;

    private Provider<SettingNavigationApi> provideSettingsNavigationApiProvider;

    private Provider<CommunityNavigationApi> provideCommunityNavigationApiProvider;

    private Provider<TestimonialsNavigationApi> provideTestimonialsNavigationApiProvider;

    private Provider<PartnersNavigationApi> providePartnersNavigationApiProvider;

    private Provider<AdminApi> providesEventApiProvider;

    private Provider<EventDao> provideEventDaoProvider;

    private Provider<UserFeedbackDao> provideUserFeedbackDaoProvider;

    private Provider<EventsFeedbackDao> provideEventsFeedbackDaoProvider;

    private Provider<PartnersDao> providePartnersDaoProvider;

    private Provider<AdminRepository> provideEventRepositoryProvider;

    private Provider<CacheManager> cacheManagerProvider;

    private Provider<AboutClubService> provideAboutUsApiProvider;

    private Provider<ClubBioDao> provideClubBioDaoProvider;

    private Provider<ClubBioRepository> provideClubBioRepositoryProvider;

    private Provider<CommunitiesApiService> provideAboutUsApiProvider2;

    private Provider<CommunityRepository> provideAboutUsRepositoryProvider;

    private Provider<TestimonialsService> provideTestimonialApiProvider;

    private Provider<TestimonialsDao> provideTestimonialsDaoProvider;

    private Provider<TestimonialsRepository> provideTestimonialsRepositoryProvider;

    private Provider<EventBus> eventBusProvider;

    private Provider<EventService> providesEventApiProvider2;

    private Provider<TicketDao> provideTicketDaoProvider;

    private Provider<EventRepository> provideEventRepositoryProvider2;

    private Provider<ExecutiveRepository> provideExecutiveRepositoryProvider;

    private Provider<PartnersService> providePartnersApiServiceProvider;

    private Provider<PartnersRepository> providePartnersRepositoryProvider;

    private Provider<PreferenceManager> providePrefsManagerProvider;

    private Provider<PrefsRepository> providePrefsRepositoryProvider;

    private Provider<UpdateUserRepository> provideAccountRepositoryProvider;

    private Provider<ProfileFormValidator> profileFormValidatorProvider;

    private Provider<ProfileStateHolder> profileStateHolderProvider;

    private Provider<ProfileStateReducer> profileStateReducerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private SessionManager sessionManager() {
      return new SessionManager(ApplicationContextModule_ProvideContextFactory.provideContext(applicationContextModule));
    }

    private RoomDataManager roomDataManager() {
      return new RoomDataManager(provideAppDatabaseProvider.get());
    }

    private NotificationService notificationService() {
      return new NotificationService(provideContextProvider.get());
    }

    private FCMTokenRepository fCMTokenRepository() {
      return new FCMTokenRepository(provideContextProvider.get(), provideNotificationApiServiceProvider.get(), provideUserDaoProvider.get());
    }

    private NavigationSubGraphs navigationSubGraphs() {
      return AppModule_ProvideNavigationSubGraphsFactory.provideNavigationSubGraphs(AuthNavModule_ProvideAuthNavApiFactory.provideAuthNavApi(), provideEventNavApiProvider.get(), provideHomeNavigationApiProvider.get(), provideEventNavApiProvider2.get(), provideEventNavApiProvider3.get(), provideEventNavApiProvider4.get(), provideCommunityApiProvider.get(), provideUserFeedbackNavigationApiProvider.get(), provideSettingsNavigationApiProvider.get(), provideCommunityNavigationApiProvider.get(), provideTestimonialsNavigationApiProvider.get(), providePartnersNavigationApiProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 3));
      this.provideMeruInnovatorsApiProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 2));
      this.provideAuthServiceProvider = DoubleCheck.provider(new SwitchingProvider<AuthService>(singletonCImpl, 1));
      this.provideAppDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 4));
      this.provideUserDaoProvider = DoubleCheck.provider(new SwitchingProvider<UserDao>(singletonCImpl, 5));
      this.provideAuthRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 0));
      this.provideImageLoaderProvider = DoubleCheck.provider(new SwitchingProvider<ImageLoader>(singletonCImpl, 6));
      this.provideContextProvider = DoubleCheck.provider(new SwitchingProvider<Context>(singletonCImpl, 8));
      this.provideNotificationApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<NotificationApiService>(singletonCImpl, 9));
      this.notificationsManagerProvider = DoubleCheck.provider(new SwitchingProvider<NotificationsManager>(singletonCImpl, 7));
      this.provideEventNavApiProvider = DoubleCheck.provider(new SwitchingProvider<EventsNavigationApi>(singletonCImpl, 10));
      this.provideHomeNavigationApiProvider = DoubleCheck.provider(new SwitchingProvider<HomeNavigationApi>(singletonCImpl, 11));
      this.provideEventNavApiProvider2 = DoubleCheck.provider(new SwitchingProvider<BlogsNavigationApi>(singletonCImpl, 12));
      this.provideEventNavApiProvider3 = DoubleCheck.provider(new SwitchingProvider<AccountNavigationApi>(singletonCImpl, 13));
      this.provideEventNavApiProvider4 = DoubleCheck.provider(new SwitchingProvider<AdminNavigationApi>(singletonCImpl, 14));
      this.provideCommunityApiProvider = DoubleCheck.provider(new SwitchingProvider<AboutUsNavigationApi>(singletonCImpl, 15));
      this.provideUserFeedbackNavigationApiProvider = DoubleCheck.provider(new SwitchingProvider<FeedbackNavigationApi>(singletonCImpl, 16));
      this.provideSettingsNavigationApiProvider = DoubleCheck.provider(new SwitchingProvider<SettingNavigationApi>(singletonCImpl, 17));
      this.provideCommunityNavigationApiProvider = DoubleCheck.provider(new SwitchingProvider<CommunityNavigationApi>(singletonCImpl, 18));
      this.provideTestimonialsNavigationApiProvider = DoubleCheck.provider(new SwitchingProvider<TestimonialsNavigationApi>(singletonCImpl, 19));
      this.providePartnersNavigationApiProvider = DoubleCheck.provider(new SwitchingProvider<PartnersNavigationApi>(singletonCImpl, 20));
      this.providesEventApiProvider = DoubleCheck.provider(new SwitchingProvider<AdminApi>(singletonCImpl, 22));
      this.provideEventDaoProvider = DoubleCheck.provider(new SwitchingProvider<EventDao>(singletonCImpl, 23));
      this.provideUserFeedbackDaoProvider = DoubleCheck.provider(new SwitchingProvider<UserFeedbackDao>(singletonCImpl, 24));
      this.provideEventsFeedbackDaoProvider = DoubleCheck.provider(new SwitchingProvider<EventsFeedbackDao>(singletonCImpl, 25));
      this.providePartnersDaoProvider = DoubleCheck.provider(new SwitchingProvider<PartnersDao>(singletonCImpl, 26));
      this.provideEventRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AdminRepository>(singletonCImpl, 21));
      this.cacheManagerProvider = DoubleCheck.provider(new SwitchingProvider<CacheManager>(singletonCImpl, 27));
      this.provideAboutUsApiProvider = DoubleCheck.provider(new SwitchingProvider<AboutClubService>(singletonCImpl, 29));
      this.provideClubBioDaoProvider = DoubleCheck.provider(new SwitchingProvider<ClubBioDao>(singletonCImpl, 30));
      this.provideClubBioRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ClubBioRepository>(singletonCImpl, 28));
      this.provideAboutUsApiProvider2 = DoubleCheck.provider(new SwitchingProvider<CommunitiesApiService>(singletonCImpl, 32));
      this.provideAboutUsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<CommunityRepository>(singletonCImpl, 31));
      this.provideTestimonialApiProvider = DoubleCheck.provider(new SwitchingProvider<TestimonialsService>(singletonCImpl, 34));
      this.provideTestimonialsDaoProvider = DoubleCheck.provider(new SwitchingProvider<TestimonialsDao>(singletonCImpl, 35));
      this.provideTestimonialsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<TestimonialsRepository>(singletonCImpl, 33));
      this.eventBusProvider = DoubleCheck.provider(new SwitchingProvider<EventBus>(singletonCImpl, 36));
      this.providesEventApiProvider2 = DoubleCheck.provider(new SwitchingProvider<EventService>(singletonCImpl, 38));
      this.provideTicketDaoProvider = DoubleCheck.provider(new SwitchingProvider<TicketDao>(singletonCImpl, 39));
      this.provideEventRepositoryProvider2 = DoubleCheck.provider(new SwitchingProvider<EventRepository>(singletonCImpl, 37));
      this.provideExecutiveRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<ExecutiveRepository>(singletonCImpl, 40));
      this.providePartnersApiServiceProvider = DoubleCheck.provider(new SwitchingProvider<PartnersService>(singletonCImpl, 42));
      this.providePartnersRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<PartnersRepository>(singletonCImpl, 41));
      this.providePrefsManagerProvider = DoubleCheck.provider(new SwitchingProvider<PreferenceManager>(singletonCImpl, 44));
      this.providePrefsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<PrefsRepository>(singletonCImpl, 43));
      this.provideAccountRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<UpdateUserRepository>(singletonCImpl, 45));
      this.profileFormValidatorProvider = DoubleCheck.provider(new SwitchingProvider<ProfileFormValidator>(singletonCImpl, 47));
      this.profileStateHolderProvider = DoubleCheck.provider(new SwitchingProvider<ProfileStateHolder>(singletonCImpl, 46));
      this.profileStateReducerProvider = DoubleCheck.provider(new SwitchingProvider<ProfileStateReducer>(singletonCImpl, 48));
    }

    @Override
    public AuthRepository authRepository() {
      return provideAuthRepositoryProvider.get();
    }

    @Override
    public void injectMeruInnovatorsApp(MeruInnovatorsApp arg0) {
      injectMeruInnovatorsApp2(arg0);
    }

    @Override
    public NotificationsManager notificationManager() {
      return notificationsManagerProvider.get();
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @CanIgnoreReturnValue
    private MeruInnovatorsApp injectMeruInnovatorsApp2(MeruInnovatorsApp instance) {
      MeruInnovatorsApp_MembersInjector.injectWorkerFactory(instance, new TokenRefreshWorkerFactory());
      MeruInnovatorsApp_MembersInjector.injectImageLoader(instance, provideImageLoaderProvider.get());
      MeruInnovatorsApp_MembersInjector.injectNotificationsManager(instance, notificationsManagerProvider.get());
      MeruInnovatorsApp_MembersInjector.injectAuthRepository(instance, provideAuthRepositoryProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.newton.network.domain.repositories.AuthRepository 
          return (T) AuthRepositoryModule_ProvideAuthRepositoryFactory.provideAuthRepository(singletonCImpl.provideAuthServiceProvider.get(), singletonCImpl.sessionManager(), singletonCImpl.roomDataManager(), singletonCImpl.provideUserDaoProvider.get());

          case 1: // com.newton.network.data.remote.AuthService 
          return (T) AuthServiceModule_ProvideAuthServiceFactory.provideAuthService(singletonCImpl.provideMeruInnovatorsApiProvider.get());

          case 2: // retrofit2.Retrofit 
          return (T) AppModule_ProvideMeruInnovatorsApiFactory.provideMeruInnovatorsApi(singletonCImpl.provideHttpClientProvider.get());

          case 3: // okhttp3.OkHttpClient 
          return (T) AppModule_ProvideHttpClientFactory.provideHttpClient(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.newton.database.db.AppDatabase 
          return (T) AppDatabaseModule_ProvideAppDatabaseFactory.provideAppDatabase(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonCImpl.applicationContextModule));

          case 5: // com.newton.database.dao.UserDao 
          return (T) AppDatabaseModule_ProvideUserDaoFactory.provideUserDao(singletonCImpl.provideAppDatabaseProvider.get());

          case 6: // coil3.ImageLoader 
          return (T) CoilModule_ProvideImageLoaderFactory.provideImageLoader(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.newton.notifications.manager.NotificationsManager 
          return (T) new NotificationsManager(singletonCImpl.provideContextProvider.get(), singletonCImpl.notificationService(), singletonCImpl.fCMTokenRepository());

          case 8: // android.content.Context 
          return (T) AppModule_ProvideContextFactory.provideContext(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.newton.notifications.data.remote.NotificationApiService 
          return (T) NotificationModule_ProvideNotificationApiServiceFactory.provideNotificationApiService(singletonCImpl.provideMeruInnovatorsApiProvider.get());

          case 10: // com.newton.events.navigation.EventsNavigationApi 
          return (T) EventNavigationModule_ProvideEventNavApiFactory.provideEventNavApi();

          case 11: // com.newton.home.navigation.HomeNavigationApi 
          return (T) HomeNavigationModule_ProvideHomeNavigationApiFactory.provideHomeNavigationApi();

          case 12: // com.newton.blogs.navigation.BlogsNavigationApi 
          return (T) BlogsModule_ProvideEventNavApiFactory.provideEventNavApi();

          case 13: // com.newton.account.navigation.AccountNavigationApi 
          return (T) AccountNavigationModule_ProvideEventNavApiFactory.provideEventNavApi();

          case 14: // com.newton.admin.navigation.AdminNavigationApi 
          return (T) AdminModule_ProvideEventNavApiFactory.provideEventNavApi();

          case 15: // com.newton.communities.navigation.AboutUsNavigationApi 
          return (T) AboutUsNavigationModule_ProvideCommunityApiFactory.provideCommunityApi();

          case 16: // com.newton.feedback.navigation.FeedbackNavigationApi 
          return (T) UserFeedbackNavigationModule_ProvideUserFeedbackNavigationApiFactory.provideUserFeedbackNavigationApi();

          case 17: // com.newton.settings.navigation.SettingNavigationApi 
          return (T) SettingsNavigationModule_ProvideSettingsNavigationApiFactory.provideSettingsNavigationApi();

          case 18: // com.newton.communities.navigation.CommunityNavigationApi 
          return (T) CommunityNavigationModule_ProvideCommunityNavigationApiFactory.provideCommunityNavigationApi();

          case 19: // com.newton.testimonials.navigation.TestimonialsNavigationApi 
          return (T) TestimonialsNavigationModule_ProvideTestimonialsNavigationApiFactory.provideTestimonialsNavigationApi();

          case 20: // com.newton.partners.navigation.PartnersNavigationApi 
          return (T) PartnersNavigationModule_ProvidePartnersNavigationApiFactory.providePartnersNavigationApi();

          case 21: // com.newton.network.domain.repositories.AdminRepository 
          return (T) AdminModule_ProvideEventRepositoryFactory.provideEventRepository(singletonCImpl.providesEventApiProvider.get(), singletonCImpl.provideEventDaoProvider.get(), singletonCImpl.provideUserFeedbackDaoProvider.get(), singletonCImpl.provideEventsFeedbackDaoProvider.get(), singletonCImpl.providePartnersDaoProvider.get());

          case 22: // com.newton.network.data.remote.AdminApi 
          return (T) AdminModule_ProvidesEventApiFactory.providesEventApi(singletonCImpl.provideMeruInnovatorsApiProvider.get());

          case 23: // com.newton.database.dao.EventDao 
          return (T) AppDatabaseModule_ProvideEventDaoFactory.provideEventDao(singletonCImpl.provideAppDatabaseProvider.get());

          case 24: // com.newton.database.dao.UserFeedbackDao 
          return (T) AppDatabaseModule_ProvideUserFeedbackDaoFactory.provideUserFeedbackDao(singletonCImpl.provideAppDatabaseProvider.get());

          case 25: // com.newton.database.dao.EventsFeedbackDao 
          return (T) AppDatabaseModule_ProvideEventsFeedbackDaoFactory.provideEventsFeedbackDao(singletonCImpl.provideAppDatabaseProvider.get());

          case 26: // com.newton.database.dao.PartnersDao 
          return (T) AppDatabaseModule_ProvidePartnersDaoFactory.providePartnersDao(singletonCImpl.provideAppDatabaseProvider.get());

          case 27: // com.newton.database.dbManager.CacheManager 
          return (T) new CacheManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideImageLoaderProvider.get());

          case 28: // com.newton.network.domain.repositories.ClubBioRepository 
          return (T) AboutUsRepositoryModule_ProvideClubBioRepositoryFactory.provideClubBioRepository(singletonCImpl.provideAboutUsApiProvider.get(), singletonCImpl.provideClubBioDaoProvider.get());

          case 29: // com.newton.network.data.remote.AboutClubService 
          return (T) AboutUsApiModule_ProvideAboutUsApiFactory.provideAboutUsApi(singletonCImpl.provideMeruInnovatorsApiProvider.get());

          case 30: // com.newton.database.dao.ClubBioDao 
          return (T) AppDatabaseModule_ProvideClubBioDaoFactory.provideClubBioDao(singletonCImpl.provideAppDatabaseProvider.get());

          case 31: // com.newton.network.domain.repositories.CommunityRepository 
          return (T) CommunityRepositoryModule_ProvideAboutUsRepositoryFactory.provideAboutUsRepository(singletonCImpl.provideAboutUsApiProvider2.get());

          case 32: // com.newton.network.data.remote.CommunitiesApiService 
          return (T) CommunityApiModule_ProvideAboutUsApiFactory.provideAboutUsApi(singletonCImpl.provideMeruInnovatorsApiProvider.get());

          case 33: // com.newton.network.domain.repositories.TestimonialsRepository 
          return (T) TestimonialsRepositoryModule_ProvideTestimonialsRepositoryFactory.provideTestimonialsRepository(singletonCImpl.provideTestimonialApiProvider.get(), singletonCImpl.provideTestimonialsDaoProvider.get());

          case 34: // com.newton.network.data.remote.TestimonialsService 
          return (T) TestimonialsApiModule_ProvideTestimonialApiFactory.provideTestimonialApi(singletonCImpl.provideMeruInnovatorsApiProvider.get());

          case 35: // com.newton.database.dao.TestimonialsDao 
          return (T) AppDatabaseModule_ProvideTestimonialsDaoFactory.provideTestimonialsDao(singletonCImpl.provideAppDatabaseProvider.get());

          case 36: // com.newton.shared.sharedBus.EventBus 
          return (T) new EventBus();

          case 37: // com.newton.network.domain.repositories.EventRepository 
          return (T) EventRepositoryModule_ProvideEventRepositoryFactory.provideEventRepository(singletonCImpl.providesEventApiProvider2.get(), singletonCImpl.provideAppDatabaseProvider.get(), singletonCImpl.provideTicketDaoProvider.get(), singletonCImpl.provideEventDaoProvider.get());

          case 38: // com.newton.network.data.remote.EventService 
          return (T) EventApiModule_ProvidesEventApiFactory.providesEventApi(singletonCImpl.provideMeruInnovatorsApiProvider.get());

          case 39: // com.newton.database.dao.TicketDao 
          return (T) AppDatabaseModule_ProvideTicketDaoFactory.provideTicketDao(singletonCImpl.provideAppDatabaseProvider.get());

          case 40: // com.newton.network.domain.repositories.ExecutiveRepository 
          return (T) AboutUsRepositoryModule_ProvideExecutiveRepositoryFactory.provideExecutiveRepository(singletonCImpl.provideAboutUsApiProvider.get());

          case 41: // com.newton.network.domain.repositories.PartnersRepository 
          return (T) PartnersRepositoryModule_ProvidePartnersRepositoryFactory.providePartnersRepository(singletonCImpl.providePartnersApiServiceProvider.get());

          case 42: // com.newton.network.data.remote.PartnersService 
          return (T) PartnersServiceModule_ProvidePartnersApiServiceFactory.providePartnersApiService(singletonCImpl.provideMeruInnovatorsApiProvider.get());

          case 43: // com.newton.sharedprefs.domain.PrefsRepository 
          return (T) PrefsModule_ProvidePrefsRepositoryFactory.providePrefsRepository(singletonCImpl.providePrefsManagerProvider.get());

          case 44: // com.newton.sharedprefs.prefsManager.PreferenceManager 
          return (T) PrefsModule_ProvidePrefsManagerFactory.providePrefsManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 45: // com.newton.network.domain.repositories.UpdateUserRepository 
          return (T) AccountRepositoryModule_ProvideAccountRepositoryFactory.provideAccountRepository(singletonCImpl.provideUserDaoProvider.get(), singletonCImpl.provideAuthServiceProvider.get());

          case 46: // com.newton.account.presentation.viewmodel.ProfileStateHolder 
          return (T) new ProfileStateHolder(singletonCImpl.profileFormValidatorProvider.get());

          case 47: // com.newton.account.presentation.viewmodel.ProfileFormValidator 
          return (T) new ProfileFormValidator();

          case 48: // com.newton.account.presentation.viewmodel.ProfileStateReducer 
          return (T) new ProfileStateReducer();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
