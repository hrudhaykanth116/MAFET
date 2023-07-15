package com.hrudhaykanth116.core.common.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hrudhaykanth116.core.ui.StatefulViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun <STATE, EFFECT, EVENT> HandleEffect(
    viewModel: StatefulViewModel<STATE, EFFECT, EVENT>,
    handle: suspend CoroutineScope.(EFFECT) -> Unit
) {
    // val effect by viewModel.effect.collectAsStateWithLifecycle()
    // LaunchedEffect(effect) {
    //     effect?.let {
    //         handle(it)
    //         viewModel.resetEffect()
    //     }
    // }
}