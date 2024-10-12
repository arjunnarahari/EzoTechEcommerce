package app.ezotech.ecommerce.domain.repository

import app.ezotech.ecommerce.data.repository.datasource.local.entity.CartProduct

interface LocalDbRepository {

    suspend fun insertItemsToCart(item: CartProduct)

    suspend fun updateItemsToCart(qty: Int, id:Int,price:Double)

    suspend fun removeItemFromCart(id: Int)

    suspend fun clearItemsFromCart()

    suspend fun getProductList(): List<CartProduct>

    suspend fun getProductCount(): Int

    suspend fun getCartTotalValue(): Double

    suspend fun getQtyOfItem(id:Int) : Int
}