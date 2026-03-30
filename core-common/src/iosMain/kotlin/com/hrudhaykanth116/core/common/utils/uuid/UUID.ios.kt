package com.hrudhaykanth116.core.common.utils.uuid

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()
