package com.hrudhaykanth116.media.ui.screens

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.media.data.models.PhotoResponse
import com.hrudhaykanth116.media.data.repositories.PexelsRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class MediaViewModel(
    private val pexelsRepository: PexelsRepository,
    private val networkMonitor: NetworkMonitor,
) : UIStateViewModel<MediaScreenUIState, MediaScreenEvent, MediaScreenEffect>(
    initialState = UIState.Idle(contentState = MediaScreenUIState()),
    defaultState = MediaScreenUIState(),
    networkMonitor = networkMonitor
) {

    init {
        initializeData()
    }

    override fun initializeData() {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                // Test: Get curated photos
                when (val result = pexelsRepository.getCuratedPhotos(page = Random.nextInt(1, 10), perPage = 20)) {
                    is DomainResult.Success -> {
                        val curatedPhotos: List<PhotoResponse> = result.data.photos
                        setState {
                            UIState.Idle(contentState = contentState?.copy(photoList = curatedPhotos))
                        }

                        val photoId = curatedPhotos.firstOrNull()?.id ?: 0
                        when (val photoResult = pexelsRepository.getPhotoById(photoId)) {
                            is DomainResult.Success -> {
                                Logger.d(TAG, "Photo: ${photoResult.data}")
                            }
                            is DomainResult.Error -> {
                                Logger.e(TAG, "Photo error: ${photoResult.error.toMessage()}")
                            }
                        }
                    }
                    is DomainResult.Error -> {
                        Logger.e(TAG, "Curated photos error: ${result.error.toMessage()}")
                    }
                }

                when (val searchResult = pexelsRepository.searchPhotos(query = "nature", page = 1, perPage = 10)) {
                    is DomainResult.Success -> {
                        Logger.d(TAG, "Search photos: ${searchResult.data}")
                    }
                    is DomainResult.Error -> {
                        Logger.e(TAG, "Search photos error: ${searchResult.error.toMessage()}")
                    }
                }

            } catch (e: Exception) {
                Logger.e(TAG, "API error: ${e.message}", e)
            }
        }

        viewModelScope.launch {
            try {
                when (val result = pexelsRepository.getPopularVideos(perPage = 5)) {
                    is DomainResult.Success -> {
                        val response = result.data
                        Logger.d(TAG, "Popular Videos: $response")

                        val videoUrl = response.videos
                            ?.firstOrNull()
                            ?.videoFiles
                            ?.firstOrNull { it?.fileType == "video/mp4" }
                            ?.link

                        Logger.d(TAG, "Popular Video: $videoUrl")

                        // val videoUrl =  "https://player.vimeo.com/external/342571552.sd.mp4?s=e0df43853c25598dfd0ec4d3f413bce1e002deef&profile_id=164&oauth2_token_id=57447761"

                        // val videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

                        // val staticUrl = "https://www.pexels.com/video/a-person-holding-a-eucalyptus-plant-with-soil-6963395/"

                        if (!videoUrl.isNullOrBlank()) {
                            setState {
                                UIState.Idle(contentState = contentState?.copy(videoUrl = videoUrl))
                            }
                        } else {
                            Logger.e(TAG, "No valid video URL found")
                        }
                    }
                    is DomainResult.Error -> {
                        Logger.e(TAG, "Popular videos error: ${result.error.toMessage()}")
                    }
                }
            } catch (e: Exception) {
                Logger.e(TAG, "API error: ${e.message}", e)
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