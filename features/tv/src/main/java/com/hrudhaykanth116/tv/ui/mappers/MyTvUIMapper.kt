package com.hrudhaykanth116.tv.ui.mappers

import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState

class MyTvUIMapper {
}

fun MyTvDomainModel.toUIState(): MyTvUIState {
    return MyTvUIState(
        id = id,
        name = name.toUIText(),
        currentEpisode = currentEpisode.toUIText(),
        lastWatched = lastWatched.toUIText(),
        imgSource = imgSource.toUrlImageHolder()

    )
}

fun List<MyTvDomainModel>.toUIState(): List<MyTvUIState>? {


    return map {
        it.toUIState()
    }
}