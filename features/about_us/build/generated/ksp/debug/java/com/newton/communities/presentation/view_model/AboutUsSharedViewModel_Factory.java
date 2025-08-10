package com.newton.communities.presentation.view_model;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AboutUsSharedViewModel_Factory implements Factory<AboutUsSharedViewModel> {
  @Override
  public AboutUsSharedViewModel get() {
    return newInstance();
  }

  public static AboutUsSharedViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AboutUsSharedViewModel newInstance() {
    return new AboutUsSharedViewModel();
  }

  private static final class InstanceHolder {
    private static final AboutUsSharedViewModel_Factory INSTANCE = new AboutUsSharedViewModel_Factory();
  }
}
