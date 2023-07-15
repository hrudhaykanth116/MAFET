package com.hrudhaykanth116.core.common.utils.random

import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UniqueIdGenerator @Inject constructor(

) {

    fun getUniqueId(): String {
        return UUID.randomUUID().toString()
    }

}