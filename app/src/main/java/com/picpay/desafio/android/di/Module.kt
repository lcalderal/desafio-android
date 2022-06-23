package com.picpay.desafio.android.di

import com.picpay.desafio.android.service.PicPayService
import com.picpay.desafio.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun providesPicPayService(retrofit: Retrofit): PicPayService {
        return retrofit.create(PicPayService::class.java)
    }
}