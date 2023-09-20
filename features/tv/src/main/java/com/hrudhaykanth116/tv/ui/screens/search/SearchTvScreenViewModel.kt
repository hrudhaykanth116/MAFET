package com.hrudhaykanth116.tv.ui.screens.search

import com.hrudhaykanth116.core.udf.UDFViewModel
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenEffect
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenEvent
import com.hrudhaykanth116.tv.ui.models.search.SearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchTvScreenViewModel @Inject constructor(

) : UDFViewModel<SearchScreenState, SearchScreenEvent, SearchScreenEffect>(
    SearchScreenState("")
) {

    override fun processEvent(event: SearchScreenEvent) {



    }


}