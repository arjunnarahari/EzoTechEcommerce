package app.ezotech.ecommerce.domain.repository

import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.utils.Resource
import retrofit2.Response

interface ProductListRepository {

    suspend fun getProductList(
    ): Resource<Response<List<ProductItem?>>>


    suspend fun getProductDetails(id:Int
    ): Resource<Response<ProductItem?>>
}