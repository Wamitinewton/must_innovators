package com.newton.meruinnovators.application;

import androidx.hilt.work.HiltWrapper_WorkerFactoryModule;
import com.newton.account.di.AccountNavigationModule;
import com.newton.account.di.AccountRepositoryModule;
import com.newton.account.presentation.viewmodel.AccountManagementViewModel_HiltModules;
import com.newton.account.presentation.viewmodel.UpdateAccountViewModel_HiltModules;
import com.newton.admin.di.AdminModule;
import com.newton.admin.presentation.club.viewmodel.ClubViewModel_HiltModules;
import com.newton.admin.presentation.community.viewmodels.CommunityViewModel_HiltModules;
import com.newton.admin.presentation.community.viewmodels.UpdateCommunityViewModel_HiltModules;
import com.newton.admin.presentation.events.viewmodel.AddEventViewModel_HiltModules;
import com.newton.admin.presentation.events.viewmodel.EventsViewModel_HiltModules;
import com.newton.admin.presentation.events.viewmodel.UpdateEventsViewModel_HiltModules;
import com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel_HiltModules;
import com.newton.admin.presentation.notification.viewmodel.NotificationsViewModel_HiltModules;
import com.newton.admin.presentation.partners.viewModel.PartnersViewModel_HiltModules;
import com.newton.admin.presentation.roleManagement.executives.viewModel.ExecutiveViewModel_HiltModules;
import com.newton.auth.data.workManager.TokenRefreshWorkerFactoryEntryPoint;
import com.newton.auth.di.AuthNavModule;
import com.newton.auth.di.AuthRepositoryModule;
import com.newton.auth.presentation.login.viewModel.GetUserDataViewModel_HiltModules;
import com.newton.auth.presentation.login.viewModel.LoginViewModel_HiltModules;
import com.newton.auth.presentation.resetPassword.viewModel.ForgotPasswordViewModel_HiltModules;
import com.newton.auth.presentation.signUp.viewmodel.SignupViewModel_HiltModules;
import com.newton.communities.di.AboutUsNavigationModule;
import com.newton.communities.di.AboutUsRepositoryModule;
import com.newton.communities.di.CommunityNavigationModule;
import com.newton.communities.di.CommunityRepositoryModule;
import com.newton.communities.presentation.viewModel.ClubBioViewModel_HiltModules;
import com.newton.communities.presentation.viewModel.CommunitiesViewModel_HiltModules;
import com.newton.communities.presentation.viewModel.CommunitySharedViewModel_HiltModules;
import com.newton.database.di.AppDatabaseModule;
import com.newton.events.di.EventNavigationModule;
import com.newton.events.di.EventRepositoryModule;
import com.newton.events.presentation.viewmodel.EventRsvpViewmodel_HiltModules;
import com.newton.events.presentation.viewmodel.EventViewModel_HiltModules;
import com.newton.events.presentation.viewmodel.EventsSharedViewModel_HiltModules;
import com.newton.events.presentation.viewmodel.RsvpSharedViewModel_HiltModules;
import com.newton.events.presentation.viewmodel.UserTicketsViewModel_HiltModules;
import com.newton.feedback.di.UserFeedbackNavigationModule;
import com.newton.feedback.presentation.viewmodel.UserFeedbackViewModel_HiltModules;
import com.newton.home.di.BlogsModule;
import com.newton.home.di.HomeNavigationModule;
import com.newton.meruinnovators.activity.MainActivity_GeneratedInjector;
import com.newton.meruinnovators.di.AppModule;
import com.newton.meruinnovators.di.CoilModule;
import com.newton.network.di.AboutUsApiModule;
import com.newton.network.di.AuthServiceModule;
import com.newton.network.di.CommunityApiModule;
import com.newton.network.di.EventApiModule;
import com.newton.network.di.PartnersServiceModule;
import com.newton.network.di.TestimonialsApiModule;
import com.newton.notifications.di.NotificationModule;
import com.newton.notifications.initializer.NotificationsInitializer;
import com.newton.notifications.services.FCMService_GeneratedInjector;
import com.newton.partners.di.PartnersNavigationModule;
import com.newton.partners.di.PartnersRepositoryModule;
import com.newton.partners.presentation.viewModel.PartnersSharedViewModel_HiltModules;
import com.newton.settings.di.SettingsNavigationModule;
import com.newton.settings.presentation.viewModel.CacheManagerViewModel_HiltModules;
import com.newton.sharedprefs.di.PrefsModule;
import com.newton.sharedprefs.viewModel.ThemeViewModel_HiltModules;
import com.newton.testimonials.di.TestimonialsNavigationModule;
import com.newton.testimonials.di.TestimonialsRepositoryModule;
import com.newton.testimonials.presentation.viewModel.CreateTestimonialViewModel_HiltModules;
import com.newton.testimonials.presentation.viewModel.GetTestimonialsViewModel_HiltModules;
import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.android.components.ViewComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.components.ViewWithFragmentComponent;
import dagger.hilt.android.flags.FragmentGetContextFix;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_DefaultViewModelFactories_ActivityModule;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ViewModelModule;
import dagger.hilt.android.internal.managers.ActivityComponentManager;
import dagger.hilt.android.internal.managers.FragmentComponentManager;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_LifecycleModule;
import dagger.hilt.android.internal.managers.HiltWrapper_SavedStateHandleModule;
import dagger.hilt.android.internal.managers.ServiceComponentManager;
import dagger.hilt.android.internal.managers.ViewComponentManager;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.HiltWrapper_ActivityModule;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.FragmentScoped;
import dagger.hilt.android.scopes.ServiceScoped;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.android.scopes.ViewScoped;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedComponent;
import dagger.hilt.migration.DisableInstallInCheck;
import javax.annotation.processing.Generated;
import javax.inject.Singleton;

