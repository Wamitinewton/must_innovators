package com.newton.meruinnovators.di

import android.content.*
import com.newton.account.navigation.*
import com.newton.admin.navigation.*
import com.newton.auth.authInterceptor.*
import com.newton.auth.navigation.*
import com.newton.blogs.navigation.*
import com.newton.communities.navigation.*
import com.newton.events.navigation.*
import com.newton.feedback.navigation.*
import com.newton.home.navigation.*
import com.newton.meruinnovators.*
import com.newton.meruinnovators.navigation.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*
import okhttp3.*
import okhttp3.logging.*
import retrofit2.*
import retrofit2.converter.gson.*
import java.util.concurrent.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val loggingInterceptor =
        HttpLoggingInterceptor().apply {
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
        communityNavigationApi: CommunityNavigationApi,
        feedbackNavigationApi: FeedbackNavigationApi
    ): NavigationSubGraphs {
        return NavigationSubGraphs(
            authNavigationApi,
            eventsNavigationApi,
            homeNavigationApi,
            blogsNavigationApi,
            accountNavigationApi,
            adminNavigationApi,
            communityNavigationApi,
            feedbackNavigationApi
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
            .baseUrl(BuildConfig.BACKEND_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideContext(
        @ApplicationContext context: Context
    ): Context {
        return context
    }
}
