package com.hrudhaykanth116.mafet.auth.domain.usecases

import com.hrudhaykanth116.mafet.common.data.data_models.DataResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValidatePasswordUseCase @Inject constructor(

) {

    operator fun invoke(pwd: String?): DataResult<String>{
        if (pwd?.length in 8..16){
            return DataResult.Success("Password is valid.")
        }else{
            return DataResult.Error("Password length should be between 8 and 16")
        }
    }

}