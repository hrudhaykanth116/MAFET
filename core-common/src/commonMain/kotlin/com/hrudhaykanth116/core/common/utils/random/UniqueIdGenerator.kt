package com.hrudhaykanth116.core.common.utils.random

import com.hrudhaykanth116.core.common.utils.uuid.randomUUID

class UniqueIdGenerator {

    fun getUniqueId(): String {
        return randomUUID()
    }

}
