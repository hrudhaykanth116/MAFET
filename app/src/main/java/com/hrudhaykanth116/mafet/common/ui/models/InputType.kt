package com.hrudhaykanth116.mafet.common.ui.models

import android.graphics.drawable.Icon

sealed class InputType{
    object RegularInputType: InputType()
    object EmailInputType: InputType()
    object PwdInputType: InputType()
}
