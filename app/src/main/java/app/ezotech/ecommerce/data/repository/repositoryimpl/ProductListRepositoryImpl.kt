package app.ezotech.ecommerce.data.repository.repositoryimpl

import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.repository.datasource.remote.ProductListRemoteDataSource
import app.ezotech.ecommerce.data.utils.Resource
import app.ezotech.ecommerce.data.utils.safeApiCall
import app.ezotech.ecommerce.domain.repository.ProductListRepository
import retrofit2.Response
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(private val productListRemoteDataSource: ProductListRemoteDataSource): ProductListRepository {

    override suspend fun getProductList(): Resource<Response<List<ProductItem?>>> {
        return safeApiCall {
            productListRemoteDataSource.getProductList()
        }
    }

    override suspend fun getProductListByCategory(category: String): Resource<Response<List<ProductItem?>>> {
        return safeApiCall {
            productListRemoteDataSource.getProductListByCategory(category)
        }
    }

    override suspend fun getProductDetails(id: Int): Resource<Response<ProductItem?>> {
        return safeApiCall {
            productListRemoteDataSource.getProductDetails(id)
        }
    }
}