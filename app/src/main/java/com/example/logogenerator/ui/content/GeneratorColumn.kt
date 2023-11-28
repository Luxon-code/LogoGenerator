package com.example.logogenerator.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.logogenerator.ui.component.actionButton
import com.example.logogenerator.ui.component.titleText

@Composable
fun GeneratorColumn(){
    Column (verticalArrangement = Arrangement.spacedBy(16.dp)){
        titleText(text = "3. Genera el logo")
        actionButton(text = "Generar", icon = Icons.Filled.Draw, description = "Genera el logotipo") {
            //Click
        }
    }
}