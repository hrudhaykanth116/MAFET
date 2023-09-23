package com.hrudhaykanth116.tv.data.datasources.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.ui.models.ImageHolder

@Entity
data class MyTvEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "current_episode") val currentEpisode: String,
    @ColumnInfo(name = "last_watched") val lastWatched: String,
    @ColumnInfo(name = "image_source") val imgSource: String,
)
