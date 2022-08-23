package com.mcash.isnow.ui.loan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mcash.isnow.model.Borrow
import com.mcash.isnow.model.LoanProducts
import com.mcash.isnow.model.Resource
import com.mcash.isnow.usecases.BorrowUseCase
import com.mcash.isnow.usecases.LoanProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BorrowViewModel @Inject constructor(
    private val loanProductsUseCase: LoanProductsUseCase,
    private val borrowUseCase: BorrowUseCase
): ViewModel() {

    private val _productState = MutableStateFlow<LoanProductState>(LoanProductState.Initial)
    val productState get() = _productState.asLiveData()

    private val _borrowState = MutableStateFlow<BorrowState>(BorrowState.Initial)
    val borrowState get() = _borrowState.asLiveData()

    fun getLoanProducts(ic: Int, user:String){
        viewModelScope.launch {
            with(Dispatchers.IO){
                loanProductsUseCase.invoke(LoanProductsUseCase.Param(ic, user)).collect{
                    Log.d("Response", "$it")
                    when(it){
                        is Resource.Loading -> _productState.value = LoanProductState.Loading
                        is Resource.Error -> _productState.value = LoanProductState.Error(it.exception)
                        is Resource.Success -> _productState.value = LoanProductState.Success(it.data)
                    }
                }
            }
        }
    }

    fun borrow(user: String, ic: Int, paymentMethod: String, loanProduct:String, amount:Int){
        viewModelScope.launch {
            with(Dispatchers.IO){
                borrowUseCase.invoke(BorrowUseCase.Param(user, ic, paymentMethod, loanProduct, amount)).collect{
                    Log.d("Response", "$it")
                    when(it){
                        is Resource.Loading -> _borrowState.value = BorrowState.Loading
                        is Resource.Error -> _borrowState.value = BorrowState.Error(it.exception)
                        is Resource.Success -> _borrowState.value = BorrowState.Success(it.data)
                    }
                }
            }
        }
    }


    sealed class LoanProductState{
        object Initial: LoanProductState()
        object Loading: LoanProductState()
        class Error(val message:String): LoanProductState()
        class Success(val data: LoanProducts): LoanProductState()
    }

    sealed class BorrowState{
        object Initial: BorrowState()
        object Loading: BorrowState()
        class Error(val message:String): BorrowState()
        class Success(val data: Borrow): BorrowState()
    }

}