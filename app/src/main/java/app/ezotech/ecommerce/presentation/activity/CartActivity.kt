package app.ezotech.ecommerce.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import app.ezotech.ecommerce.R
import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.databinding.ActivityCartBinding
import app.ezotech.ecommerce.presentation.bottomsheet.ViewBillDetailsBottomsheet
import app.ezotech.ecommerce.presentation.viewmodel.CartViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCartBinding
    private val viewModel by lazy { ViewModelProvider(this)[CartViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.getProductListFromCart()
        getObservers()
        onClickEvents()
    }

    private fun getObservers(){

        viewModel.eventShowDialog.observe(this){
            val item : ProductItem = it as ProductItem
            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle(R.string.remove_product)
            //set message for alert dialog
            builder.setMessage(R.string.are_you_sure_remove_product)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                viewModel.removeProduct(item)
                dialogInterface.dismiss()
            }
            //performing cancel action
            builder.setNeutralButton("Cancel"){dialogInterface , which ->
                dialogInterface.dismiss()
            }
            //performing negative action
            builder.setNegativeButton("No"){dialogInterface, which ->
                dialogInterface.dismiss()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    private fun onClickEvents(){
        binding.imgBackArrow.setOnClickListener {
            finish()
        }

        /**
         * If in case user wants to again to back to product list screen
         * **/
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this,ProductListActivity::class.java))
            finish()
        }

        /**
         * If in case user wants to again to back to product list screen
         * **/
        binding.imgSearch.setOnClickListener {
            startActivity(Intent(this,ProductListActivity::class.java))
            finish()
        }

        /**
         * Show bill details bottomsheet
         * **/
        binding.textViewBill.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("totalItems", viewModel.cartCountLiveData.value.toString())
            bundle.putString("totalEstimatedPayable", viewModel.cartTotalValueLiveData.value.toString())
            val viewBillDetailsBottomSheet = ViewBillDetailsBottomsheet().newInstance()
            viewBillDetailsBottomSheet.arguments = bundle
            viewBillDetailsBottomSheet.isCancelable = true
            viewBillDetailsBottomSheet.show(supportFragmentManager, "ViewBillDetailsBottomSheet")
        }

        /**
         * place the order
         * **/
        binding.btnCheckout.setOnClickListener {
            binding.clOrderPlacedView.visibility = View.VISIBLE
            binding.constraintToolbar.visibility = View.GONE
            binding.btnAdd.visibility = View.GONE
            binding.clBottom.visibility = View.GONE
            viewModel.clearItemsFromCart()
            lifecycleScope.launch {
                delay(3000)
                Glide.with(binding.ivOrderPlaced.context).load(R.drawable.order_placed_gif)
                    .into(binding.ivOrderPlaced)

                delay(2100)
                val intent = Intent(this@CartActivity, ProductListActivity::class.java).apply {
                    putExtra("onFinishReCreateHomePage", true)
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
                finishAffinity()

//                  //finish()
            }
        }
    }
}