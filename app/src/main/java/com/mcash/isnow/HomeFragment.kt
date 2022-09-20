package com.mcash.isnow

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcash.isnow.adapter.HistoryAdapter
import com.mcash.isnow.databinding.FragmentHomeBinding
import com.mcash.isnow.model.ContributionEntity
import com.mcash.isnow.ui.loan.BorrowFragment
import com.mcash.isnow.ui.repay.RepayViewModel
import com.mcash.isnow.utils.BaseFragment
import com.mcash.isnow.utils.KEY_EMAIL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    val viewModel: RepayViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ic = getIC()
        val email = getEmail()
        val gender = getGender()
        val user =  getUser().name
        val fullList = ArrayList<ContributionEntity>()

        with(binding){

            with(viewModel){
                if (email != null && ic != null) {
                    getContributions("1", ic, email)
                    Log.d("Email contribute", email)
                    Log.d("IC contribute", ic)
                    getContributions("2", ic, email)
                    //getContributions("3", ic, email)
                }

                contributionListState.observe(viewLifecycleOwner){
                    when(it){
                        is RepayViewModel.ContributionListState.Loading -> {
                            progress.visibility = View.VISIBLE
                        }
                        is RepayViewModel.ContributionListState.Error -> {
                            progress.visibility = View.GONE
                            Log.d("Error", "${it.message}")
                        }
                        is RepayViewModel.ContributionListState.Success -> {
                            progress.visibility = View.GONE
                            val list = it.data.contributions
                            Log.d("list", "$list")
                            for (i in list){
                                Log.d("list i", "$i")
                                fullList.add(i)
                            }
                            Log.d("fulllist", "$fullList")
                            if (fullList.isNotEmpty()){
                                noRecent.visibility = View.GONE
                                recent.visibility = View.VISIBLE
                                recent.adapter = HistoryAdapter(requireContext(), fullList)
                                recent.layoutManager = LinearLayoutManager(requireContext())
                            }
                        }
                        else -> {
                            Log.d("List Loading else", "Loading list")
                        }
                    }
                }
            }

            name.text = getUser().name
            if (gender != null && user != null){
                if (gender == "Male") name.text = "Mr. $user"
                if (gender == "Female") name.text = "Ms. $user"
            }

            textView2.text = "Member ID: ${getUser().id}"
            club.text = getUser().club

            loan.text = "UGX: ${getUser().loan}"
            contribution.text =  "UGX: ${getUser().contributions}"

            Log.d("Email 1", KEY_EMAIL)
            Log.d("User", "${getUser()}")

            borrow.setOnClickListener {
                navigate(HomeFragmentDirections.actionHomeFragmentToBorrowFragment())
                //borrow.cardBackgroundColor = resources.getColor(R.color.icon_color)
                //getParentActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, BorrowFragment()).addToBackStack(null).commit()
            }

            settings.setOnClickListener {
                navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
                //getParentActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, SettingsFragment()).addToBackStack(null).commit()
            }
            repay.setOnClickListener {
                navigate(HomeFragmentDirections.actionHomeFragmentToRepayFragment())
                //getParentActivity().supportFragmentManager.beginTransaction().replace(R.id.mainContainer, RepayFragment()).addToBackStack(null).commit()
            }



        }
    }
}