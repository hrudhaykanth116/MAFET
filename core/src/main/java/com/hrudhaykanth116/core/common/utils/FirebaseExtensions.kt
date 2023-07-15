package com.hrudhaykanth116.core.common.utils

import com.google.android.gms.tasks.Task
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun <T> Task<T>.await(): DataResult<T> {
    return suspendCancellableCoroutine { cont ->

        // TODO: Use success failure as below

        addOnCompleteListener { task: Task<T> ->
            task.exception?.let { exception ->
                cont.resume(
                    DataResult.Error(
                        exception.message?.let {
                            UIText.Text(it)
                        } ?: UIText.StringRes(R.string.something_went_wrong)
                    )
                )
            } ?: kotlin.run {
                cont.resume(DataResult.Success(task.result))
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