package com.mcash.isnow

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.button.MaterialButton
import com.mcash.isnow.databinding.FragmentSettingsBinding
import com.mcash.isnow.ui.auth.Login
import com.mcash.isnow.utils.BaseFragment


class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val root = inflater.inflate(R.layout.fragment_settings, container, false)
//        val btnLogout = root.findViewById<MaterialButton>(R.id.btnLogout)
//        btnLogout.setOnClickListener {
//            startActivity(Intent(requireContext(), Login::class.java))
//        }
//
//        return root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){

            val user = getUserName()
            val gen = getGender()
            if (user != null) {
                name.text = user
            }
            if (gen != null && user != null){
                if (gen == "Male") name.text = "Mr. $user"
                if (gen == "Female") name.text = "Ms. $user"
            }

            val aAdapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, listOf("Male", "Female"))
            gender.setAdapter(aAdapter)

            editLayout.setOnClickListener {
                container.visibility = View.GONE
                editProfile.visibility = View.VISIBLE

                btnEdit.setOnClickListener {
                    when{
                        phone.text.toString().isEmpty() -> {
                            error.visibility = View.VISIBLE
                            error.text = "Name can't be empty"
                        }
                        gender.text.toString().isEmpty() -> {
                            error.visibility = View.VISIBLE
                            error.text = "Gender can't be empty"
                        }
                        else -> {
                            error.visibility = View.GONE
                            storeGender(gender.text.toString())
                            storeUserName(phone.text.toString())
                            navigateUp()
                        }
                    }
                }
            }

            btnLogout.setOnClickListener {
                logout()
                startActivity(Intent(requireContext(), Login::class.java))
            }
        }
    }
}