package app.ezotech.ecommerce.data.repository.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.ezotech.ecommerce.data.repository.datasource.local.entity.CartProduct

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemsToCart(item: CartProduct)

    @Query("UPDATE addedProduct SET qty = :qty,price = :price WHERE id =:id")
    fun  updateItemsToCart(qty:Int,id: Int,price:Double)

    @Query("Delete from addedProduct WHERE id =:id")
    fun removeItemFromCart(id: Int)

    @Query("Delete from addedProduct")
    fun clearItemsFromCart()

    @Query("SELECT * FROM addedProduct")
    fun getProductList(): List<CartProduct>

    @Query("SELECT count(*) FROM addedProduct")
    fun getProductCount(): Int

    @Query("SELECT Sum(price) FROM addedProduct")
    fun getCartTotalValue() : Double

    @Query("SELECT qty FROM addedProduct where id =:id")
    fun getQtyOfItem(id:Int) : Int
}