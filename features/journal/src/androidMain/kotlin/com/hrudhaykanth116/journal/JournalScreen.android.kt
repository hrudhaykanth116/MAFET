package com.hrudhaykanth116.journal

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import java.util.Locale

private const val TAG = "JournalScreen"

@Composable
actual fun JournalScreen() {
    val context = LocalContext.current

    val intent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }
    }

    var isListening by remember { mutableStateOf(false) }
    var spokenText by remember { mutableStateOf("") }

    val speechRecognizer = remember {
        SpeechRecognizer.createSpeechRecognizer(context)
    }

    var listener: RecognitionListener? = remember {
        null
    }

    fun restartListening() {
        speechRecognizer.stopListening()
        speechRecognizer.cancel()
        speechRecognizer.setRecognitionListener(listener)
        speechRecognizer.startListening(intent)
    }

    LaunchedEffect(Unit) {
        listener = object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d(TAG, "onReadyForSpeech: Ready to start listening")
            }

            override fun onBeginningOfSpeech() {
                Log.d(TAG, "onBeginningOfSpeech: User started speaking")
            }

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.d(TAG, "onBufferReceived: Audio buffer received")
            }

            override fun onEndOfSpeech() {
                Log.d(TAG, "onEndOfSpeech: User stopped speaking")
            }

            override fun onError(error: Int) {
                isListening = false
                val errorMessage = when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                    SpeechRecognizer.ERROR_CLIENT -> "Client side error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
                    SpeechRecognizer.ERROR_NETWORK -> "Network error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                    SpeechRecognizer.ERROR_NO_MATCH -> "No match found"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognizer busy"
                    SpeechRecognizer.ERROR_SERVER -> "Server error"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
                    else -> "Unknown error"
                }
                Log.e(TAG, "onError: $errorMessage (code: $error)")
                if (isListening) {
                    restartListening()
                }
            }

            override fun onResults(results: Bundle?) {
                val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                spokenText += (data?.getOrNull(0) ?: "") + " "
                if (isListening) {
                    restartListening()
                }
                Log.d(TAG, "onResults: $spokenText")
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val partial =
                    partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                Log.d(TAG, "onPartialResults: $partial")
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.d(TAG, "onEvent: eventType = $eventType")
            }
        }

        speechRecognizer.setRecognitionListener(listener)
    }

    CenteredColumn {
        val composition by rememberLottieComposition(LottieCompositionSpec.Asset("lottie_mic.json"))

        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = isListening,
            restartOnPlay = true,
            cancellationBehavior = LottieCancellationBehavior.Immediately
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = spokenText,
            modifier = Modifier.padding(bottom = 8.dp),
            style = TextStyle(color = Color.White)
        )

        Button(onClick = {
            Logger.d(TAG, "JournalScreen: start listening. isListening: $isListening")
            if (!isListening) {
                isListening = true
                restartListening()
            }
        }) {
            Text("Start Listening")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            Logger.d(TAG, "JournalScreen: stop listening")
            speechRecognizer.stopListening()
            isListening = false
        }) {
            Text("Stop Listening")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            speechRecognizer.destroy()
        }
    }
}
