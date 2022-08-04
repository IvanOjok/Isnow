package com.mcash.isnow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_home, container, false)

        val borrow = root.findViewById<CardView>(R.id.borrow)
        val repay = root.findViewById<CardView>(R.id.repay)
        val settings = root.findViewById<ImageView>(R.id.settings)

        borrow.setOnClickListener {
            //borrow.cardBackgroundColor = resources.getColor(R.color.icon_color)
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, BorrowFragment()).addToBackStack(null).commit()
        }

        repay.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, RepayFragment()).addToBackStack(null).commit()
        }

        settings.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, SettingsFragment()).addToBackStack(null).commit()
        }

        return root
    }

}