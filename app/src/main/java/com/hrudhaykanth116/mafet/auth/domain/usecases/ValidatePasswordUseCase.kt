package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.core.data.models.DataResult
import com.hrudhaykanth116.core.data.models.UIText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidatePasswordUseCase @Inject constructor(

) {

    operator fun invoke(pwd: String?): com.hrudhaykanth116.core.data.models.DataResult<com.hrudhaykanth116.core.data.models.UIText> {
        if (pwd?.length in 8..16) {
            return com.hrudhaykanth116.core.data.models.DataResult.Success(
                com.hrudhaykanth116.core.data.models.UIText.Text("Password is valid.")
            )
        } else {
            return com.hrudhaykanth116.core.data.models.DataResult.Error(
                com.hrudhaykanth116.core.data.models.UIText.Text("Password length should be between 8 and 16")
            )
        }
    }

}