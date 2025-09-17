package com.hrudhaykanth116.tv.ui.mappers

import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.data.models.UIText
import com.hrudhaykanth116.core.data.models.toUIText
import com.hrudhaykanth116.core.ui.models.constants.UIDefaultValues
import com.hrudhaykanth116.core.ui.models.toUrlImageHolder
import com.hrudhaykanth116.tv.domaintemp.models.MyTvDomainModel
import com.hrudhaykanth116.tv.ui.models.home.MyTvUIState

fun MyTvDomainModel.toUIState(dateTimeUtils: DateTimeUtils): MyTvUIState {

    // hrudhay_check_list: Handle null case
    val lastWatchedSeasonEpisode = UIText.Text("S${lastWatchedSeason ?: 0}E${lastWatchedEpisode ?: 0}")

    return MyTvUIState(
        id = id,
        name = name.toUIText(),
        lastWatchedSeason = lastWatchedSeason,
        lastWatchedEpisode = lastWatchedEpisode,
        lastWatchedSeasonEpisode = lastWatchedSeasonEpisode,
        lastWatchedTimeUIText = dateTimeUtils.getDateFromMillis(lastWatchedTime)?.toUIText() ?: UIDefaultValues.EMPTY_VALUE.toUIText(),
        lastWatchedTime = lastWatchedTime,
        imgSource = imgSource?.toUrlImageHolder()
    )
}

fun List<MyTvDomainModel>.toUIState(dateTimeUtils: DateTimeUtils): List<MyTvUIState>? {


    return map {
        it.toUIState(dateTimeUtils)
    }
}