package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.mafet.common.data.models.DataResult
import com.hrudhaykanth116.mafet.common.data.models.UIText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidatePasswordUseCase @Inject constructor(

) {

    operator fun invoke(pwd: String?): DataResult<UIText> {
        if (pwd?.length in 8..16) {
            return DataResult.Success(
                UIText.Text("Password is valid.")
            )
        } else {
            return DataResult.Error(
                UIText.Text("Password length should be between 8 and 16")
            )
        }
    }

}