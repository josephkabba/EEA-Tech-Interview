package com.engie.eea_tech_interview.di

import android.content.Context
import com.engie.eea_tech_interview.MovieRepository
import com.engie.eea_tech_interview.data.MovieRepositoryImpl
import com.engie.eea_tech_interview.remote.MovieApiService
import com.engie.eea_tech_interview.utils.ResultCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val CACHE_SIZE: Long = 10 * 1024 * 1024 // 10MB
const val READ_TIME_OUT: Long = 30
const val WRITE_TIME_OUT: Long = 10
const val CONNECT_TIME_OUT: Long = 10

const val baseUrl = "https://api.themoviedb.org/3/"

@InstallIn(SingletonComponent::class)
@Module
object RemoteModule {

    @Provides
    @Singleton
    fun createRetrofit(
        converterFactory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun createOkHttpClient(@ApplicationContext context: Context): OkHttpClient {

        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .cache(Cache(context.cacheDir, CACHE_SIZE))

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun createMoshiConverter(): Converter.Factory =
        MoshiConverterFactory.create(Moshi.Builder().build())

    @Provides
    fun provideMovieAPIService(
        retrofit: Retrofit
    ): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }


    @Provides
    fun provideMovieRepository(
        movieApiService: MovieApiService
    ): MovieRepository {
        return MovieRepositoryImpl(movieApiService)
    }
}