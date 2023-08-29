package com.hrudhaykanth116.core.common.utils.string

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Replaces the string with given value if the string is null or blank
 */
// @OptIn(ExperimentalContracts::class)
fun String?.replaceIfBlank(value: String): String {

    // contract {
    //     returns(true) implies (this@replaceIfBlank != null)
    // }

    return if (isNullOrEmpty()) {
        value
    } else
        this
}