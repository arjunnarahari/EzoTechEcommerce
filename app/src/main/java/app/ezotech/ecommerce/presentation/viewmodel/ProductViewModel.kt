package app.ezotech.ecommerce.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ezotech.ecommerce.data.model.Category
import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.repository.datasource.local.entity.CartProduct
import app.ezotech.ecommerce.data.utils.MessageConstants
import app.ezotech.ecommerce.data.utils.isNetworkAvailable
import app.ezotech.ecommerce.domain.usecases.LocalDbUseCase
import app.ezotech.ecommerce.domain.usecases.ProductListUseCase
import app.ezotech.ecommerce.presentation.events.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productListUseCase: ProductListUseCase,
    private val localDbUseCase: LocalDbUseCase,
    @ApplicationContext private val context: Context
) :
    ViewModel() {

    private val showMessage = MutableLiveData<Event<MessageConstants>>()
    val eventMessage: LiveData<Event<MessageConstants>> get() = showMessage

    private val showLoader = MutableLiveData<Boolean>()
    val eventshowLoader: LiveData<Boolean> get() = showLoader

    private val showDialog = MutableLiveData<ProductItem>()
    val eventShowDialog: LiveData<ProductItem> get() = showDialog

    private val productListMutableLiveData = MutableLiveData<List<ProductItem?>>()
    val productListLivedata: LiveData<List<ProductItem?>> get() = productListMutableLiveData

    private val categoryListMutableLiveData = MutableLiveData<List<Category?>>()
    val categoryListLivedata: LiveData<List<Category?>> get() = categoryListMutableLiveData

    private val productDetailsMutableLiveData = MutableLiveData<ProductItem?>()
    val productDetailsLiveData: LiveData<ProductItem?> get() = productDetailsMutableLiveData

    private val cartCountMutableLiveData = MutableLiveData<Int>()
    val cartCountLiveData: LiveData<Int> get() = cartCountMutableLiveData

    private val cartTotalValueMutableLiveData = MutableLiveData<Double>()
    val cartTotalValueLiveData: LiveData<Double> get() = cartTotalValueMutableLiveData

    private val localCategorySelectedMutableLiveData = MutableLiveData<String>("")
    val localCategorySelectedLiveData: LiveData<String?> get() = localCategorySelectedMutableLiveData



    init{
        getCartCountAndTotalValue()
    }

    fun getCategoryList(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = ArrayList<Category>()
            list.add(Category("electronics",false))
            list.add(Category("jewelery",false))
            list.add(Category("men's clothing",false))
            list.add(Category("women's clothing",false))
            categoryListMutableLiveData.postValue(list)
        }
    }

    /**
     * This function is used to fetch the list of products
     * check internet connection and fetch from API
     * Show no results found text if the list is empty
     * Show no internet toast if there is no internet connectivity
     * **/
    fun getProductsList() {
        viewModelScope.launch(Dispatchers.IO) {
            showLoader.postValue(true)
            if (isNetworkAvailable(context)) {
                try {
                    productListUseCase.getProductList()?.let {
                        if (!it.isNullOrEmpty()) {
                            for(item in it){
                                val qty = localDbUseCase.getQtyOfItem(item!!.id)
                                if(qty > 0){
                                    item.qty = qty
                                }
                            }
                            productListMutableLiveData.postValue(it)
                        } else {
                            showMessage.postValue(Event(MessageConstants.EMPTY_RESULTS))
                        }
                        getCartCountAndTotalValue()

                        showLoader.postValue(false)
                    }
                } catch (ex: Exception) {
                    Log.i("exception ::::", "" + ex.toString())
                    showMessage.postValue(Event(MessageConstants.EMPTY_RESULTS))
                    showLoader.postValue(false)
                }

            } else{
                showMessage.postValue(Event(MessageConstants.NO_NETWORK))
                showLoader.postValue(false)
            }
        }
    }

    /**
     * This function is used to fetch the list of products
     * check internet connection and fetch from API
     * Show no results found text if the list is empty
     * Show no internet toast if there is no internet connectivity
     * **/
    fun getProductsListByCategory(category:String) {
        viewModelScope.launch(Dispatchers.IO) {
            showLoader.postValue(true)
            if (isNetworkAvailable(context)) {
                try {
                    productListUseCase.getProductListByCategory(category)?.let {
                        if (!it.isNullOrEmpty()) {
                            for(item in it){
                                val qty = localDbUseCase.getQtyOfItem(item!!.id)
                                if(qty > 0){
                                    item.qty = qty
                                }
                            }
                            productListMutableLiveData.postValue(it)
                        } else {
                            showMessage.postValue(Event(MessageConstants.EMPTY_RESULTS))
                        }
                        getCartCountAndTotalValue()
                        showLoader.postValue(false)
                    }
                } catch (ex: Exception) {
                    Log.i("exception ::::", "" + ex.toString())
                    showMessage.postValue(Event(MessageConstants.EMPTY_RESULTS))
                    showLoader.postValue(false)
                }

            } else{
                showMessage.postValue(Event(MessageConstants.NO_NETWORK))
                showLoader.postValue(false)
            }
        }
    }

    /**
     * This function is used to fetch the details of the product by passing id
     * check internet connection and fetch from API
     * Show no results found text if the data is empty
     * Show no internet toast if there is no internet connectivity
     * **/
    fun getProductDetails(id:Int) {

        viewModelScope.launch(Dispatchers.IO) {
            showLoader.postValue(true)
            if (isNetworkAvailable(context)) {
                try {
                    productListUseCase.getProductDetails(id)?.let {
                        val qty = localDbUseCase.getQtyOfItem(id)
                        if(qty > 0){
                            it.qty = qty
                        }
                        productDetailsMutableLiveData.postValue(it)
                    }
                    getCartCountAndTotalValue()
                    showLoader.postValue(false)
                } catch (ex: Exception) {
                    Log.i("exception ::::", "" + ex.toString())
                    showMessage.postValue(Event(MessageConstants.EMPTY_RESULTS))
                    showLoader.postValue(false)
                }

            } else {
                showMessage.postValue(Event(MessageConstants.NO_NETWORK))
                showLoader.postValue(false)
            }
        }
    }

    /**
     * Add product to cart, insert item to the database, only 1 qty will be added
     * **/
    fun addProductToCart(item : ProductItem,clickedOnPage: String){
        try {
            val cartItem = CartProduct(item.id,item.title,item.image,item.description,item.category,item.price,1)
            viewModelScope.launch(Dispatchers.IO) {
                localDbUseCase.insertItemsToCart(cartItem)
                if(clickedOnPage=="PRD_DETAILS"){
                    updateCart()
                }else{
                    updateList()
                }

                getCartCountAndTotalValue()
            }
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }

    /**
     * Get total items added and total value of the cart
     * **/
    private fun getCartCountAndTotalValue(){
        viewModelScope.launch(Dispatchers.IO) {
            val count = localDbUseCase.getProductCount()
            cartCountMutableLiveData.postValue(count)

            val totalValue = localDbUseCase.getCartTotalValue()
            cartTotalValueMutableLiveData.postValue(totalValue)
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
                if(clickedOnPage=="cart"){
                    updateCart()
                }else{
                    updateList()
                }
            }else{
                Log.i("item",""+item)
                if(item.qty!! <= 1){
                    showDialog.postValue(item)
                }else{
                    val qty = item.qty?.minus(1)
                    localDbUseCase.updateItemsToCart(qty!!,item)
                }
                if(clickedOnPage=="cart"){
                    updateCart()
                }else{
                    updateList()
                }
            }
            getCartCountAndTotalValue()
        }
    }

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

    private fun updateCart(){
        viewModelScope.launch(Dispatchers.IO) {
            val item = productDetailsMutableLiveData.value!!
            val qty = localDbUseCase.getQtyOfItem(item.id)
            item.qty = qty
            productDetailsMutableLiveData.postValue(item)
        }
    }


    /**
     * Remove item from cart if qty is less than 1
     * **/

    fun removeProduct(item : ProductItem,clickedOnPage:String){
        viewModelScope.launch(Dispatchers.IO) {
            localDbUseCase.removeItemFromCart(item)
            if(clickedOnPage=="PRD_DETAILS"){
                updateCart()
            }else{
                updateList()
            }
            getCartCountAndTotalValue()
        }
    }

    fun setFilterRadioSelection(selectedText:String){
        viewModelScope.launch(Dispatchers.IO) {
            val list = categoryListMutableLiveData.value!!
            for(item in list){
                item?.isSelected = false
                if (item != null) {
                    if(selectedText == item.categoryType){
                        item.isSelected = true
                    }
                }
            }
            categoryListMutableLiveData.postValue(list)
            localCategorySelectedMutableLiveData.postValue(selectedText)
        }
    }

    fun clearFilterSelection(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = categoryListMutableLiveData.value!!
            for(item in list){
                if(item?.isSelected == true){
                    item.isSelected = false
                }
            }
            categoryListMutableLiveData.postValue(list)
            localCategorySelectedMutableLiveData.postValue("")
        }
    }
}