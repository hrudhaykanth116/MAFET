package com.hrudhaykanth116.core.common.mappers

import com.hrudhaykanth116.core.data.models.ApiError
import com.hrudhaykanth116.core.data.models.ApiResultWrapper
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper

fun <T> ApiResultWrapper<T>.toRepoResult(): RepoResultWrapper<T> {
    return when (this) {
        is ApiResultWrapper.Success -> RepoResultWrapper.Success(data)

        is ApiResultWrapper.Error -> RepoResultWrapper.Error(
            when (val error = apiError) {
                is ApiError.NoInternetError -> com.hrudhaykanth116.core.domain.models.ErrorState.NoNetwork

                is ApiError.ResponseError -> com.hrudhaykanth116.core.domain.models.ErrorState.Api(
                    message = "API Error: ${error.code}",
                    description = error.error?.string()
                )

                is ApiError.TimeOutError -> com.hrudhaykanth116.core.domain.models.ErrorState.TimeOut

                is ApiError.NullResponseError -> com.hrudhaykanth116.core.domain.models.ErrorState.Api(
                    message = "Null Response: ${error.code}",
                    description = error.error?.string()
                )

                is ApiError.ExceptionError -> com.hrudhaykanth116.core.domain.models.ErrorState.SomethingWentWrong

                is ApiError.SomethingWentWrong -> com.hrudhaykanth116.core.domain.models.ErrorState.SomethingWentWrong

                is ApiError.InvalidUser -> com.hrudhaykanth116.core.domain.models.ErrorState.InvalidUser
            }
        )
    }
}
