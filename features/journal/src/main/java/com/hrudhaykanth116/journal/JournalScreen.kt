package com.hrudhaykanth116.journal

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.hrudhaykanth116.core.common.compose.rememberTextToSpeech
import com.hrudhaykanth116.core.common.utils.log.Logger
import com.hrudhaykanth116.core.ui.components.CenteredColumn
import com.hrudhaykanth116.journal.ui.theme.MAFETTheme
import java.util.Locale
import com.airbnb.lottie.compose.*


private const val TAG = "JournalScreen"

@Composable
fun JournalScreenTemp(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val speechText = remember { mutableStateOf("Your speech will appear here.") }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                speechText.value = result?.get(0) ?: "No speech detected."
            } else {
                speechText.value = "[Speech recognition failed.]"
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d("Permission", "Permission granted")
        } else {
            Log.d("Permission", "Permission denied")
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {

            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Go on then, say something.")
                launcher.launch(intent)
            } else {
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }


        }) {
            Text("Start speech recognition")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(speechText.value)
    }

}

@Composable
fun JournalScreen() {
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

            override fun onRmsChanged(rmsdB: Float) {
                // Log.d(TAG, "onRmsChanged: RMS dB = $rmsdB")
            }

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
                // isListening = false
                val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                spokenText += (data?.getOrNull(0) ?: "") + " "
                if (isListening) {
                    restartListening()
                }
                Log.d(TAG, "onResults: ${spokenText}")
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

    CenteredColumn() {

        val composition by rememberLottieComposition(LottieCompositionSpec.Asset("lottie_mic.json"))
        // val lottieAnimatable = rememberLottieAnimatable()

        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = isListening,
            restartOnPlay = true,
            cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = spokenText, modifier = Modifier.padding(bottom = 8.dp), style = TextStyle(
                color = Color.White
            )
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
            speechRecognizer.stopListening() // ⛔️ Stop manually
            isListening = false
            // lottieAnimatable.resetToBeginning()
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


@Preview(showBackground = true)
@Composable
private fun JournalScreenPreview() {
    MAFETTheme {
        JournalScreen()
    }
}