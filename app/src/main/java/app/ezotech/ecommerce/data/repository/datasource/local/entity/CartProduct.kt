package app.ezotech.ecommerce.data.repository.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addedProduct")
class CartProduct(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "qty") val qty: Int
)