package com.hrudhaykanth116.core.common.utils.number

expect fun formatFloat(value: Float, digitsAfterDecimalPoint: Int): String

fun Float?.truncateToDecimalsIfDecimalNumber(digitsAfterDecimalPoint: Int): String? {
    return if (this?.minus(this.toInt()) == 0.0f) this.toInt().toString() else truncateToDecimals(digitsAfterDecimalPoint)
}

fun Float?.truncateToDecimals(digitsAfterDecimalPoint: Int): String? {
    return when {
        this == null -> {
            null
        }
        this == 0.0f -> {
            "0"
        }
        else -> {
            formatFloat(this, digitsAfterDecimalPoint)
        }
    }
}

