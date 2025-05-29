package com.hrudhaykanth116.core.common.compose

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
fun rememberTextToSpeech(): MutableState<TextToSpeech?> {
    val context = LocalContext.current
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }

    DisposableEffect(context) {
        val textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = Locale.US
            }
        }
        tts.value = textToSpeech

        onDispose {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
    return tts
}

// var isSpeaking by remember { mutableStateOf(false) }
// val tts = rememberTextToSpeech()
//
// Column(modifier = Modifier.padding(24.dp)) {
//     isSpeaking = false
//     for (sentence in sentences) {
//         Button(onClick = {
//             if (tts.value?.isSpeaking == true) {
//                 tts.value?.stop()
//                 isSpeaking = false
//             } else {
//                 tts.value?.speak(
//                     sentence, TextToSpeech.QUEUE_FLUSH, null, ""
//                 )
//                 isSpeaking = true
//             }
//         }) {
//             Text(sentence)
//         } // End Button
//     } // End for
//
// }