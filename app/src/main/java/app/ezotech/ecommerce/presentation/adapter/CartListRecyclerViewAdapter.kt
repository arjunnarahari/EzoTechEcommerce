package app.ezotech.ecommerce.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.ezotech.ecommerce.R
import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.databinding.CartListItemBinding
import app.ezotech.ecommerce.presentation.viewmodel.CartViewModel
import com.bumptech.glide.Glide

class CartListRecyclerViewAdapter(
    var list: List<ProductItem>?,
    var viewModel: CartViewModel,
) : RecyclerView.Adapter<CartListRecyclerViewAdapter.CartListViewHolder>() {


    inner class CartListViewHolder(binding: CartListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: CartListItemBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val binding: CartListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cart_list_item, parent, false
        )
        return CartListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        val item = list?.get(position)
        holder.binding.item = item
        holder.binding.viewModel = viewModel
        if (!list?.get(position)?.image.isNullOrEmpty()) {
            Glide.with(holder.binding.imvIcon.context.applicationContext)
                .load(list?.get(position)?.image)
                .into(holder.binding.imvIcon)
        }
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }
}