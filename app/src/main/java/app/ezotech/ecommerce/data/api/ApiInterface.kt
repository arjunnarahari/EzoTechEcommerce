package app.ezotech.ecommerce.data.api

import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.utils.ApiEndpointConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET(ApiEndpointConstants.FETCH_PRODUCTS)
    suspend fun getProductList(
    ): Response<List<ProductItem?>>

    @GET(ApiEndpointConstants.FETCH_PRODUCT_BY_CATEGORIES)
    suspend fun getProductListByCategory(
        @Path(value="category", encoded=true) category : String
    ): Response<List<ProductItem?>>

    @GET(ApiEndpointConstants.FETCH_PRODUCT_DETAILS)
    suspend fun getProductDetails(
        @Path(value="id", encoded=true) id : Int
    ): Response<ProductItem?>

}