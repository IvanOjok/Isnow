package com.mcash.isnow.usecases

import com.mcash.isnow.model.OTPResponse
import com.mcash.isnow.model.Resource
import com.mcash.isnow.repository.AuthRepository
import com.mcash.isnow.repository.UtilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException
import javax.inject.Inject

class VerifyOTPUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val utilRepository: UtilRepository
): BaseFlowUseCase<VerifyOTPUseCase.Param, Resource<OTPResponse>>() {
    data class Param(
        val user: String,
        val ic:String,
        val pin: String,
        var otp:String
    )

    override fun run(param: Param?): Flow<Resource<OTPResponse>> = flow {
        emit(Resource.Loading)
        try {
            param?.let {
                val hashMap = HashMap<String, Any>().apply {
                    this["user"] = it.user
                    this["ic"] = it.ic
                    this["pin"] = it.pin
                    this["otp"] = it.otp
                }
                val response = authRepository.sendLoginWithOTP(hashMap)

                emit(Resource.Success(response))
            } ?: throw InvalidParameterException()

        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }
    }
}