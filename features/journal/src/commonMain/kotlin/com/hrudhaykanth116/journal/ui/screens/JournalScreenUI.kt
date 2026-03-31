package com.hrudhaykanth116.journal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.core.ui.modifier.screenBackground
import com.hrudhaykanth116.core.ui.preview.AppPreviewContainer
import com.hrudhaykanth116.core.ui.preview.MyPreview
import com.hrudhaykanth116.journal.resources.Res
import com.hrudhaykanth116.journal.resources.journal_clear_button
import com.hrudhaykanth116.journal.resources.journal_empty_subtitle
import com.hrudhaykanth116.journal.resources.journal_empty_title
import com.hrudhaykanth116.journal.resources.journal_input_hint
import com.hrudhaykanth116.journal.resources.journal_save_button
import com.hrudhaykanth116.journal.resources.journal_screen_title
import com.hrudhaykanth116.journal.ui.models.JournalUIState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreenUI(
    modifier: Modifier = Modifier,
    uiState: JournalUIState,
    onTextChanged: (TextFieldValue) -> Unit = {},
    onSaveClicked: () -> Unit = {},
    onClearClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.journal_screen_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .screenBackground()
                .padding(paddingValues)
        ) {
            if (uiState.journalText.text.isEmpty() && !uiState.isSaving) {
                EmptyStateContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    TextField(
                        value = uiState.journalText,
                        onValueChange = onTextChanged,
                        placeholder = {
                            Text(
                                text = stringResource(Res.string.journal_input_hint),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onClearClicked,
                        modifier = Modifier.weight(1f),
                        enabled = uiState.journalText.text.isNotEmpty() && !uiState.isSaving
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(Res.string.journal_clear_button))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = onSaveClicked,
                        modifier = Modifier.weight(1f),
                        enabled = uiState.journalText.text.isNotEmpty() && !uiState.isSaving
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(Res.string.journal_save_button))
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyStateContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.journal_empty_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.journal_empty_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        )
    }
}

@MyPreview
@Composable
fun JournalScreenUIPreview() {
    AppPreviewContainer {
        JournalScreenUI(
            uiState = JournalUIState()
        )
    }
}

@MyPreview
@Composable
fun JournalScreenUIWithTextPreview() {
    AppPreviewContainer {
        JournalScreenUI(
            uiState = JournalUIState(
                journalText = TextFieldValue("This is a sample journal entry...")
            )
        )
    }
}
