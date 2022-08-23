package com.mcash.isnow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            btnLogout.setOnClickListener {
                logout()
                startActivity(Intent(requireContext(), Login::class.java))
            }
        }
    }
}