package com.mcash.isnow.usecases

import android.util.Log
import com.mcash.isnow.model.*
import com.mcash.isnow.repository.AuthRepository
import com.mcash.isnow.repository.UtilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException
import javax.inject.Inject

class DepositUseCase @Inject constructor(
    val authRepository: AuthRepository,
    val utilRepository: UtilRepository
): BaseFlowUseCase<DepositUseCase.Param, Resource<LoanRepay>>()
{
    data class Param(
        var user:String,
        var ic:String,
        var payment_method: String,
        var loan_product: String,
        var amount: Int
    )

    override fun run(param: Param?): Flow<Resource<LoanRepay>> = flow {
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

                val res = authRepository.repayLoan(hash)
                emit(Resource.Success(res))
            } ?: throw InvalidParameterException()

        }
        catch (t: Throwable){
            emit(Resource.Error(utilRepository.getNetworkError(t)))
        }

    }
}


class GetContributionProductsUseCase @Inject constructor(
    var authRepository: AuthRepository,
    var utilRepository: UtilRepository
): BaseFlowUseCase<GetContributionProductsUseCase.Param, Resource<ContributionProducts>>()
{
    data class Param(
        var ic: String,
        var user:String
    )

    override fun run(param: Param?): Flow<Resource<ContributionProducts>> = flow {
        emit(Resource.Loading)

        try {
            param?.let {
                val map = hashMapOf<String, Any>().apply {
                    this["ic"] = it.ic
                    this["user"] = it.user
                }
                val res = authRepository.getContributionProducts(map)
                emit(Resource.Success(res))
            } ?: throw InvalidParameterException()
        }
        catch (t: Throwable){
            emit(Resource.Error(utilRepository.getNetworkError(t)))
        }
    }
}


class MakeContributionUseCase @Inject constructor(
    val authRepository: AuthRepository,
    val utilRepository: UtilRepository
): BaseFlowUseCase<MakeContributionUseCase.Param, Resource<Contribution>>()
{

    data class Param(
        var user:String,
        var ic:String,
        var contribution_id:String,
        var amount: Int
    )

    override fun run(param: Param?): Flow<Resource<Contribution>> = flow {
        emit(Resource.Loading)

        try {
            param?.let {
                val map = hashMapOf<String, Any>().apply {
                    this["user"] = it.user
                    this["ic"] = it.ic
                    this["contribution_id"] = it.contribution_id
                    this["amount"] = it.amount
                }

                val res = authRepository.makeContribution(map)
                emit(Resource.Success(res))
            } ?: throw InvalidParameterException()
        }
        catch (t: Throwable){
            emit(Resource.Error(utilRepository.getNetworkError(t)))
        }
    }
}


class GetContributionListUseCase @Inject constructor(
    var authRepository: AuthRepository,
    var utilRepository: UtilRepository

): BaseFlowUseCase<GetContributionListUseCase.Param, Resource<ContributionList>>() {
    data class Param(
        var pathId:String,
        var ic: String,
        var user:String
    )

    override fun run(param: Param?): Flow<Resource<ContributionList>> = flow {
        emit(Resource.Loading)

        try {
            param?.let {
                val map = hashMapOf<String, Any>().apply {
                    this["ic"] = it.ic
                    this["user"] = it.user
                }
                val res = authRepository.getContributionList(param.pathId, map)
                Log.d("Email contributions", "$res")
                emit(Resource.Success(res))
            } ?: throw InvalidParameterException()
        }
        catch (t: Throwable){
            emit(Resource.Error(utilRepository.getNetworkError(t)))
        }
    }

}