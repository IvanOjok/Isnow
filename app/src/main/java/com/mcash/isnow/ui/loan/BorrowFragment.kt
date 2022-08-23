package com.mcash.isnow.ui.loan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.mcash.isnow.MainActivity
import com.mcash.isnow.R
import com.mcash.isnow.databinding.FragmentBorrowBinding
import com.mcash.isnow.model.Borrow
import com.mcash.isnow.utils.BaseFragment
import com.mcash.isnow.ui.loan.BorrowViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BorrowFragment : BaseFragment<FragmentBorrowBinding>(FragmentBorrowBinding::inflate) {

    private val viewModel: BorrowViewModel by viewModels()

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val root = inflater.inflate(R.layout.fragment_borrow, container, false)
//
//        val amt = root.findViewById<TextInputEditText>(R.id.amount)
//        val btnBorrow = root.findViewById<MaterialButton>(R.id.btnBorrow)
//
//        btnBorrow.setOnClickListener {
//            val amount = amt.text.toString()
//            if (amount.isNotEmpty()){
//                AlertDialog.Builder(requireContext()).setTitle("Borrow")
//                    .setMessage("You are requesting to borrow UGX $amount. \n Enter Your pin to confirm.")
//                    .setView(R.layout.pin_edit_text)
//                    .setCancelable(true)
//                    .setPositiveButton("OK"
//                    ) { p0, p1 ->
//
//                        p0?.dismiss()
//                        showBottomNavigation()
//                    }
//                    .setNegativeButton("Cancel"){p0, p1 ->
//                        p0.dismiss()
//                    }
//                    .create()
//                    .show()
//            }
//            else{
//                Toast.makeText(requireContext(), "Enter Correct Amount", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//        return root
//    }

    private fun showBottomNavigation() {
        val sheet = BottomSheetDialog(requireContext())
        sheet.setContentView(R.layout.success_layout)
        sheet.findViewById<MaterialButton>(R.id.btnNext)?.setOnClickListener {
            sheet.dismiss()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        sheet.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ic = getIC()
        val email = getEmail()

        var products:HashMap<String, String>?= null
        var method:HashMap<String, String>?= null
        with(binding){
            with(viewModel){

                viewModel.getLoanProducts(ic.toInt(), email!!)

                productState.observe(viewLifecycleOwner){
                    when(it){
                        is BorrowViewModel.LoanProductState.Loading -> {
                            showProgressDialog()
                        }
                        is BorrowViewModel.LoanProductState.Error -> {
                            hideProgressDialog()
                            product.setTextColor(resources.getColor(R.color.red))
                            error.visibility = View.VISIBLE
                            error.text = it.message
                            payment.setTextColor(resources.getColor(R.color.red))
                            error.text = it.message
                        }
                        is BorrowViewModel.LoanProductState.Success -> {
                            hideProgressDialog()
                            Log.d("Products", "$it")
                            products = it.data.data!!.loan_products //!!.one
//                             Log.d("Array", "${it.data.data!!.loan_product!!.one}")
//                             val array = arrayOf(products!!.one, products.two, products.three)
                            val keys = products!!.keys
                            val array = ArrayList<String>()
                            keys.forEach {
                                array.add(products!![it]!!)
                            }

                            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, array)
                            product.setAdapter(adapter)

                            method = it.data.data!!.payment_methods
                            val mKeys = method!!.keys
                            val arr = ArrayList<String>()
                            mKeys.forEach {
                                arr.add(method!![it]!!)
                            }
                            val mAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arr)
                            payment.setAdapter(mAdapter)
                        }
                    }

                }

                borrowState.observe(viewLifecycleOwner){
                    when(it){
                        is BorrowViewModel.BorrowState.Loading -> {
                            showProgressDialog()
                        }
                        is BorrowViewModel.BorrowState.Error -> {
                            hideProgressDialog()
                            error.visibility = View.VISIBLE
                            error.text = it.message
                        }
                        is BorrowViewModel.BorrowState.Success -> {
                            hideProgressDialog()
                            showBottomNavigation()

                            storeLoan(it.data.loan_account_balance.toString())
                        }
                    }
                }
            }

            btnBorrow.setOnClickListener {
                when {
                    product.text.toString().isEmpty() -> {
                        product.setTextColor(resources.getColor(R.color.red))
                        error.visibility = View.VISIBLE
                        error.text = "Product Can't be empty"
                    }
                    payment.text.toString().isEmpty() -> {
                        payment.setTextColor(resources.getColor(R.color.red))
                        error.visibility = View.VISIBLE
                        error.text = "Method Can't be empty"
                    }
                    amount.text.toString().isEmpty() -> {
                        amount.setTextColor(resources.getColor(R.color.red))
                        error.visibility = View.VISIBLE
                        error.text = "Amount can't be empty"
                    }
                    else -> {

                        val k = products!!.filterValues { it ==  product.text.toString() }.keys
                        val m = method!!.filterValues { it == payment.text.toString() }.keys
                        k.first()
                        viewModel.borrow(
                            email!!,
                            ic.toInt(),
                            m.first(),
                            k.first(),
                            amount.text.toString().toInt()
                        )
                    }


                }
            }

        }
    }

}