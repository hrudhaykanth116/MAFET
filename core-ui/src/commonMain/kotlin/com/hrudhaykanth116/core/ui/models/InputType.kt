package com.hrudhaykanth116.core.ui.models

sealed class InputType{
    object RegularInputType: InputType()
    object EmailInputType: InputType()
    object PwdInputType: InputType()
}
