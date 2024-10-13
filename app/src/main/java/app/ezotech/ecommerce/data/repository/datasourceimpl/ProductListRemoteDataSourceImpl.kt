package app.ezotech.ecommerce.data.repository.datasourceimpl

import app.ezotech.ecommerce.data.api.ApiInterface
import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.repository.datasource.remote.ProductListRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class ProductListRemoteDataSourceImpl @Inject constructor(private val apiInterface: ApiInterface) :
    ProductListRemoteDataSource {

    override suspend fun getProductList(): Response<List<ProductItem?>> {
        return apiInterface.getProductList()
    }

    override suspend fun getProductListByCategory(category: String): Response<List<ProductItem?>> {
        return apiInterface.getProductListByCategory(category)
    }

    override suspend fun getProductDetails(id: Int): Response<ProductItem?> {
        return apiInterface.getProductDetails(id)
    }
}