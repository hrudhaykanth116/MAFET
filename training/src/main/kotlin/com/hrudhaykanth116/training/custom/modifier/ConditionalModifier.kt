package com.hrudhaykanth116.training.custom.modifier

import androidx.compose.ui.Modifier

fun Modifier.onCondition(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
){

    if (condition) {
        then(modifier())
    }


}