package com.hrudhaykanth116.auth.domain.models.signup

import com.hrudhaykanth116.core.ui.models.UIText

sealed class SignUpEffect{
    data class Success(val message: UIText? = null): SignUpEffect()
    data class Error(val message: UIText? = null): SignUpEffect()
}