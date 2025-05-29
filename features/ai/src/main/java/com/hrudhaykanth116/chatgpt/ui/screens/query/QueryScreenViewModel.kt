package com.hrudhaykanth116.chatgpt.ui.screens.query

import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.vertexai.GenerativeModel
import com.google.firebase.vertexai.vertexAI
import com.hrudhaykanth116.chatgpt.ui.screens.models.QueryScreenEffect
import com.hrudhaykanth116.chatgpt.ui.screens.models.QueryScreenEvent
import com.hrudhaykanth116.chatgpt.ui.screens.models.QueryScreenUIState
import com.hrudhaykanth116.core.udf.UDFViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueryScreenViewModel @Inject constructor(

): UDFViewModel<QueryScreenUIState, QueryScreenEvent, QueryScreenEffect>(QueryScreenUIState()) {

    private val generativeModel: GenerativeModel = Firebase.vertexAI.generativeModel("gemini-2.0-flash")

    // val imageModel = Firebase.vertexAI.imagenModel(
    //     modelName = "imagen-3.0-generate-001",
    //     generationConfig = ImagenGenerationConfig(
    //         imageFormat = ImagenImageFormat.jpeg(compresssionQuality = 75),
    //         addWatermark = true,
    //         numberOfImages = 1,
    //         aspectRatio = ImagenAspectRatio.SQUARE_1x1
    //     ),
    //     safetySettings = ImagenSafetySettings(
    //         safetyFilterLevel = ImagenSafetyFilterLevel.BLOCK_LOW_AND_ABOVE
    //                 personFilterLevel = ImagenPersonFilterLevel.ALLOW_ADULT
    //     )
    // )

    override fun processEvent(event: QueryScreenEvent) {

    }

    fun getContent(prompt: String){

        viewModelScope.launch {
            // val response = generativeModel.generateContent("")

            var response = ""
            generativeModel.generateContentStream(prompt).collect { chunk ->
                print(chunk.text)
                response += chunk.text
            }

        }


    }


}