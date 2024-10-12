package app.ezotech.ecommerce.di

import app.ezotech.ecommerce.data.repository.datasource.remote.ProductListRemoteDataSource
import app.ezotech.ecommerce.data.repository.datasourceimpl.ProductListRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataModule {

    @Binds
    abstract fun bindsProductListRemoteDataSource(productListRemoteDataSourceImpl: ProductListRemoteDataSourceImpl): ProductListRemoteDataSource

}