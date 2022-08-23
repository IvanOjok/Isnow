package com.mcash.isnow.usecases

import android.util.Log
import com.mcash.isnow.model.Account
import com.mcash.isnow.model.Resource
import com.mcash.isnow.repository.AuthRepository
import com.mcash.isnow.repository.UtilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val utilRepository: UtilRepository
): BaseFlowUseCase<AccountUseCase.Param, Resource<Account>>() {

    data class Param(
        var user: String
    )

    override fun run(param: Param?): Flow<Resource<Account>> = flow {
        emit(Resource.Loading)
        try {
            param?.let {
                val user = it.user
                val response= authRepository.getAccount(user)
                Log.d("ResponseC", "$response")
                emit(Resource.Success(response))
            } ?: throw InvalidParameterException()
        }
        catch (t: Throwable){
            emit(Resource.Error(utilRepository.getNetworkError(t)))
        }
    }
}