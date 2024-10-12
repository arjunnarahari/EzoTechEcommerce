package app.ezotech.ecommerce.di

import app.ezotech.ecommerce.data.repository.datasource.local.LocalDbRepositoryImpl
import app.ezotech.ecommerce.data.repository.repositoryimpl.ProductListRepositoryImpl
import app.ezotech.ecommerce.domain.repository.LocalDbRepository
import app.ezotech.ecommerce.domain.repository.ProductListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsProductListRepository(loginRepositoryImpl: ProductListRepositoryImpl): ProductListRepository

    @Binds
    abstract fun bindsProductListLocalRepository(localDbRepositoryImpl: LocalDbRepositoryImpl): LocalDbRepository
}