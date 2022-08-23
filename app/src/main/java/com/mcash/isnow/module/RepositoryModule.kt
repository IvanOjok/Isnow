package com.mcash.isnow.module

import com.mcash.isnow.impl.AuthRepositoryImpl
import com.mcash.isnow.impl.UtilRepositoryImpl
import com.mcash.isnow.repository.AuthRepository
import com.mcash.isnow.repository.UtilRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUtilRepository(utilRepositoryImpl: UtilRepositoryImpl): UtilRepository
}