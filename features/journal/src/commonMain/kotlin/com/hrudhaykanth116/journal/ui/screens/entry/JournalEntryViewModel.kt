package com.hrudhaykanth116.journal.ui.screens.entry

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.hrudhaykanth116.core.common.time.TimeProvider
import com.hrudhaykanth116.core.common.utils.date.DateTimeUtils
import com.hrudhaykanth116.core.common.utils.random.UniqueIdGenerator
import com.hrudhaykanth116.core.domain.result.DomainResult
import com.hrudhaykanth116.core.ui.NetworkMonitor
import com.hrudhaykanth116.core.ui.models.UIState
import com.hrudhaykanth116.core.ui.models.UIText
import com.hrudhaykanth116.core.ui.models.toErrorMessage
import com.hrudhaykanth116.core.ui.models.toSuccessMessage
import com.hrudhaykanth116.core.ui.models.toUIText
import com.hrudhaykanth116.core.ui.viewmodels.UIStateViewModel
import com.hrudhaykanth116.journal.domain.model.JournalEntry
import com.hrudhaykanth116.journal.domain.use_cases.CreateEntryUseCase
import com.hrudhaykanth116.journal.domain.use_cases.GetEntryUseCase
import com.hrudhaykanth116.journal.domain.use_cases.UpdateEntryUseCase
import com.hrudhaykanth116.journal.resources.Res
import com.hrudhaykanth116.journal.resources.journal_error_body_required
import com.hrudhaykanth116.journal.resources.journal_error_title_required
import com.hrudhaykanth116.journal.resources.journal_success_created
import com.hrudhaykanth116.journal.resources.journal_success_updated
import com.hrudhaykanth116.journal.ui.models.entry.JournalEntryEffect
import com.hrudhaykanth116.journal.ui.models.entry.JournalEntryScreenEvent
import com.hrudhaykanth116.journal.ui.models.entry.JournalEntryUIState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class JournalEntryViewModel(
    private val createEntryUseCase: CreateEntryUseCase,
    private val updateEntryUseCase: UpdateEntryUseCase,
    private val getEntryUseCase: GetEntryUseCase,
    private val networkMonitor: NetworkMonitor,
    private val uniqueIdGenerator: UniqueIdGenerator,
    private val dateTimeUtils: DateTimeUtils,
    private val timeProvider: TimeProvider,
    private val dispatcher: CoroutineDispatcher,
    private val entryId: String?
) : UIStateViewModel<JournalEntryUIState, JournalEntryScreenEvent, JournalEntryEffect>(
    initialState = UIState.Idle(JournalEntryUIState(isEditMode = entryId != null)),
    defaultState = JournalEntryUIState(),
    networkMonitor = networkMonitor
) {

    override fun initializeData() {
        if (entryId != null) {
            loadEntry(entryId)
        } else {
            val currentTime = timeProvider.currentTimeMillis()
            setState {
                UIState.Idle(
                    contentStateOrDefault.copy(
                        selectedDate = currentTime,
                        formattedDate = formatDate(currentTime)
                    )
                )
            }
        }
    }

    private fun loadEntry(id: String) {
        setState { UIState.Idle(contentStateOrDefault.copy(isLoading = true)) }

        viewModelScope.launch(dispatcher) {
            when (val result = getEntryUseCase(id)) {
                is DomainResult.Success -> {
                    val entry = result.data
                    setState {
                        UIState.Idle(
                            contentStateOrDefault.copy(
                                id = entry.id,
                                title = TextFieldValue(entry.title),
                                body = TextFieldValue(entry.body),
                                mood = entry.mood,
                                tags = entry.tags.toImmutableList(),
                                selectedDate = entry.createdAt,
                                formattedDate = formatDate(entry.createdAt),
                                isEditMode = true,
                                isLoading = false
                            )
                        )
                    }
                }
                is DomainResult.Error -> {
                    setState { UIState.Idle(contentStateOrDefault.copy(isLoading = false)) }
                    showUserMessage(result.error.toMessage().toUIText().toErrorMessage())
                }
            }
        }
    }

    private fun formatDate(millis: Long): String {
        return dateTimeUtils.getFormattedDateTime(millis, DateTimeUtils.DAY_DATE_FORMAT) ?: ""
    }

    override fun processEvent(event: JournalEntryScreenEvent) {
        when (event) {
            is JournalEntryScreenEvent.OnTitleChanged -> onTitleChanged(event.title)
            is JournalEntryScreenEvent.OnBodyChanged -> onBodyChanged(event.body)
            is JournalEntryScreenEvent.OnMoodChanged -> onMoodChanged(event.mood)
            is JournalEntryScreenEvent.OnTagInputChanged -> onTagInputChanged(event.tagInput)
            JournalEntryScreenEvent.OnAddTag -> onAddTag()
            is JournalEntryScreenEvent.OnRemoveTag -> onRemoveTag(event.tag)
            JournalEntryScreenEvent.OnDateFieldClicked -> onDateFieldClicked()
            is JournalEntryScreenEvent.OnDateSelected -> onDateSelected(event.dateMillis)
            JournalEntryScreenEvent.OnDatePickerDismissed -> onDatePickerDismissed()
            JournalEntryScreenEvent.OnSaveClicked -> onSaveClicked()
        }
    }

    private fun onTitleChanged(title: TextFieldValue) {
        setState { UIState.Idle(contentState?.copy(title = title)) }
    }

    private fun onBodyChanged(body: TextFieldValue) {
        setState { UIState.Idle(contentState?.copy(body = body)) }
    }

    private fun onMoodChanged(mood: Int) {
        setState { UIState.Idle(contentState?.copy(mood = mood)) }
    }

    private fun onTagInputChanged(tagInput: TextFieldValue) {
        setState { UIState.Idle(contentState?.copy(tagInput = tagInput)) }
    }

    private fun onAddTag() {
        val currentState = contentStateOrDefault
        val newTag = currentState.tagInput.text.trim()
        if (newTag.isNotBlank() && !currentState.tags.contains(newTag)) {
            setState {
                UIState.Idle(
                    currentState.copy(
                        tags = (currentState.tags + newTag).toImmutableList(),
                        tagInput = TextFieldValue()
                    )
                )
            }
        }
    }

    private fun onRemoveTag(tag: String) {
        val currentState = contentStateOrDefault
        setState {
            UIState.Idle(
                currentState.copy(
                    tags = currentState.tags.filter { it != tag }.toImmutableList()
                )
            )
        }
    }

    private fun onDateFieldClicked() {
        setState { UIState.Idle(contentState?.copy(isDatePickerVisible = true)) }
    }

    private fun onDateSelected(dateMillis: Long?) {
        dateMillis?.let {
            setState {
                UIState.Idle(
                    contentState?.copy(
                        selectedDate = it,
                        formattedDate = formatDate(it),
                        isDatePickerVisible = false
                    )
                )
            }
        }
    }

    private fun onDatePickerDismissed() {
        setState { UIState.Idle(contentState?.copy(isDatePickerVisible = false)) }
    }

    private fun onSaveClicked() {
        val currentState = contentStateOrDefault

        if (currentState.title.text.isBlank()) {
            showUserMessage(UIText.StringRes(Res.string.journal_error_title_required).toErrorMessage())
            return
        }

        if (currentState.body.text.isBlank()) {
            showUserMessage(UIText.StringRes(Res.string.journal_error_body_required).toErrorMessage())
            return
        }

        setState { UIState.Idle(currentState.copy(isSaving = true)) }

        viewModelScope.launch(dispatcher) {
            val entry = JournalEntry(
                id = currentState.id ?: uniqueIdGenerator.getUniqueId(),
                title = currentState.title.text,
                body = currentState.body.text,
                mood = currentState.mood,
                tags = currentState.tags,
                createdAt = currentState.selectedDate ?: timeProvider.currentTimeMillis(),
                updatedAt = 0
            )

            val result = if (currentState.isEditMode) {
                updateEntryUseCase(entry)
            } else {
                createEntryUseCase(entry)
            }

            when (result) {
                is DomainResult.Success -> {
                    val message = if (currentState.isEditMode) {
                        UIText.StringRes(Res.string.journal_success_updated)
                    } else {
                        UIText.StringRes(Res.string.journal_success_created)
                    }
                    showUserMessage(message.toSuccessMessage())
                    setEffect(JournalEntryEffect.NavigateBack)
                }
                is DomainResult.Error -> {
                    setState { UIState.Idle(contentStateOrDefault.copy(isSaving = false)) }
                    showUserMessage(result.error.toMessage().toUIText().toErrorMessage())
                }
            }
        }
    }
}
