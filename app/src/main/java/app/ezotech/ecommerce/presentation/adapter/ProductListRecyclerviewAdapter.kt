package app.ezotech.ecommerce.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.ezotech.ecommerce.R
import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.databinding.ProductListItemBinding
import app.ezotech.ecommerce.presentation.activity.ProductDetailsActivity
import app.ezotech.ecommerce.presentation.viewmodel.ProductViewModel
import com.bumptech.glide.Glide

class ProductListRecyclerviewAdapter(
    var list: List<ProductItem>?,
    var viewModel: ProductViewModel,
) : RecyclerView.Adapter<ProductListRecyclerviewAdapter.ProductListViewHolder>() {


    inner class ProductListViewHolder(binding: ProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: ProductListItemBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding: ProductListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_list_item, parent, false
        )
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val item = list?.get(position)
        holder.binding.item = item
        holder.binding.viewModel = viewModel
        if (!list?.get(position)?.image.isNullOrEmpty()) {
            Glide.with(holder.binding.imvIcon.context.applicationContext)
                .load(list?.get(position)?.image)
                .into(holder.binding.imvIcon)
        }

        holder.binding.clItem.setOnClickListener {
            holder.binding.clItem.context.startActivity(
                Intent(
                    holder.binding.clItem.context,
                    ProductDetailsActivity::class.java
                ).putExtra(
                    "id", item?.id
                )
            )
        }

        holder.binding.btnAdd.setOnClickListener {
            viewModel.addProductToCart(item!!,"PRD_LIST")
            Toast.makeText(
                holder.binding.btnAdd.context,
                holder.binding.btnAdd.context.getString(R.string.product_added),
                Toast.LENGTH_LONG
            ).show()
        }

    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }
}