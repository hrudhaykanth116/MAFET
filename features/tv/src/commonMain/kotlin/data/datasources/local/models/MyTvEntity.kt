package com.hrudhaykanth116.tv.data.datasources.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyTvEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "last_watched_season") val lastWatchedSeason: Int?,
    @ColumnInfo(name = "last_watched_episode") val lastWatchedEpisode: Int?,
    @ColumnInfo(name = "last_watched_time") val lastWatchedTime: Long?,
    @ColumnInfo(name = "image_source") val imgSource: String?,
    @ColumnInfo(name = "status", defaultValue = "0") val status: Int = WatchStatus.WATCHING.ordinal,
    @ColumnInfo(name = "rating", defaultValue = "NULL") val rating: Int? = null,
    @ColumnInfo(name = "notes", defaultValue = "NULL") val notes: String? = null,
)
