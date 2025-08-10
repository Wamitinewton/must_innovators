package com.newton.meruinnovators.application;

import coil3.ImageLoader;
import com.newton.auth.data.workManager.TokenRefreshWorkerFactory;
import com.newton.network.domain.repositories.AuthRepository;
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
public final class MeruInnovatorsApp_MembersInjector implements MembersInjector<MeruInnovatorsApp> {
  private final Provider<TokenRefreshWorkerFactory> workerFactoryProvider;

  private final Provider<ImageLoader> imageLoaderProvider;

  private final Provider<NotificationsManager> notificationsManagerProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public MeruInnovatorsApp_MembersInjector(
      Provider<TokenRefreshWorkerFactory> workerFactoryProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<NotificationsManager> notificationsManagerProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
    this.imageLoaderProvider = imageLoaderProvider;
    this.notificationsManagerProvider = notificationsManagerProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  public static MembersInjector<MeruInnovatorsApp> create(
      Provider<TokenRefreshWorkerFactory> workerFactoryProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<NotificationsManager> notificationsManagerProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new MeruInnovatorsApp_MembersInjector(workerFactoryProvider, imageLoaderProvider, notificationsManagerProvider, authRepositoryProvider);
  }

  @Override
  public void injectMembers(MeruInnovatorsApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectImageLoader(instance, imageLoaderProvider.get());
    injectNotificationsManager(instance, notificationsManagerProvider.get());
    injectAuthRepository(instance, authRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.newton.meruinnovators.application.MeruInnovatorsApp.workerFactory")
  public static void injectWorkerFactory(MeruInnovatorsApp instance,
      TokenRefreshWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }

  @InjectedFieldSignature("com.newton.meruinnovators.application.MeruInnovatorsApp.imageLoader")
  public static void injectImageLoader(MeruInnovatorsApp instance, ImageLoader imageLoader) {
    instance.imageLoader = imageLoader;
  }

  @InjectedFieldSignature("com.newton.meruinnovators.application.MeruInnovatorsApp.notificationsManager")
  public static void injectNotificationsManager(MeruInnovatorsApp instance,
      NotificationsManager notificationsManager) {
    instance.notificationsManager = notificationsManager;
  }

  @InjectedFieldSignature("com.newton.meruinnovators.application.MeruInnovatorsApp.authRepository")
  public static void injectAuthRepository(MeruInnovatorsApp instance,
      AuthRepository authRepository) {
    instance.authRepository = authRepository;
  }
}
