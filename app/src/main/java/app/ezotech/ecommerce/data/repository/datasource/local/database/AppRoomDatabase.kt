package app.ezotech.ecommerce.data.repository.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.ezotech.ecommerce.data.repository.datasource.local.ProductDao
import app.ezotech.ecommerce.data.repository.datasource.local.entity.CartProduct

@Database(
    entities = [CartProduct::class], version = 1, exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getAppDao(): ProductDao

}