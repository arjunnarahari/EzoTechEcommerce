package app.ezotech.ecommerce.data.repository.datasource.remote

import app.ezotech.ecommerce.data.model.ProductItem
import retrofit2.Response

interface ProductListRemoteDataSource {

    suspend fun getProductList(): Response<List<ProductItem?>>

    suspend fun getProductListByCategory(category:String): Response<List<ProductItem?>>

    suspend fun getProductDetails(id:Int): Response<ProductItem?>


}