package app.ezotech.ecommerce.presentation.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import app.ezotech.ecommerce.R
import app.ezotech.ecommerce.databinding.PaymentMethodBottomsheetBinding
import app.ezotech.ecommerce.presentation.viewmodel.CartViewModel
import app.ezotech.ecommerce.presentation.viewmodel.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaymentMethodBottomSheet(private var viewModel: CartViewModel) : BottomSheetDialogFragment() {

    private lateinit var binding: PaymentMethodBottomsheetBinding
    private lateinit var dialog: BottomSheetDialog

    /**
     * Set the style for bottomsheet
     * **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Inflate the bottomsheet view
     * **/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.payment_method_bottomsheet, container, false)
        binding = DataBindingUtil.bind(view)!!
        return binding.root
    }

    /**
     * 1) Bind the viewmodel to the view inside initView()
     * 2) Setup event listeners
     * **/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Bind the viewmodel to the view inside initView()
        initView()
        // 3) Setup event listeners
        setEventListeners()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = BottomSheetDialog(requireContext(), theme)

        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
        (dialog as BottomSheetDialog).behavior.isDraggable = true
        return dialog
    }

    private fun initView() {

        binding.let {
            it.lifecycleOwner = activity
        }

        val bundle = arguments ?: return

        val displayMetrics = context?.resources?.displayMetrics
        //contex.defaultDisplay.getMetrics(displayMetrics)
        val displayWidth = displayMetrics?.widthPixels
        val displayheight = displayMetrics?.heightPixels


        //  binding.parentPaymentModeCouponAppliedBottomSheet.layoutParams.width = 294.toPx
        if (displayheight != null) {
            binding.parentBottomSheet.layoutParams.height =  (displayheight * 0.9f).toInt()
        }
    }

    private fun setEventListeners() {
        binding.imgClose.setOnClickListener { closeBottomSheet() }

        binding.radioOnline.setOnClickListener {
            binding.radioCod.isChecked = false
        }

        binding.radioCod.setOnClickListener {
            binding.radioOnline.isChecked = false
        }

        binding.btnApply.setOnClickListener {
            var selectedPaymentMethod = ""
            if(binding.radioOnline.isChecked){
                selectedPaymentMethod = binding.radioOnline.text.toString()
            }else{
                selectedPaymentMethod = binding.radioCod.text.toString()
            }
            viewModel.setPaymentMethod(selectedPaymentMethod)
            closeBottomSheet()
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    private fun closeBottomSheet() {
        dialog.dismiss()
    }
}