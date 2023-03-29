package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.mafet.common.data.models.DataResult
import com.hrudhaykanth116.mafet.common.data.models.UIText
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateEmailUseCase @Inject constructor(

) {

    operator fun invoke(email: String?): DataResult<Unit> {
        if (!email.isNullOrBlank()) {
            if (email.contains("@")) {
                return DataResult.Success(Unit)
            } else {
                return DataResult.Error(
                    uiMessage = UIText.Text("Not valid email format")
                )
            }
        } else {
            return DataResult.Error(uiMessage = UIText.Text("Email cannot be empty"))
        }
    }

}