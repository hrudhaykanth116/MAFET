package com.hrudhaykanth116.media.ui.screens.detail

import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.download.ImageDownloadManager
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.toErrorMessage
import com.hrudhaykanth116.core.ui.models.toSuccessMessage
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.media.domain.models.MediaType
import com.hrudhaykanth116.media.domain.usecases.GetMediaDetailUseCase
import com.hrudhaykanth116.media.platform.MediaPlatformActions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MediaDetailViewModel(
    private val getMediaDetailUseCase: GetMediaDetailUseCase,
    private val platformActions: MediaPlatformActions,
    private val downloadManager: ImageDownloadManager,
    private val networkMonitor: NetworkMonitor,
    private val dispatcher: CoroutineDispatcher,
    private val mediaId: Int,
    private val mediaType: MediaType
) : UIStateViewModel<MediaDetailUIState, MediaDetailEvent, MediaDetailEffect>(
    initialState = UIState.Idle(MediaDetailUIState()),
    defaultState = MediaDetailUIState(),
    networkMonitor = networkMonitor
) {

    override fun initializeData() {
        loadMediaDetail()
    }

    override fun processEvent(event: MediaDetailEvent) {
        when (event) {
            is MediaDetailEvent.OnBackClick -> setEffect(MediaDetailEffect.NavigateBack)
            is MediaDetailEvent.OnShareClick -> handleShareClick()
            is MediaDetailEvent.OnDownloadClick -> handleDownloadClick(event.quality)
            is MediaDetailEvent.OnPhotographerClick -> handlePhotographerClick()
        }
    }

    private fun loadMediaDetail() {
        viewModelScope.launch(dispatcher) {
            setLoadingState(contentStateOrDefault)

            when (val result = getMediaDetailUseCase(mediaId, mediaType)) {
                is DomainResult.Success -> {
                    setIdleState {
                        copy(mediaItem = result.data)
                    }
                }
                is DomainResult.Error -> {
                    setState {
                        UIState.Error(
                            errorState = result.error,
                            contentState = contentStateOrDefault
                        )
                    }
                }
            }
        }
    }

    private fun handleShareClick() {
        val mediaItem = contentStateOrDefault.mediaItem ?: return
        setEffect(
            MediaDetailEffect.ShareMedia(
                url = mediaItem.originalUrl,
                photographer = "Photo by ${mediaItem.photographer} on Pexels"
            )
        )
    }

    private fun handleDownloadClick(quality: DownloadQuality) {
        val mediaItem = contentStateOrDefault.mediaItem ?: return

        val url = when (quality) {
            DownloadQuality.ORIGINAL -> mediaItem.originalUrl
            DownloadQuality.MEDIUM -> mediaItem.mediumUrl
            DownloadQuality.SMALL -> mediaItem.smallUrl
        }

        val filename = "pexels_${mediaItem.id}_${quality.name.lowercase()}.${if (mediaItem.type == MediaType.VIDEOS) "mp4" else "jpg"}"

        viewModelScope.launch(dispatcher) {
            val success = downloadManager.downloadFile(url, filename)

            if (success) {
                showUserMessage("Download completed successfully".toUIText().toSuccessMessage())
            } else {
                showUserMessage("Download failed. Please try again.".toUIText().toErrorMessage())
            }
        }
    }

    private fun handlePhotographerClick() {
        val mediaItem = contentStateOrDefault.mediaItem ?: return
        setEffect(MediaDetailEffect.OpenUrl(mediaItem.photographerUrl))
    }
}
