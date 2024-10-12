package app.ezotech.ecommerce.domain.mappers

import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.repository.datasource.local.entity.CartProduct

/**
 * This is the mapper class where we convert the existing model class
 * Entry(for online) to SongsItem(for local db) and vice versa
 * **/

fun mapToProductItemList(list: List<CartProduct>): List<ProductItem> {
    val productList = arrayListOf<ProductItem>()
    list.mapIndexed { index, item ->

        productList.add(
            ProductItem(
                id = item.id!!,
                title = item.title,
                image = item.image!!,
                description = item.description!!,
                price = item.price,
                qty = item.qty
            )
        )
    }
    return productList
}