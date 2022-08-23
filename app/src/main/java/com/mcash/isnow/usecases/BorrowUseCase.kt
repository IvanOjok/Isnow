package com.mcash.isnow.usecases

import android.util.Log
import com.mcash.isnow.model.Borrow
import com.mcash.isnow.model.Resource
import com.mcash.isnow.repository.AuthRepository
import com.mcash.isnow.repository.UtilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException
import javax.inject.Inject

class BorrowUseCase @Inject constructor(
    val authRepository: AuthRepository,
    val utilRepository: UtilRepository
): BaseFlowUseCase<BorrowUseCase.Param, Resource<Borrow>>() {
    data class Param(
        var user:String,
        var ic: Int,
        var payment_method: String,
        var loan_product: String,
        var amount: Int
    )

    override fun run(param: Param?): Flow<Resource<Borrow>> = flow {
        emit(Resource.Loading)

        try {
            param?.let {
                val hash = hashMapOf<String, Any>().apply {
                    this["user"] = it.user
                    this["ic"] = it.ic
                    this["payment_method"] = it.payment_method
                    this["loan_product"] = it.loan_product
                    this["amount"] = it.amount
                }

                Log.d("hash", "$hash")
                val response = authRepository.borrow(hash)
                emit(Resource.Success(response))
            } ?: throw InvalidParameterException()
        }
        catch (t: Throwable){
            emit(Resource.Error(utilRepository.getNetworkError(t)))
        }
    }
}