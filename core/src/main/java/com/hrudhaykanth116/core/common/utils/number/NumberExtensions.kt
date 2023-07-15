package com.hrudhaykanth116.weathertens.common.utils.number

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
            String.format("%.${digitsAfterDecimalPoint}f", this)
        }
    }
}

