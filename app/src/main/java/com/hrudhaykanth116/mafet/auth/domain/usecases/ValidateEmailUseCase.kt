package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.mafet.common.data.data_models.DataResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidateEmailUseCase @Inject constructor(

) {

    operator fun invoke(email: String?): DataResult<String>{
        if (!email.isNullOrBlank()) {
            if (email.contains("@")) {
                return DataResult.Success("Email is valid")
            }else{
                return DataResult.Error("Not valida email format")
            }
        }else{
            return DataResult.Error("Email cannot be empty")
        }
    }

}