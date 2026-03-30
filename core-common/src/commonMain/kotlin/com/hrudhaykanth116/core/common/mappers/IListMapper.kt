package com.hrudhaykanth116.core.common.mappers

interface IListMapper<INPUT, OUTPUT> : IModelMapper<List<INPUT>, List<OUTPUT>>

class ListMapperImpl<I, O>(
    private val mapper: IModelMapper<I, O>
) : IListMapper<I, O> {

    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }

}