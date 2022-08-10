package com.mcash.isnow

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class BorrowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_borrow, container, false)

        val amt = root.findViewById<TextInputEditText>(R.id.amount)
        val btnBorrow = root.findViewById<MaterialButton>(R.id.btnBorrow)

        btnBorrow.setOnClickListener {
            val amount = amt.text.toString()
            if (amount.isNotEmpty()){
                AlertDialog.Builder(requireContext()).setTitle("Borrow")
                    .setMessage("You are requesting to borrow UGX $amount. \n Enter Your pin to confirm.")
                    .setView(R.layout.pin_edit_text)
                    .setCancelable(true)
                    .setPositiveButton("OK"
                    ) { p0, p1 ->
                        
                        p0?.dismiss()
                        showBottomNavigation()
                    }
                    .setNegativeButton("Cancel"){p0, p1 ->
                        p0.dismiss()
                    }
                    .create()
                    .show()
            }
            else{
                Toast.makeText(requireContext(), "Enter Correct Amount", Toast.LENGTH_SHORT).show()
            }

        }
        return root
    }

    private fun showBottomNavigation() {
        val sheet = BottomSheetDialog(requireContext())
        sheet.setContentView(R.layout.success_layout)
        sheet.findViewById<MaterialButton>(R.id.btnNext)?.setOnClickListener {
            sheet.dismiss()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        sheet.show()
    }

}