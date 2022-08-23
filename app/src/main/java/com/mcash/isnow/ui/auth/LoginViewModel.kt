package com.mcash.isnow.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mcash.isnow.model.Account
import com.mcash.isnow.model.OTPResponse
import com.mcash.isnow.model.Resource
import com.mcash.isnow.usecases.AccountUseCase
import com.mcash.isnow.usecases.AuthUseCase
import com.mcash.isnow.usecases.VerifyOTPUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val accountUseCase: AccountUseCase,
    private val verifyOTPUseCase: VerifyOTPUseCase

): ViewModel() {

    private val _accountState = MutableStateFlow<AccountState>(AccountState.Initial)
    val accountState get() = _accountState.asLiveData()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState get() = _loginState.asLiveData()

    private val _verifyOtpState = MutableStateFlow<VerifyOtpState>(VerifyOtpState.Initial)
    val verifyOtpState get() = _verifyOtpState.asLiveData()

    fun checkUser(id: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                accountUseCase.invoke(AccountUseCase.Param(id)).collect{
                    Log.d("ErrorC", "$it")
                    when(it){
                        is Resource.Loading -> _accountState.value = AccountState.Loading
                        is Resource.Error -> _accountState.value = AccountState.Error(it.exception)
                        is Resource.Success -> _accountState.value = AccountState.Success(it.data)
                    }
                }
            }
        }
    }

    fun loginRequest(id: String, ic: String, password: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                authUseCase.invoke(AuthUseCase.Params(id, ic, password, "")).collect{
                    Log.d("Error", "$it")
                    when(it){
                        is Resource.Loading -> _loginState.value = LoginState.Loading
                        is Resource.Error -> _loginState.value = LoginState.Error(it.exception)
                        is Resource.Success -> _loginState.value = LoginState.Success(it.data)
                    }
                }
            }
        }
    }

    fun verifyOTP(id: String, ic: String, pin: String, otp: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                verifyOTPUseCase.invoke(VerifyOTPUseCase.Param(id, ic, pin, otp)).collect{
                    when(it){
                        is Resource.Loading ->  _verifyOtpState.value = VerifyOtpState.Loading
                        is Resource.Error -> _verifyOtpState.value = VerifyOtpState.Error(it.exception)
                        is Resource.Success -> _verifyOtpState.value = VerifyOtpState.Success(it.data)
                    }
                }
            }
        }
    }
}

sealed class AccountState{
    object Initial: AccountState()
    object Loading : AccountState()
    data class Success(val account: Account) : AccountState()
    data class Error(val message: String) : AccountState()
}

sealed class LoginState{
    object Initial: LoginState()
    object Loading : LoginState()
    data class Success(val data: Int) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class VerifyOtpState{
    object Initial: VerifyOtpState()
    object Loading : VerifyOtpState()
    data class Success(val data: OTPResponse) : VerifyOtpState()
    data class Error(val message: String) : VerifyOtpState()
}