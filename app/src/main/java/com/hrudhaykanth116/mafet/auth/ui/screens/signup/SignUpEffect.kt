package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import com.hrudhaykanth116.core.data.models.UIText

sealed class SignUpEffect{
    data class Success(val message: com.hrudhaykanth116.core.data.models.UIText? = null): SignUpEffect()
    data class Error(val message: com.hrudhaykanth116.core.data.models.UIText? = null): SignUpEffect()
}