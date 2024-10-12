package app.ezotech.ecommerce.data.repository.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductItem")
data class ProductItemLocal(
    @PrimaryKey
    @ColumnInfo(name = "id") var id: Long? = 0,
    @ColumnInfo(name = "title") var title: String? = "",
    @ColumnInfo(name = "price") var price: Double? = 0.0,
    @ColumnInfo(name = "description") var description: String? = "",
    @ColumnInfo(name = "image") var imageUrl: String? = "",
    @ColumnInfo(name = "category") var category: String? = "",
    @ColumnInfo(name = "rate") var rate: Double? = 0.0,
    @ColumnInfo(name = "count") var count: Int? = 0,
)