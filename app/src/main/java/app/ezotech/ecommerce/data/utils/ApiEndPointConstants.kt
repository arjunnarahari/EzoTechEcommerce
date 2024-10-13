package app.ezotech.ecommerce.data.utils

import app.ezotech.ecommerce.BuildConfig

object ApiEndpointConstants {

    fun baseUrl(): String {
        return BuildConfig.baseUrl
    }

    const val FETCH_PRODUCTS = "products"
    const val FETCH_PRODUCT_DETAILS = "products/{id}"
    const val FETCH_PRODUCT_BY_CATEGORIES = "/products/category/{category}"
}