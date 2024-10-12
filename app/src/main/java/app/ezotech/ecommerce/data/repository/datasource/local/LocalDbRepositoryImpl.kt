package app.ezotech.ecommerce.data.repository.datasource.local

import app.ezotech.ecommerce.data.repository.datasource.local.entity.CartProduct
import app.ezotech.ecommerce.domain.repository.LocalDbRepository
import javax.inject.Inject

class LocalDbRepositoryImpl @Inject constructor(private val dbDao: ProductDao) : LocalDbRepository {

    override suspend fun insertItemsToCart(item: CartProduct) {
        dbDao.insertItemsToCart(item)
    }

    override suspend fun updateItemsToCart(qty:Int,id: Int,price:Double) {
        dbDao.updateItemsToCart(qty,id,price)
    }

    override suspend fun removeItemFromCart(id: Int) {
        dbDao.removeItemFromCart(id)
    }

    override suspend fun clearItemsFromCart() {
        dbDao.clearItemsFromCart()
    }

    override suspend fun getProductList(): List<CartProduct> {
        return dbDao.getProductList()
    }

    override suspend fun getProductCount(): Int {
        return dbDao.getProductCount()
    }

    override suspend fun getCartTotalValue(): Double {
        return dbDao.getCartTotalValue()
    }

    override suspend fun getQtyOfItem(id: Int): Int {
        return dbDao.getQtyOfItem(id)
    }


}