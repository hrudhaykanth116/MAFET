package com.hrudhaykanth116.tv.domaintemp.mappers

import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel

class MyTvListMapper(

)

fun MyTvEntity.toMyTvDomainModel(): MyTvDomainModel {
    return MyTvDomainModel(
        id = id,
        name = name,
        lastWatchedSeason = lastWatchedSeason,
        lastWatchedEpisode = lastWatchedEpisode,
        lastWatchedTime = lastWatchedTime,
        imgSource = imgSource
    )
}

fun MyTvDomainModel.toMyTvDataEntity(): MyTvEntity {
    return MyTvEntity(
        id = id,
        name = name,
        lastWatchedSeason = lastWatchedSeason,
        lastWatchedEpisode = lastWatchedEpisode,
        lastWatchedTime = lastWatchedTime,
        imgSource = imgSource
    )
}


// hrudhay_check_list: Create generic list mapper
fun List<MyTvEntity>.toDomainModel(): List<MyTvDomainModel> {

    return map {
        it.toMyTvDomainModel()
    }

}

fun List<MyTvDomainModel>.toMyTvDataEntity(): List<MyTvEntity> {
    return map {
        it.toMyTvDataEntity()
    }
}

