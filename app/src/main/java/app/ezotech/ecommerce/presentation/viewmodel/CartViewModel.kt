package app.ezotech.ecommerce.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.utils.MessageConstants
import app.ezotech.ecommerce.domain.mappers.mapToProductItemList
import app.ezotech.ecommerce.domain.usecases.LocalDbUseCase
import app.ezotech.ecommerce.domain.usecases.ProductListUseCase
import app.ezotech.ecommerce.presentation.events.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productListUseCase: ProductListUseCase,
    private val localDbUseCase: LocalDbUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val showMessage = MutableLiveData<Event<MessageConstants>>()
    val eventMessage: LiveData<Event<MessageConstants>> get() = showMessage

    private val showDialog = MutableLiveData<ProductItem>()
    val eventShowDialog: LiveData<ProductItem> get() = showDialog

    private val cartCountMutableLiveData = MutableLiveData<Int>()
    val cartCountLiveData: LiveData<Int> get() = cartCountMutableLiveData

    private val cartTotalValueMutableLiveData = MutableLiveData<Double>()
    val cartTotalValueLiveData: LiveData<Double> get() = cartTotalValueMutableLiveData

    private val productListMutableLiveData = MutableLiveData<List<ProductItem?>>()
    val productListLiveData: LiveData<List<ProductItem?>> get() = productListMutableLiveData

    private val paymentMethodSelectedMutableLiveData = MutableLiveData<String>("")
    val paymentMethodSelectedLiveData: LiveData<String?> get() = paymentMethodSelectedMutableLiveData

    init{
        getCartCountAndTotalValue()
        paymentMethodSelectedMutableLiveData.postValue("COD")
    }

    /**
     * Get total items added and total value of the cart
     * **/
    private fun getCartCountAndTotalValue(){
        viewModelScope.launch(Dispatchers.IO) {
            val count = localDbUseCase.getProductCount()
            if(count <= 0){
                showMessage.postValue(Event(MessageConstants.EMPTY_RESULTS))
            }
            cartCountMutableLiveData.postValue(count)

            val totalValue = localDbUseCase.getCartTotalValue()
            cartTotalValueMutableLiveData.postValue(totalValue)
        }
    }

    /**
     * Inside cart activity show the list of products added into local database
     * **/

     fun getProductListFromCart(){
        viewModelScope.launch(Dispatchers.IO) {
            val productList = localDbUseCase.getProductList()
            if(!productList.isNullOrEmpty()){
                productListMutableLiveData.postValue(mapToProductItemList(productList))
            }
        }
    }

    /**
     * Update qty of the product added
     * Increase and decrease quantity based on stepper
     * **/
    fun updateQty(type:String,item : ProductItem,clickedOnPage:String){
        viewModelScope.launch(Dispatchers.IO) {
            if(type=="add"){
                val qty = item.qty?.plus(1)
                localDbUseCase.updateItemsToCart(qty!!,item)
                updateList()
            }else{
                if(item.qty!! <= 1){
                    showDialog.postValue(item)
                }else{
                    val qty = item.qty?.minus(1)
                    localDbUseCase.updateItemsToCart(qty!!,item)
                }
                updateList()
            }
            getCartCountAndTotalValue()
        }
    }

    /**
     * Update the list when quantity is updated and synced with local database
     * **/
    private fun updateList(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = productListMutableLiveData.value!!
            for(item in list){
                val qty = localDbUseCase.getQtyOfItem(item!!.id)
                item.qty = qty
            }
            productListMutableLiveData.postValue(list)
        }
    }

    /**
     * Remove item from cart if qty is less than 1
     * **/

    fun removeProduct(item : ProductItem){
        viewModelScope.launch(Dispatchers.IO) {
            localDbUseCase.removeItemFromCart(item)
            updateList()
            getCartCountAndTotalValue()
        }
    }

    /**
     * Once Order is placed delete all the items present into local database
     * this is done to push the data to backend server
     * **/
    fun clearItemsFromCart(){
        viewModelScope.launch(Dispatchers.IO) {
            localDbUseCase.clearItemsFromCart()
        }
    }

    fun setPaymentMethod(selectedPaymentMethod : String){
        if(selectedPaymentMethod=="Online"){
            paymentMethodSelectedMutableLiveData.postValue("Online")
        }else{
            paymentMethodSelectedMutableLiveData.postValue("COD")
        }
    }
}