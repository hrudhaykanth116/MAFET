package com.hrudhaykanth116.core.domain.models

sealed class DomainState<out T>(open val contentState: T?) {

    open class LoadingDomainState<T>(contentState: T? = null): DomainState<T>(contentState)
    open class ErrorDomainState<T>(val errorState: ErrorState, contentState: T? = null): DomainState<T>(contentState)
    open class LoadedDomainState<T>(override val contentState: T): DomainState<T>(contentState)

}



