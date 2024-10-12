package app.ezotech.ecommerce.presentation.bindingadapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.presentation.adapter.CartListRecyclerViewAdapter
import app.ezotech.ecommerce.presentation.adapter.ProductListRecyclerviewAdapter
import app.ezotech.ecommerce.presentation.viewmodel.CartViewModel
import app.ezotech.ecommerce.presentation.viewmodel.ProductViewModel
import com.bumptech.glide.Glide

@BindingAdapter("setProductList", "viewModel")
fun setProductList(
    view: RecyclerView,
    list: List<ProductItem>?,
    viewModel: ProductViewModel
) {
    view.adapter?.run {
        //notify
        if (this is ProductListRecyclerviewAdapter) {
            this.list = list
            this.viewModel = viewModel
            this.notifyDataSetChanged()
        }
    } ?: run {
        view.adapter = ProductListRecyclerviewAdapter(list, viewModel)
    }
}

@BindingAdapter("setCartProductList", "viewModel")
fun setCartProductList(
    view: RecyclerView,
    list: List<ProductItem>?,
    viewModel: CartViewModel
) {
    view.adapter?.run {
        //notify
        if (this is CartListRecyclerViewAdapter) {
            this.list = list
            this.viewModel = viewModel
            this.notifyDataSetChanged()
        }
    } ?: run {
        view.adapter = CartListRecyclerViewAdapter(list, viewModel)
    }
}

@BindingAdapter("setPrdImage")
fun setPrdImage(
    view: AppCompatImageView?,
    productItem: ProductItem?
) {
    view?.run {
        Glide.with(view.context)
            .load(productItem!!.image)
            .into(view)
    }
}