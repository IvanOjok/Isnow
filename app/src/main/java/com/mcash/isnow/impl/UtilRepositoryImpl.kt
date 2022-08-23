package com.mcash.isnow.impl

import com.mcash.isnow.repository.UtilRepository
import com.mcash.isnow.utils.resolveError
import javax.inject.Inject

class UtilRepositoryImpl @Inject constructor(): UtilRepository {

    override fun getNetworkError(throwable: Throwable): String = throwable.resolveError()

}