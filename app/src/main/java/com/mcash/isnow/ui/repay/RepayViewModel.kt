package com.mcash.isnow.ui.repay

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mcash.isnow.model.*
import com.mcash.isnow.usecases.DepositUseCase
import com.mcash.isnow.usecases.GetContributionListUseCase
import com.mcash.isnow.usecases.GetContributionProductsUseCase
import com.mcash.isnow.usecases.MakeContributionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RepayViewModel @Inject constructor(
    private val depositUseCase: DepositUseCase,
    private val contributionUseCase: MakeContributionUseCase,
    private val contributionListUseCase: GetContributionListUseCase,
    private val contributionProductsUseCase: GetContributionProductsUseCase

): ViewModel() {

    private val _repayLoanState = MutableStateFlow<RepayLoanState>(RepayLoanState.Initial)
    val repayLoanState get() = _repayLoanState.asLiveData()

    private val _contTypeState = MutableStateFlow<ContTypeState>(ContTypeState.Initial)
    val contTypeState get() = _contTypeState.asLiveData()

    private val _contributionState = MutableStateFlow<ContributionState>(ContributionState.Initial)
    val contributionState get() = _contributionState.asLiveData()

    private val _contributionListState = MutableStateFlow<ContributionListState>(ContributionListState.Initial)
    val contributionListState get() = _contributionListState.asLiveData()

    fun repayLoan(user:String, ic:String, payment_method: String, loan_product:String, amount:String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                depositUseCase.invoke(DepositUseCase.Param(user, ic, payment_method, loan_product, amount.toInt())).collect {
                    when(it){
                        is Resource.Loading -> _repayLoanState.value = RepayLoanState.Loading
                        is Resource.Error -> _repayLoanState.value = RepayLoanState.Error(it.exception)
                        is Resource.Success -> _repayLoanState.value = RepayLoanState.Success(it.data)
                    }
                }
            }
        }

    }

    fun getContributionTypes(ic:String, user:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                contributionProductsUseCase.invoke(GetContributionProductsUseCase.Param(ic, user)).collect {
                    when(it){
                        is Resource.Loading -> _contTypeState.value = ContTypeState.Loading
                        is Resource.Error -> _contTypeState.value = ContTypeState.Error(it.exception)
                        is Resource.Success -> _contTypeState.value = ContTypeState.Success(it.data)
                    }
                }
            }
        }
    }

    fun makeContribution(user:String, ic:String, contribution_id:String, amount:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                contributionUseCase.invoke(MakeContributionUseCase.Param(user, ic, contribution_id, amount.toInt())).collect {
                    when(it){
                        is Resource.Loading -> _contributionState.value = ContributionState.Loading
                        is Resource.Error -> _contributionState.value = ContributionState.Error(it.exception)
                        is Resource.Success -> _contributionState.value = ContributionState.Success(it.data)
                    }
                }
            }
        }
    }

    fun getContributions(pathId:String, ic:String, user:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                contributionListUseCase.invoke(GetContributionListUseCase.Param(pathId, ic, user)).collect {
                    Log.d("Email contributions", "$it")
                    when(it){
                        is Resource.Loading -> _contributionListState.value = ContributionListState.Loading
                        is Resource.Error -> _contributionListState.value = ContributionListState.Error(it.exception)
                        is Resource.Success -> _contributionListState.value = ContributionListState.Success(it.data)
                    }
                }
            }
        }
    }

    sealed class RepayLoanState{
        object Initial: RepayLoanState()
        object Loading: RepayLoanState()
        class Error(var message: String): RepayLoanState()
        class Success(var data: LoanRepay): RepayLoanState()
    }

    sealed class ContTypeState {
        object Initial: ContTypeState()
        object Loading: ContTypeState()
        class Error(var message: String): ContTypeState()
        class Success(var data: ContributionProducts): ContTypeState()
    }

    sealed class ContributionState {
        object Initial: ContributionState()
        object Loading: ContributionState()
        class Error(var message: String): ContributionState()
        class Success(var data: Contribution): ContributionState()
    }

    sealed class ContributionListState {
        object Initial: ContributionListState()
        object Loading: ContributionListState()
        class Error(var message: String): ContributionListState()
        class Success(var data: ContributionList): ContributionListState()
    }
}