@Generated("dagger.hilt.processor.internal.root.RootProcessor")
public final class MeruInnovatorsApp_HiltComponents {
  private MeruInnovatorsApp_HiltComponents() {
  }

  @Module(
      subcomponents = ServiceC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ServiceCBuilderModule {
    @Binds
    ServiceComponentBuilder bind(ServiceC.Builder builder);
  }

  @Module(
      subcomponents = ActivityRetainedC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ActivityRetainedCBuilderModule {
    @Binds
    ActivityRetainedComponentBuilder bind(ActivityRetainedC.Builder builder);
  }

  @Module(
      subcomponents = ActivityC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ActivityCBuilderModule {
    @Binds
    ActivityComponentBuilder bind(ActivityC.Builder builder);
  }

  @Module(
      subcomponents = ViewModelC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewModelCBuilderModule {
    @Binds
    ViewModelComponentBuilder bind(ViewModelC.Builder builder);
  }

  @Module(
      subcomponents = ViewC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewCBuilderModule {
    @Binds
    ViewComponentBuilder bind(ViewC.Builder builder);
  }

  @Module(
      subcomponents = FragmentC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface FragmentCBuilderModule {
    @Binds
    FragmentComponentBuilder bind(FragmentC.Builder builder);
  }

  @Module(
      subcomponents = ViewWithFragmentC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewWithFragmentCBuilderModule {
    @Binds
    ViewWithFragmentComponentBuilder bind(ViewWithFragmentC.Builder builder);
  }

  @Component(
      modules = {
          AboutUsApiModule.class,
          AboutUsNavigationModule.class,
          AboutUsRepositoryModule.class,
          AccountNavigationModule.class,
          AccountRepositoryModule.class,
          AdminModule.class,
          AppDatabaseModule.class,
          AppModule.class,
          ApplicationContextModule.class,
          AuthNavModule.class,
          AuthRepositoryModule.class,
          AuthServiceModule.class,
          BlogsModule.class,
          CoilModule.class,
          CommunityApiModule.class,
          CommunityNavigationModule.class,
          CommunityRepositoryModule.class,
          EventApiModule.class,
          EventNavigationModule.class,
          EventRepositoryModule.class,
          HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule.class,
          HiltWrapper_WorkerFactoryModule.class,
          HomeNavigationModule.class,
          ActivityRetainedCBuilderModule.class,
          ServiceCBuilderModule.class,
          NotificationModule.class,
          PartnersNavigationModule.class,
          PartnersRepositoryModule.class,
          PartnersServiceModule.class,
          PrefsModule.class,
          SettingsNavigationModule.class,
          TestimonialsApiModule.class,
          TestimonialsNavigationModule.class,
          TestimonialsRepositoryModule.class,
          UserFeedbackNavigationModule.class
      }
  )
  @Singleton
  public abstract static class SingletonC implements TokenRefreshWorkerFactoryEntryPoint,
      MeruInnovatorsApp_GeneratedInjector,
      NotificationsInitializer.NotificationInitializerEntryPoint,
      FragmentGetContextFix.FragmentGetContextFixEntryPoint,
      HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint,
      ServiceComponentManager.ServiceComponentBuilderEntryPoint,
      SingletonComponent,
      GeneratedComponent {
  }

  @Subcomponent
  @ServiceScoped
  public abstract static class ServiceC implements FCMService_GeneratedInjector,
      ServiceComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ServiceComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          AccountManagementViewModel_HiltModules.KeyModule.class,
          AddEventViewModel_HiltModules.KeyModule.class,
          AdminFeedbackViewModel_HiltModules.KeyModule.class,
          CacheManagerViewModel_HiltModules.KeyModule.class,
          ClubBioViewModel_HiltModules.KeyModule.class,
          ClubViewModel_HiltModules.KeyModule.class,
          CommunitiesViewModel_HiltModules.KeyModule.class,
          CommunitySharedViewModel_HiltModules.KeyModule.class,
          CommunityViewModel_HiltModules.KeyModule.class,
          CreateTestimonialViewModel_HiltModules.KeyModule.class,
          EventRsvpViewmodel_HiltModules.KeyModule.class,
          EventViewModel_HiltModules.KeyModule.class,
          EventsSharedViewModel_HiltModules.KeyModule.class,
          EventsViewModel_HiltModules.KeyModule.class,
          ExecutiveViewModel_HiltModules.KeyModule.class,
          com.newton.communities.presentation.viewModel.ExecutiveViewModel_HiltModules.KeyModule.class,
          ForgotPasswordViewModel_HiltModules.KeyModule.class,
          GetTestimonialsViewModel_HiltModules.KeyModule.class,
          GetUserDataViewModel_HiltModules.KeyModule.class,
          HiltWrapper_ActivityRetainedComponentManager_LifecycleModule.class,
          HiltWrapper_SavedStateHandleModule.class,
          LoginViewModel_HiltModules.KeyModule.class,
          ActivityCBuilderModule.class,
          ViewModelCBuilderModule.class,
          NotificationsViewModel_HiltModules.KeyModule.class,
          PartnersSharedViewModel_HiltModules.KeyModule.class,
          PartnersViewModel_HiltModules.KeyModule.class,
          com.newton.partners.presentation.viewModel.PartnersViewModel_HiltModules.KeyModule.class,
          RsvpSharedViewModel_HiltModules.KeyModule.class,
          SignupViewModel_HiltModules.KeyModule.class,
          ThemeViewModel_HiltModules.KeyModule.class,
          UpdateAccountViewModel_HiltModules.KeyModule.class,
          UpdateCommunityViewModel_HiltModules.KeyModule.class,
          UpdateEventsViewModel_HiltModules.KeyModule.class,
          UserFeedbackViewModel_HiltModules.KeyModule.class,
          UserTicketsViewModel_HiltModules.KeyModule.class
      }
  )
  @ActivityRetainedScoped
  public abstract static class ActivityRetainedC implements ActivityRetainedComponent,
      ActivityComponentManager.ActivityComponentBuilderEntryPoint,
      HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ActivityRetainedComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          HiltWrapper_ActivityModule.class,
          HiltWrapper_DefaultViewModelFactories_ActivityModule.class,
          FragmentCBuilderModule.class,
          ViewCBuilderModule.class
      }
  )
  @ActivityScoped
  public abstract static class ActivityC implements MainActivity_GeneratedInjector,
      ActivityComponent,
      DefaultViewModelFactories.ActivityEntryPoint,
      HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint,
      FragmentComponentManager.FragmentComponentBuilderEntryPoint,
      ViewComponentManager.ViewComponentBuilderEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ActivityComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          AccountManagementViewModel_HiltModules.BindsModule.class,
          AddEventViewModel_HiltModules.BindsModule.class,
          AdminFeedbackViewModel_HiltModules.BindsModule.class,
          CacheManagerViewModel_HiltModules.BindsModule.class,
          ClubBioViewModel_HiltModules.BindsModule.class,
          ClubViewModel_HiltModules.BindsModule.class,
          CommunitiesViewModel_HiltModules.BindsModule.class,
          CommunitySharedViewModel_HiltModules.BindsModule.class,
          CommunityViewModel_HiltModules.BindsModule.class,
          CreateTestimonialViewModel_HiltModules.BindsModule.class,
          EventRsvpViewmodel_HiltModules.BindsModule.class,
          EventViewModel_HiltModules.BindsModule.class,
          EventsSharedViewModel_HiltModules.BindsModule.class,
          EventsViewModel_HiltModules.BindsModule.class,
          ExecutiveViewModel_HiltModules.BindsModule.class,
          com.newton.communities.presentation.viewModel.ExecutiveViewModel_HiltModules.BindsModule.class,
          ForgotPasswordViewModel_HiltModules.BindsModule.class,
          GetTestimonialsViewModel_HiltModules.BindsModule.class,
          GetUserDataViewModel_HiltModules.BindsModule.class,
          HiltWrapper_HiltViewModelFactory_ViewModelModule.class,
          LoginViewModel_HiltModules.BindsModule.class,
          NotificationsViewModel_HiltModules.BindsModule.class,
          PartnersSharedViewModel_HiltModules.BindsModule.class,
          PartnersViewModel_HiltModules.BindsModule.class,
          com.newton.partners.presentation.viewModel.PartnersViewModel_HiltModules.BindsModule.class,
          RsvpSharedViewModel_HiltModules.BindsModule.class,
          SignupViewModel_HiltModules.BindsModule.class,
          ThemeViewModel_HiltModules.BindsModule.class,
          UpdateAccountViewModel_HiltModules.BindsModule.class,
          UpdateCommunityViewModel_HiltModules.BindsModule.class,
          UpdateEventsViewModel_HiltModules.BindsModule.class,
          UserFeedbackViewModel_HiltModules.BindsModule.class,
          UserTicketsViewModel_HiltModules.BindsModule.class
      }
  )
  @ViewModelScoped
  public abstract static class ViewModelC implements ViewModelComponent,
      HiltViewModelFactory.ViewModelFactoriesEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewModelComponentBuilder {
    }
  }

  @Subcomponent
  @ViewScoped
  public abstract static class ViewC implements ViewComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewComponentBuilder {
    }
  }

  @Subcomponent(
      modules = ViewWithFragmentCBuilderModule.class
  )
  @FragmentScoped
  public abstract static class FragmentC implements FragmentComponent,
      DefaultViewModelFactories.FragmentEntryPoint,
      ViewComponentManager.ViewWithFragmentComponentBuilderEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends FragmentComponentBuilder {
    }
  }

  @Subcomponent
  @ViewScoped
  public abstract static class ViewWithFragmentC implements ViewWithFragmentComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewWithFragmentComponentBuilder {
    }
  }
}
