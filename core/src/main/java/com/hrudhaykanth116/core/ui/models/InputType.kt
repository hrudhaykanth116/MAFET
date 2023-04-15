package com.hrudhaykanth116.core.ui.models

import android.graphics.drawable.Icon

sealed class InputType{
    object RegularInputType: InputType()
    object EmailInputType: InputType()
    object PwdInputType: InputType()
}
