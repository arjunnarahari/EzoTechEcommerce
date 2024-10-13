package app.ezotech.ecommerce.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import app.ezotech.ecommerce.R
import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.utils.MessageConstants
import app.ezotech.ecommerce.databinding.ActivityProductListBinding
import app.ezotech.ecommerce.presentation.bottomsheet.FilterBottomsheet
import app.ezotech.ecommerce.presentation.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private val viewModel by lazy { ViewModelProvider(this)[ProductViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        getObservers()
        onClickEvents()
    }

    private fun getObservers(){
        viewModel.eventMessage.observe(this) {
            val message = it as MessageConstants
            if (message == (MessageConstants.NO_NETWORK)) {
                Toast.makeText(
                    this,
                    getString(R.string.no_internet_msg),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

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
                viewModel.removeProduct(item,"PRD_LIST")
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
        binding.btnViewCart.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }

        binding.imgCart.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }

        /**
         * show filter bottomsheet
         * **/
        binding.imgFilter.setOnClickListener {
            val bundle = Bundle()
//            bundle.putString("totalItems", viewModel.cartCountLiveData.value.toString())
//            bundle.putString("totalEstimatedPayable", viewModel.cartTotalValueLiveData.value.toString())
            val filterBottomsheet = FilterBottomsheet(viewModel)
            filterBottomsheet.arguments = bundle
            filterBottomsheet.isCancelable = true
            filterBottomsheet.show(supportFragmentManager, "FilterBottomsheet")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProductsList()
        viewModel.getCategoryList()
    }
}