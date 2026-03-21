package com.hrudhaykanth116.media.ui.screens

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.data.RepoResultWrapper
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
                    is RepoResultWrapper.Success -> {
                        val curatedPhotos: List<PhotoResponse> = result.data.photos
                        setState {
                            UIState.Idle(contentState = contentState?.copy(photoList = curatedPhotos))
                        }

                        val photoId = curatedPhotos.firstOrNull()?.id ?: 0
                        when (val photoResult = pexelsRepository.getPhotoById(photoId)) {
                            is RepoResultWrapper.Success -> {
                                Log.d(TAG, "Photo: ${photoResult.data}")
                            }
                            is RepoResultWrapper.Error -> {
                                Log.e(TAG, "Photo error: ${photoResult.errorState}")
                            }
                        }
                    }
                    is RepoResultWrapper.Error -> {
                        Log.e(TAG, "Curated photos error: ${result.errorState}")
                    }
                }

                when (val searchResult = pexelsRepository.searchPhotos(query = "nature", page = 1, perPage = 10)) {
                    is RepoResultWrapper.Success -> {
                        Log.d(TAG, "Search photos: ${searchResult.data}")
                    }
                    is RepoResultWrapper.Error -> {
                        Log.e(TAG, "Search photos error: ${searchResult.errorState}")
                    }
                }

            } catch (e: Exception) {
                Log.e(TAG, "API error: ${e.message}", e)
            }
        }

        viewModelScope.launch {
            try {
                when (val result = pexelsRepository.getPopularVideos(perPage = 5)) {
                    is RepoResultWrapper.Success -> {
                        val response = result.data
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
                            UIState.Idle(contentState = contentState?.copy(videoUrl = videoUrl))
                        }
                    }
                    is RepoResultWrapper.Error -> {
                        Log.e(TAG, "Popular videos error: ${result.errorState}")
                    }
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