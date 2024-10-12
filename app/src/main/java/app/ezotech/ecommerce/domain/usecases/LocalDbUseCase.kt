package app.ezotech.ecommerce.domain.usecases

import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.repository.datasource.local.entity.CartProduct
import app.ezotech.ecommerce.domain.repository.LocalDbRepository
import javax.inject.Inject

class LocalDbUseCase @Inject constructor(private val localDbRepository: LocalDbRepository) {

    suspend fun getProductCount(): Int {
        return localDbRepository.getProductCount()
    }

    suspend fun getCartTotalValue(): Double {
        return localDbRepository.getCartTotalValue()
    }

    suspend fun insertItemsToCart(item: CartProduct) {
        localDbRepository.insertItemsToCart(item)
    }

    suspend fun updateItemsToCart(qty : Int,item: ProductItem) {
        val price = qty * item.price
        localDbRepository.updateItemsToCart(qty,item.id,price)
    }

    suspend fun removeItemFromCart(item: ProductItem) {
        localDbRepository.removeItemFromCart(item.id)
    }

    suspend fun clearItemsFromCart() {
        localDbRepository.clearItemsFromCart()
    }

    suspend fun getProductList(): List<CartProduct> {
        return localDbRepository.getProductList()
    }

    suspend fun getQtyOfItem(id : Int): Int {
        return localDbRepository.getQtyOfItem(id)
    }


}