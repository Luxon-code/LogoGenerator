package com.example.logogenerator.ui.content

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logogenerator.LogoGeneratorViewModel
import com.example.logogenerator.ui.component.actionButton
import com.example.logogenerator.ui.component.dataTextField
import com.example.logogenerator.ui.component.titleText
import com.example.logogenerator.ui.theme.LogoGeneratorTheme

@Composable
fun InfoColumn(context: Context,viewModel: LogoGeneratorViewModel){
    Column (verticalArrangement = Arrangement.spacedBy(16.dp)){
        titleText(text = "2. Introduce Informacion adicional")
        actionButton(if (viewModel.recording) "Detener grabacion" else "Iniciar grabacion",
            icon = Icons.Filled.Mic,
            description = "Iniciar grabacion") {
            viewModel.recordAudio(context)
        }
        if (viewModel.info.isNotEmpty()){
            actionButton(text = "Resumir", icon = Icons.Filled.Compress, description = "Resumir grabacion") {
                viewModel.createInfoSummary()
            }
            Text(text = viewModel.info)
        }
    }
}
