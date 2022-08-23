package com.mcash.isnow.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.mcash.isnow.model.User

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    private lateinit var progressUtils: ProgressUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressUtils = ProgressUtils(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return _binding?.root
    }

    fun getParentActivity(): FragmentActivity {
        return requireActivity()
    }

    protected fun showProgressDialog() {
        progressUtils.showDialog()
    }

    protected fun hideProgressDialog() {
        progressUtils.hideDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    protected fun navigate(direction: NavDirections) = findNavController().navigate(direction)
//
//    protected fun navigateClearBackstack(destination: Int, bundle: Bundle?) =
//        findNavController().navigate(
//            destination,
//            bundle,
//            NavOptions.Builder().setPopUpTo(destination, true).build()
//        )
//
//    protected fun navigateUp() = findNavController().navigateUp()

    fun init(): SharedPreferences {
        val sharedPreferences = requireActivity().getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences!!
    }

    fun getUser() : User {
        val pref = init()
        val name = pref.getString(KEY_USER, null)
        val id = pref.getString(KEY_ID, null)
        val ic = pref.getString(KEY_IC, null)
        val club = pref.getString(KEY_CLUB, null)
        val loan = pref.getString(KEY_LOAN, null)
        val contributions = pref.getString(KEY_CONT, null)

        return User(name, id, ic, club, loan, contributions)

    }

    fun getIC(): String {
        val pref = init()
        val ic = pref.getString(KEY_IC, "ic")
        return ic!!
    }

    fun getEmail(): String? {
        val pref = init()
        val email = pref.getString(KEY_EMAIL, "Default")
        return email!!
    }

    fun storeLoan(loan:String) {
        val pref = init()
        val editor = pref.edit()
        editor.putString(KEY_LOAN, loan)
        editor.apply()
        editor.commit()
    }


    fun logout(){
        val pref = init()
        val editor = pref.edit()
        editor.remove(KEY_EMAIL)
        Log.d("Email Frag", KEY_EMAIL)
        editor.apply()
 //       editor.commit()
//        editor.clear()
//        //editor.apply()
//        editor.commit()

 //       context!!.deleteSharedPreferences(myPREFERENCES)
    }
}