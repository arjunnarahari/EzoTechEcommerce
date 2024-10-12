package app.ezotech.ecommerce.domain.usecases

import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.utils.Resource
import app.ezotech.ecommerce.domain.repository.ProductListRepository
import javax.inject.Inject

class ProductListUseCase @Inject constructor(private val productListRepository: ProductListRepository) {

    suspend fun getProductList(): List<ProductItem?>? =
        when (val res = productListRepository.getProductList()) {
            is Resource.Success -> res.value?.body()
            is Resource.Failure -> null
            else -> null
        }

    suspend fun getProductDetails(id:Int): ProductItem? =
        when (val res = productListRepository.getProductDetails(id)) {
            is Resource.Success -> res.value?.body()
            is Resource.Failure -> null
            else -> null
        }

}