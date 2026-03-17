package com.hrudhaykanth116.core.common.utils.random

import java.util.UUID

class UniqueIdGenerator {

    fun getUniqueId(): String {
        return UUID.randomUUID().toString()
    }

}