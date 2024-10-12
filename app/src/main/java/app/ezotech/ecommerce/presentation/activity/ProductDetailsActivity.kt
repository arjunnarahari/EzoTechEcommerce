package app.ezotech.ecommerce.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import app.ezotech.ecommerce.R
import app.ezotech.ecommerce.data.model.ProductItem
import app.ezotech.ecommerce.data.utils.MessageConstants
import app.ezotech.ecommerce.databinding.ActivityProductDetailsBinding
import app.ezotech.ecommerce.presentation.viewmodel.ProductViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private val viewModel by lazy { ViewModelProvider(this)[ProductViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        getObservers()
        onClickEvents()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun getObservers(){
        viewModel.productDetailsLiveData.observe(this){
            if (!viewModel.productDetailsLiveData.value?.image.isNullOrEmpty()) {
                Log.i("ImagePrd", viewModel.productDetailsLiveData.value?.image!!)
                Glide.with(this)
                    .load(viewModel.productDetailsLiveData.value?.image)
                    .into(binding.imgPrdImage)
            }
        }

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
                viewModel.removeProduct(item,"PRD_DETAILS")
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

        binding.btnViewCart.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }

        binding.imgCart.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        if (intent.hasExtra("id")) {
            viewModel.getProductDetails(intent.getIntExtra("id", 0))
        }
    }
}