package com.hrudhaykanth116.core.common.mappers

interface NullableInputListMapper<I, O> : IModelMapper<List<I>?, List<O>>

class NullableInputListMapperImpl<I, O>(
    private val mapper: IModelMapper<I, O>
) : NullableInputListMapper<I, O> {

    override fun map(input: List<I>?): List<O> {
        return input?.map { mapper.map(it) }.orEmpty()
    }

}