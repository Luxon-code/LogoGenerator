package com.example.logogenerator

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.aallam.openai.client.OpenAI
import com.example.logogenerator.audio.AudioRecorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.audio.TranscriptionRequest
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.file.FileSource
import com.aallam.openai.api.image.ImageCreation
import com.aallam.openai.api.image.ImageSize
import com.aallam.openai.api.image.ImageURL
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.example.logogenerator.conf.Conf
import com.example.logogenerator.conf.Env
import kotlinx.coroutines.launch
import okio.source
import java.io.File

class LogoGeneratorViewModel : ViewModel() {
    private var openAi = OpenAI(token = Env.OPENAI_API_KEY, logging = LoggingConfig(LogLevel.All))
    var info: String by mutableStateOf("")
    var loading:Boolean by mutableStateOf(false)
    private var recorder: AudioRecorder? =  null
    private var audioFile:File? = null
    var recording:Boolean by mutableStateOf(false)
    //audio
    fun recordAudio(context:Context){
        if (recording){
            recording = false
            recorder?.stop()
            loadInfo(audioFile)
        } else{
            if (recorder == null){
                recorder = AudioRecorder(context)
            }
            File(context.cacheDir,Conf.AUDIO_FILE).also {
                recorder?.record(it)
                audioFile = it
                recording = true
            }
        }
    }

    //transcripcion
    @OptIn(BetaOpenAI::class)
    private fun loadInfo(file: File?) = viewModelScope.launch{
        file?.source()?.let {
            starLoading()
            val transcriptionRequest = TranscriptionRequest(
                audio = FileSource(name = Conf.AUDIO_FILE, source = it),
                model = ModelId(Conf.WHISPER_MODEL)
            )
            val transcription = openAi.transcription(transcriptionRequest)

            info = transcription.text

            endLoading()
        }
    }
    //loading
    private fun starLoading(){
        loading = true
    }
    private fun endLoading(){
        loading  = false
    }
    //resumen
    @OptIn(BetaOpenAI::class)
    fun createInfoSummary() = viewModelScope.launch{
        starLoading()
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(Conf.GTP_MODEL),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = "Eres un asitente especializado en resumir texto de manera extremadamente " +
                            "consisa y eficaz"
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = "Lista las palabras claves del siguiente texto: $info"
                )
            )
        )
        info = openAi.chatCompletion(chatCompletionRequest).choices.first().message?.content.toString()
        endLoading()
    }

    //generacion
    @OptIn(BetaOpenAI::class)
    fun generateLogo(game:String,elements:String,imageURL: (String) -> Unit) = viewModelScope.launch {
        starLoading()
        var prompt = "eSports logo, vector logo, ${game.trim()}, ${elements.trim()},"
        if (info.isNotEmpty()){
            prompt += " ${info.trim()}"
        }
        val images = openAi.imageURL(creation = ImageCreation(prompt,1, ImageSize.is1024x1024))
        imageURL(images.first().url)
        endLoading()
    }
}