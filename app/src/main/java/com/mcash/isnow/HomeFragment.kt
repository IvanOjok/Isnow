package com.mcash.isnow

import android.os.Bundle
import android.util.Log
import android.view.View
import com.mcash.isnow.databinding.FragmentHomeBinding
import com.mcash.isnow.ui.loan.BorrowFragment
import com.mcash.isnow.utils.BaseFragment
import com.mcash.isnow.utils.KEY_EMAIL

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val root =  inflater.inflate(R.layout.fragment_home, container, false)
//
//        val borrow = root.findViewById<CardView>(R.id.borrow)
//        val repay = root.findViewById<CardView>(R.id.repay)
//        val settings = root.findViewById<ImageView>(R.id.settings)
//
//        borrow.setOnClickListener {
//            //borrow.cardBackgroundColor = resources.getColor(R.color.icon_color)
//            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, BorrowFragment()).addToBackStack(null).commit()
//        }
//
//        repay.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, RepayFragment()).addToBackStack(null).commit()
//        }
//
//        settings.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, SettingsFragment()).addToBackStack(null).commit()
//        }
//
//        return root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            name.text = getUser().name
            textView2.text = "Member ID: ${getUser().id}"
            club.text = getUser().club

            loan.text = "UGX: ${getUser().loan}"
            contribution.text =  "UGX: ${getUser().contributions}"

            Log.d("Email 1", KEY_EMAIL)
            Log.d("User", "${getUser()}")

            borrow.setOnClickListener {
                //borrow.cardBackgroundColor = resources.getColor(R.color.icon_color)
                getParentActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, BorrowFragment()).addToBackStack(null).commit()
            }

            settings.setOnClickListener {
                getParentActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, SettingsFragment()).addToBackStack(null).commit()
            }
            repay.setOnClickListener {
                getParentActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, RepayFragment()).addToBackStack(null).commit()
            }

        }
    }
}