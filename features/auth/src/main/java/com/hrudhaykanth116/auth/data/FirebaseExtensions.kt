package com.hrudhaykanth116.auth.data

import com.google.android.gms.tasks.Task
import com.hrudhaykanth116.core.domain.result.DomainError
import com.hrudhaykanth116.core.domain.result.DomainResult
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun <T> Task<T>.await(): DomainResult<T> {
    return suspendCancellableCoroutine { cont ->

        addOnCompleteListener { task: Task<T> ->
            task.exception?.let { exception ->
                cont.resume(
                    DomainResult.Error(
                        error = DomainError.Unknown(throwable = exception)
                    )
                )
            } ?: run {
                cont.resume(DomainResult.Success(task.result))
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