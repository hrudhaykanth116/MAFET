package com.hrudhaykanth116.media.ui.screens

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.hrudhaykanth116.core.udf.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.media.data.models.PhotoResponse
import com.hrudhaykanth116.media.data.repositories.PexelsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val pexelsRepository: PexelsRepository
) : UIStateViewModel<MediaScreenUIState, MediaScreenEvent, MediaScreenEffect>(
    UIState.Idle(contentState = MediaScreenUIState())
) {

    init {
        viewModelScope.launch {
            try {
                // Test: Get curated photos
                val curatedPhotos: List<PhotoResponse> = pexelsRepository.getCuratedPhotos(page = Random.nextInt(1, 10), perPage = 20)
                Log.d(TAG, "Curated Photos: $curatedPhotos")
                setState {
                    copyUIState(newContentState = contentState?.copy(photoList = curatedPhotos))
                }

                val searchedPhotos = pexelsRepository.searchPhotos(query = "nature", page = 1, perPage = 10)
                Log.d(TAG, "Searched Photos: $searchedPhotos")

                val photoId = curatedPhotos.firstOrNull()?.id ?: 0
                val photo = pexelsRepository.getPhotoById(photoId)
                Log.d(TAG, "Photo By ID ($photoId): $photo")

            } catch (e: Exception) {
                Log.e(TAG, "API error: ${e.message}", e)
            }
        }
    }

    override fun processEvent(event: MediaScreenEvent) {
        when (event) {
            is MediaScreenEvent -> {

            }
        }
    }

    companion object {
        private const val TAG = "MediaViewModel"
    }

}