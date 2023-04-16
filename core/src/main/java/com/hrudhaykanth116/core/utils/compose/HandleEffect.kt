package com.hrudhaykanth116.core.utils.compose

import androidx.compose.runtime.Composable
import com.hrudhaykanth116.core.udf.UDFViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun <STATE, EVENT, EFFECT> HandleEffect(
    viewModel: UDFViewModel<STATE, EVENT, EFFECT>,
    handle: suspend CoroutineScope.(EFFECT) -> Unit
) {
    // val effect by viewModel.effect.collectAsStateWithLifecycle(
    // )
    // LaunchedEffect(effect) {
    //     effect?.let {
    //         handle(it)
    //         viewModel.resetEffect()
    //     }
    // }
}