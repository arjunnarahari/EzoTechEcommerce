package app.ezotech.ecommerce.data.model

data class ProductItem(
    val category: String? = "",
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating? = null,
    val title: String,
    var qty: Int? = 0
)