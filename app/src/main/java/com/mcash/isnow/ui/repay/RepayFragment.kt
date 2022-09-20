package com.mcash.isnow.ui.repay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.mcash.isnow.MainActivity
import com.mcash.isnow.R
import com.mcash.isnow.databinding.FragmentRepayBinding
import com.mcash.isnow.ui.loan.BorrowViewModel
import com.mcash.isnow.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepayFragment : BaseFragment<FragmentRepayBinding>(FragmentRepayBinding::inflate) {

    private val repayViewModel: RepayViewModel by viewModels()
    private val borrowViewModel: BorrowViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ic = getIC()
        val email = getEmail()

        var contProducts:HashMap<String, String>?= null
        var loanProducts:HashMap<String, String>?= null
        var paymentMethod: HashMap<String, String> ?= null


        with(binding){

            with(repayViewModel){
                if (email != null && ic != null) {
                    getContributionTypes(ic, email)
                }

                contTypeState.observe(viewLifecycleOwner){
                    when(it){
                        is RepayViewModel.ContTypeState.Loading -> {
                            showProgressDialog()
                        }
                        is RepayViewModel.ContTypeState.Error -> {
                            hideProgressDialog()
                            contProduct.setTextColor(resources.getColor(R.color.red))
                            contProduct.visibility = View.VISIBLE
                            contProduct.setText(it.message)
                        }
                        is RepayViewModel.ContTypeState.Success -> {
                            contProducts = it.data.contributions
                            val keys = contProducts!!.keys
                            val arr = ArrayList<String>()
                            keys.forEach {
                                arr.add(contProducts!![it]!!)
                            }

                            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arr)
                            contProduct.setAdapter(adapter)
                        }
                        else -> {
                            Log.d("UnS Contribution Type", "Repay else")
                        }
                    }
                }

                repayLoanState.observe(viewLifecycleOwner){
                    when(it) {
                        is RepayViewModel.RepayLoanState.Loading -> showProgressDialog()
                        is RepayViewModel.RepayLoanState.Error -> {
                            hideProgressDialog()
                            loanError.visibility = View.VISIBLE
                            loanError.text = it.message
                        }
                        is RepayViewModel.RepayLoanState.Success -> {
                            hideProgressDialog()
                            showBottomNavigation(it.data.message?:"Successful")
                        }
                        else -> {
                            Log.d("Unsure Else", "Repay When else")
                        }
                    }
                }

                contributionState.observe(viewLifecycleOwner){
                    when(it){
                        is RepayViewModel.ContributionState.Loading -> showProgressDialog()
                        is RepayViewModel.ContributionState.Error -> {
                            hideProgressDialog()
                            contError.visibility = View.VISIBLE
                            contError.text = it.message
                        }
                        is RepayViewModel.ContributionState.Success -> {
                            hideProgressDialog()
                            showBottomNavigation(it.data.message?:"Contribution Successful")
                        }

                    }
                }
            }

            with(borrowViewModel){
                if (email != null && ic != null) {
                    getLoanProducts(ic.toInt(), email)
                }

                productState.observe(viewLifecycleOwner){
                    when(it){
                        is BorrowViewModel.LoanProductState.Loading -> {
                            showProgressDialog()
                        }
                        is BorrowViewModel.LoanProductState.Error -> {
                            hideProgressDialog()
                            product.setTextColor(resources.getColor(R.color.red))
                            product.visibility = View.VISIBLE
                            product.setText(it.message)
                            product.setTextColor(resources.getColor(R.color.red))
                        }
                        is BorrowViewModel.LoanProductState.Success -> {
                            hideProgressDialog()
                            Log.d("Products", "$it")
                            loanProducts = it.data.data!!.loan_products
                            val keys = loanProducts!!.keys
                            val array = ArrayList<String>()
                            keys.forEach {
                                array.add(loanProducts!![it]!!)
                            }

                            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, array)
                            product.setAdapter(adapter)

                            paymentMethod = it.data.data!!.payment_methods
                            val mKeys = paymentMethod!!.keys
                            val arr = ArrayList<String>()
                            mKeys.forEach {
                                arr.add(paymentMethod!![it]!!)
                            }
                            val mAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arr)
                            payment.setAdapter(mAdapter)
                        }
                        else -> {
                            Log.d("Unsure Loan Type block", "Repay else")
                        }
                    }

                }
            }


            val aAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listOf("Contributions", "Loans"))
            type.setAdapter(aAdapter)

            type.doOnTextChanged { text, start, before, count ->
                Log.d("Change Text", "on text changed")
                if (type.text.toString() == "Contributions"){
                    Log.d("Change Text 1", "on text changed")
                    loanLay.visibility = View.GONE
                    contLay.visibility = View.VISIBLE
                    btnRepay.text = "Make Contribution"
                }
                if (type.text.toString() == "Loans"){
                    Log.d("Change Text 2", "on text changed")
                    contLay.visibility = View.GONE
                    loanLay.visibility = View.VISIBLE
                    btnRepay.text = "Repay Loan"
                }
            }

            btnRepay.setOnClickListener {
                when{
                    type.text.toString().isEmpty() -> {
                        repayError.visibility = View.VISIBLE
                        repayError.text = "Deposit Type can't be empty"
                    }
                    type.text.toString() == "Contributions" -> {
                        repayError.visibility = View.GONE
                        when{
                            contProduct.text.toString().isEmpty() -> {
                                contError.visibility = View.VISIBLE
                                contError.text = "Contribution Type can't be empty"
                            }
                            contAmount.text.toString().isEmpty() -> {
                                contError.visibility = View.VISIBLE
                                contError.text = "Contribution amount can't be empty"
                            }
                            else -> {
                                val t = contProducts?.filterValues { it == contProduct.text.toString() }?.keys

                                AlertDialog.Builder(requireContext()).setTitle("Contribution")
                                        .setMessage("You are making a contribution of UGX ${amount.text.toString()} for ${contProduct.text.toString()}. \n Enter Your pin to confirm.")
                                        .setView(com.mcash.isnow.R.layout.pin_edit_text)
                                        .setCancelable(true)
                                        .setPositiveButton("OK"
                                        ) { p0, p1 ->
                                            p0?.dismiss()
                                            if (email != null && t != null && ic != null) {
                                                repayViewModel.makeContribution(email, ic, t.first(), contAmount.text.toString())
                                            }
                                        }
                                        .create()
                                        .show()


                            }
                        }

                    }
                    type.text.toString() == "Loans" -> {
                        repayError.visibility = View.GONE
                        when{
                            product.text.toString().isEmpty() -> {
                                loanError.visibility = View.VISIBLE
                                loanError.text = "Loan Product Can't be Empty"
                            }
                            payment.text.toString().isEmpty() -> {
                                loanError.visibility = View.VISIBLE
                                loanError.text = "Payment Method Can't be Empty"
                            }
                            amount.text.toString().isEmpty() -> {
                                loanError.visibility = View.VISIBLE
                                loanError.text = "Amount can't be empty"
                            }
                            else -> {
                                val k = loanProducts?.filterValues { it ==  product.text.toString() }?.keys
                                val m = paymentMethod?.filterValues { it == payment.text.toString() }?.keys

                                AlertDialog.Builder(requireContext()).setTitle("Repay Loan")
                                    .setMessage("You are repaying Your debt of UGX ${amount.text.toString()}. \n Enter Your pin to confirm.")
                                    .setView(R.layout.pin_edit_text)
                                    .setCancelable(true)
                                    .setPositiveButton("OK"
                                    ) { p0, p1 ->
                                        p0?.dismiss()
                                        if (email != null && m != null && k != null && ic != null) {
                                            repayViewModel.repayLoan(email, ic, m.first(), k.first(), amount.text.toString())
                                        }
                                    }
                                    .create()
                                    .show()

                            }

                        }
                    }
                }
            }
        }
    }

    private fun showBottomNavigation(message:String) {
        val sheet = BottomSheetDialog(requireContext())
        sheet.setContentView(R.layout.success_layout)
        sheet.findViewById<TextView>(R.id.textView4)?.text = message
        sheet.findViewById<MaterialButton>(R.id.btnNext)?.setOnClickListener {
            sheet.dismiss()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        sheet.show()
    }

}