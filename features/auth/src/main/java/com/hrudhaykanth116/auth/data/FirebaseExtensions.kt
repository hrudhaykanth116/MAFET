package com.hrudhaykanth116.auth.data

import com.google.android.gms.tasks.Task
import com.hrudhaykanth116.core.data.ErrorState
import com.hrudhaykanth116.core.data.RepoResultWrapper
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

// TODO: Move this to common place if needed in other modules
suspend fun <T> Task<T>.await(): RepoResultWrapper<T> {
    return suspendCancellableCoroutine { cont ->

        addOnCompleteListener { task: Task<T> ->
            task.exception?.let { exception ->
                cont.resume(
                    RepoResultWrapper.Error(
                        errorState = ErrorState.SomethingWentWrong
                    )
                )
            } ?: run {
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