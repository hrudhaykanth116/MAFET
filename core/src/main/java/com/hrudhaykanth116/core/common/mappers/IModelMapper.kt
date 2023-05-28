package com.hrudhaykanth116.core.common.mappers

interface IModelMapper<INPUT, OUTPUT> {

    fun map(input: INPUT): OUTPUT

}