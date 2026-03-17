package com.hrudhaykanth116.shared

import android.os.Build

actual class Platform {
    actual val name: String = "Android ${Build.VERSION.SDK_INT}"
}
