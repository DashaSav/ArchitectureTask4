package com.example.architecturetask4.di

import com.example.architecturetask4.data.TranslationApi
import com.example.architecturetask4.data.repository.LocalTranslationRepository
import com.example.architecturetask4.data.repository.RemoteTranslationRepository
import com.example.architecturetask4.domain.repository.ILocalTranslationRepository
import com.example.architecturetask4.domain.repository.IRemoteTranslationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindLocalRepository(impl: LocalTranslationRepository): ILocalTranslationRepository

    @Binds
    abstract fun bindRemoteRepository(impl: RemoteTranslationRepository): IRemoteTranslationRepository

}

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {
    private const val BASE_URL = "https://clients5.google.com/"

    @Provides
    fun provideTranslationApi(
        retrofit: Retrofit
    ): TranslationApi {
        return retrofit.create<TranslationApi>()
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
