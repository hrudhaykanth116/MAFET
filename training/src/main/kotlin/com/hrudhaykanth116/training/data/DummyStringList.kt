package com.hrudhaykanth116.training.data

fun getDummyStringList(size: Int = 100): List<Int> {

    val list = mutableListOf<Int>()
    for(index in 1..size){
        list.add(index)
    }
    return list

}