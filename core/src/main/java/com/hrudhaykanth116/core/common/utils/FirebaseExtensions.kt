package com.hrudhaykanth116.core.common.utils

import com.google.android.gms.tasks.Task
import com.hrudhaykanth116.core.domain.models.ErrorState
import com.hrudhaykanth116.core.domain.models.RepoResultWrapper
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun <T> Task<T>.await(): RepoResultWrapper<T> {
    return suspendCancellableCoroutine { cont ->

        addOnCompleteListener { task: Task<T> ->
            task.exception?.let { exception ->
                cont.resume(
                    RepoResultWrapper.Error(
                        errorState = ErrorState.SomethingWentWrong
                    )
                )
            } ?: kotlin.run {
                cont.resume(RepoResultWrapper.Success(task.result))
            }
        }
    }
}

suspend fun <T> Task<T>.awaitOrNull(): T? {
    return suspendCancellableCoroutine { cont ->
        addOnSuccessListener {
            cont.resume(it)
        }

        addOnFailureListener {
            cont.resume(null)
        }

    }
}