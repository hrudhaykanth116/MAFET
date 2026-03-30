package com.hrudhaykanth116.tv.data.mappers

import com.hrudhaykanth116.tv.data.datasources.local.models.MyTvEntity
import com.hrudhaykanth116.tv.domain.models.MyTv

fun MyTvEntity.toDomain(): MyTv {
    return MyTv(
        id = id,
        name = name,
        lastWatchedSeason = lastWatchedSeason,
        lastWatchedEpisode = lastWatchedEpisode,
        lastWatchedTime = lastWatchedTime,
        imgSource = imgSource
    )
}

fun MyTv.toEntity(): MyTvEntity {
    return MyTvEntity(
        id = id,
        name = name,
        lastWatchedSeason = lastWatchedSeason,
        lastWatchedEpisode = lastWatchedEpisode,
        lastWatchedTime = lastWatchedTime,
        imgSource = imgSource
    )
}

fun List<MyTvEntity>.toDomainMyTvList(): List<MyTv> {
    return map { it.toDomain() }
}

fun List<MyTv>.toEntityMyTvList(): List<MyTvEntity> {
    return map { it.toEntity() }
}
