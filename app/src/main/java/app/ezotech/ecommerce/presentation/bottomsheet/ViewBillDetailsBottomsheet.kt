package app.ezotech.ecommerce.presentation.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import app.ezotech.ecommerce.R
import app.ezotech.ecommerce.databinding.BottomsheetViewBillDetailsBinding
import app.ezotech.ecommerce.presentation.viewmodel.CartViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ViewBillDetailsBottomsheet : BottomSheetDialogFragment() {

    lateinit var viewModel: CartViewModel
    private lateinit var binding: BottomsheetViewBillDetailsBinding
    private lateinit var dialog: BottomSheetDialog

    fun newInstance(): ViewBillDetailsBottomsheet {
        return ViewBillDetailsBottomsheet()
    }

    /**
     * Set the style for bottomsheet
     * **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
    }

    /**
     * Inflate the bottomsheet view
     * **/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.bottomsheet_view_bill_details, container, false)
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

        dialog.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun initView(){

        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = activity
        }

        val bundle = arguments?:
        return

        binding.totalItems = bundle.getString("totalItems")
        binding.totalEstimatedPayable = bundle.getString("totalEstimatedPayable")

    }

    private fun setEventListeners(){
        binding.imgClose.setOnClickListener { closeBottomSheet() }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    private fun closeBottomSheet() {
        dialog.dismiss()
    }
}