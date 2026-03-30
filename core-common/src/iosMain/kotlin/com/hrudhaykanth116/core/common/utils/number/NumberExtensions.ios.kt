package com.hrudhaykanth116.core.common.utils.number

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle

actual fun formatFloat(value: Float, digitsAfterDecimalPoint: Int): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterDecimalStyle
        minimumFractionDigits = digitsAfterDecimalPoint.toULong()
        maximumFractionDigits = digitsAfterDecimalPoint.toULong()
    }
    return formatter.stringFromNumber(NSNumber(value)) ?: value.toString()
}
