package com.hrudhaykanth116.mafet.common.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers

open class BaseRemoteDataSource {

    // fun <T> performNetworkOperation(networkCall: suspend () -> Resource<T>): LiveData<Resource<T>> =
    //
    //     liveData(Dispatchers.IO) {
    //         if (ConnectionStateMonitor.isConnected) {
    //             emit(Resource.loading())
    //             val responseStatus = networkCall.invoke()
    //             if (responseStatus.status == Status.SUCCESS) {
    //                 Timber.d(Gson().toJson(responseStatus.data!!))
    //                 emit(Resource.success(responseStatus.data))
    //             } else if (responseStatus.status == Status.ERROR) {
    //                 emit(Resource.error(responseStatus.message!!, null, responseStatus.error))
    //                 Timber.d(Gson().toJson(responseStatus.message))
    //             }
    //         } else {
    //             emit(
    //                 Resource.error(
    //                     LocaleHelper.getString(R.string.you_re_offline),
    //                     null,
    //                     ApiErrorExchange(ErrorUtility.getErrorMessage()).error(
    //                         LocaleHelper.getString(R.string.you_re_offline),
    //                         LocaleHelper.getString(R.string.looks_like_you_are_not_connected_to_a_network_check_your_data_or_wifi_connection),
    //                         "9999",
    //                         null,
    //                         null,
    //                     )
    //                 )
    //             )
    //         }
    //     }

}