package com.hrudhaykanth116.core.domain.models

sealed interface RepoResultWrapper<out T> {

    data class Success<T>(val data: T): RepoResultWrapper<T>
    data class Error(val errorState: ErrorState): RepoResultWrapper<Nothing>

}