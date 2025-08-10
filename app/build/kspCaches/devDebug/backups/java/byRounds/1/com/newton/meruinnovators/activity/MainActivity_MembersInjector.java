package com.newton.meruinnovators.activity;

import com.newton.meruinnovators.navigation.NavigationSubGraphs;
import com.newton.notifications.manager.NotificationsManager;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<NavigationSubGraphs> navigationSubGraphsProvider;

  private final Provider<NotificationsManager> notificationsManagerProvider;

  public MainActivity_MembersInjector(Provider<NavigationSubGraphs> navigationSubGraphsProvider,
      Provider<NotificationsManager> notificationsManagerProvider) {
    this.navigationSubGraphsProvider = navigationSubGraphsProvider;
    this.notificationsManagerProvider = notificationsManagerProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<NavigationSubGraphs> navigationSubGraphsProvider,
      Provider<NotificationsManager> notificationsManagerProvider) {
    return new MainActivity_MembersInjector(navigationSubGraphsProvider, notificationsManagerProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectNavigationSubGraphs(instance, navigationSubGraphsProvider.get());
    injectNotificationsManager(instance, notificationsManagerProvider.get());
  }

  @InjectedFieldSignature("com.newton.meruinnovators.activity.MainActivity.navigationSubGraphs")
  public static void injectNavigationSubGraphs(MainActivity instance,
      NavigationSubGraphs navigationSubGraphs) {
    instance.navigationSubGraphs = navigationSubGraphs;
  }

  @InjectedFieldSignature("com.newton.meruinnovators.activity.MainActivity.notificationsManager")
  public static void injectNotificationsManager(MainActivity instance,
      NotificationsManager notificationsManager) {
    instance.notificationsManager = notificationsManager;
  }
}
