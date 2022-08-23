package com.mcash.isnow.usecases

import kotlinx.coroutines.flow.Flow

abstract class BaseFlowUseCase<in Param, Result> where Param : Any {

    abstract fun run(param: Param?): Flow<Result>

    operator fun invoke(param: Param? = null) = run(param)
}