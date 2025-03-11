package com.newton.meruinnovators.di

import android.content.Context
import com.newton.account.navigation.AccountNavigationApi
import com.newton.admin.navigation.AdminNavigationApi
import com.newton.auth.authInterceptor.AuthInterceptor
import com.newton.auth.navigation.AuthNavigationApi
import com.newton.blogs.navigation.BlogsNavigationApi
import com.newton.communities.navigation.CommunityNavigationApi
import com.newton.events.navigation.EventsNavigationApi
import com.newton.home.navigation.HomeNavigationApi
import com.newton.meruinnovators.BuildConfig
import com.newton.meruinnovators.navigation.NavigationSubGraphs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideNavigationSubGraphs(
        authNavigationApi: AuthNavigationApi,
        eventsNavigationApi: EventsNavigationApi,
        homeNavigationApi: HomeNavigationApi,
        blogsNavigationApi: BlogsNavigationApi,
        accountNavigationApi: AccountNavigationApi,
        adminNavigationApi: AdminNavigationApi,
        communityNavigationApi: CommunityNavigationApi
    ): NavigationSubGraphs {
        return NavigationSubGraphs(
            authNavigationApi,
            eventsNavigationApi,
            homeNavigationApi,
            blogsNavigationApi,
            accountNavigationApi,
            adminNavigationApi,
            communityNavigationApi
        )
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context = context))
            .addInterceptor(loggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()


    @Provides
    @Singleton
    fun provideMeruInnovatorsApi(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://7h3pspsq-8000.uks1.devtunnels.ms/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}