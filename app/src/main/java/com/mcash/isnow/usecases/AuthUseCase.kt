package com.mcash.isnow.usecases

import android.util.Log
import com.mcash.isnow.model.Resource
import com.mcash.isnow.repository.AuthRepository
import com.mcash.isnow.repository.UtilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val utilRepository: UtilRepository
) : BaseFlowUseCase<AuthUseCase.Params, Resource<Int>>() {

    data class Params(
        var user: String,
        var ic: String,
        var pin: String,
        var otp: String
    )

    override fun run(param: Params?): Flow<Resource<Int>> = flow {

        emit(Resource.Loading)
        try {
            param?.let {
                val hashMap = HashMap<String, Any>().apply {
                    this["user"] = it.user
                    this["ic"] = it.ic
                    this["pin"] = it.pin
                    this["otp"] = it.otp
                }
                val response = authRepository.sendLoginRequest(hashMap)

                Log.d("Response", "$response")
                emit(Resource.Success(response))
            } ?: throw InvalidParameterException()

        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }
    }
}