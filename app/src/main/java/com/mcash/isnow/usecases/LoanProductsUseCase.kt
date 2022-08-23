package com.mcash.isnow.usecases

import android.util.Log
import com.mcash.isnow.model.LoanProducts
import com.mcash.isnow.model.Resource
import com.mcash.isnow.repository.AuthRepository
import com.mcash.isnow.repository.UtilRepository
import com.mcash.isnow.retrofit.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException
import javax.inject.Inject

class LoanProductsUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    val utilRepository: UtilRepository
): BaseFlowUseCase<LoanProductsUseCase.Param, Resource<LoanProducts>>() {

    data class Param(
        var ic: Int,
        val user: String
    )

    override fun run(param: Param?): Flow<Resource<LoanProducts>> = flow {
        emit(Resource.Loading)
        try {
            param?.let {
                val hashMap = hashMapOf<String, Any>().apply {
                    this["ic"] = it.ic
                    this["user"] = it.user
                }
                val response = authRepository.getLoanProducts(hashMap)
                Log.d("REs", "$response")
                Log.d("map", "$hashMap")
                emit(Resource.Success(response))
            } ?: throw InvalidParameterException()
        } catch (t: Throwable){
            emit(Resource.Error(utilRepository.getNetworkError(t)))
        }
    }
}