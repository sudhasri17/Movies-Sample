package com.example.movies.di.modules

import com.example.movies.BuildConfig
import com.example.movies.api.MovieService
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideMovieService(okHttpClient: OkHttpClient) = MovieService.create(okHttpClient)

    @Provides
    @Singleton
    fun provideLogInterceptor() :HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor, interceptor: Interceptor) =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideClientInterceptor() = Interceptor { chain: Interceptor.Chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter(MovieService.API_KEY, BuildConfig.API_KEY)
                .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }

}