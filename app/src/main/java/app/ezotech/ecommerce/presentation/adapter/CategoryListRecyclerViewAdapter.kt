package app.ezotech.ecommerce.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.ezotech.ecommerce.R
import app.ezotech.ecommerce.databinding.FilterListItemBinding
import app.ezotech.ecommerce.presentation.viewmodel.ProductViewModel


class CategoryListRecyclerViewAdapter(
    var list: List<String>?,
    var viewModel: ProductViewModel,
) : RecyclerView.Adapter<CategoryListRecyclerViewAdapter.CategoryListViewHolder>() {


    inner class CategoryListViewHolder(binding: FilterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding: FilterListItemBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        val binding: FilterListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.filter_list_item, parent, false
        )
        return CategoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        val item = list?.get(position)
        holder.binding.item = item
        holder.binding.viewModel = viewModel


        /*holder.binding.clItem.setOnClickListener {
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
        }*/

    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }
}