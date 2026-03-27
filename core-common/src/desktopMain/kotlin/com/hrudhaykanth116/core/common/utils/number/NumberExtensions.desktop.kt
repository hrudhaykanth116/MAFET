package com.hrudhaykanth116.core.common.utils.number

actual fun formatFloat(value: Float, digitsAfterDecimalPoint: Int): String {
    return String.format("%.${digitsAfterDecimalPoint}f", value)
}
