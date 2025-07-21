package com.hrudhaykanth116.media.ui.screens

import android.R.attr.apiKey
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
    private val pexelsRepository: PexelsRepository,
) : UIStateViewModel<MediaScreenUIState, MediaScreenEvent, MediaScreenEffect>(
    UIState.Idle(contentState = MediaScreenUIState())
) {

    init {
        viewModelScope.launch {
            try {
                // Test: Get curated photos
                val curatedPhotos: List<PhotoResponse> =
                    pexelsRepository.getCuratedPhotos(page = Random.nextInt(1, 10), perPage = 20)
                setState {
                    copyUIState(newContentState = contentState?.copy(photoList = curatedPhotos))
                }

                val searchedPhotos =
                    pexelsRepository.searchPhotos(query = "nature", page = 1, perPage = 10)

                val photoId = curatedPhotos.firstOrNull()?.id ?: 0
                val photo = pexelsRepository.getPhotoById(photoId)

            } catch (e: Exception) {
                Log.e(TAG, "API error: ${e.message}", e)
            }
        }

        viewModelScope.launch {
            try {
                val response = pexelsRepository.getPopularVideos(perPage = 5)

                Log.d(TAG, "Popular Videos: $response")

                val videoUrl = response.videos
                    ?.firstOrNull()
                    ?.videoFiles
                    ?.firstOrNull { it?.fileType == "video/mp4" }
                    ?.link

                Log.d(TAG, "Popular Video: $videoUrl")

                // val videoUrl =  "https://player.vimeo.com/external/342571552.sd.mp4?s=e0df43853c25598dfd0ec4d3f413bce1e002deef&profile_id=164&oauth2_token_id=57447761"

                // val videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

                // val staticUrl = "https://www.pexels.com/video/a-person-holding-a-eucalyptus-plant-with-soil-6963395/"

                setState {
                    copyUIState(newContentState = contentState?.copy(videoUrl = videoUrl))
                }
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