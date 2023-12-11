package com.example.logogenerator.ui.content

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.logogenerator.LogoGeneratorViewModel
import com.example.logogenerator.ui.component.actionButton
import com.example.logogenerator.ui.component.titleText

@Composable
fun GeneratorColumn(viewModel: LogoGeneratorViewModel,game:String,elements:String,context: Context){
    var imageURL by remember { mutableStateOf("") }
    val clipBoard = LocalClipboardManager.current
    Column (verticalArrangement = Arrangement.spacedBy(16.dp)){
        titleText(text = "3. Genera el logo")
        actionButton(text = "Generar", icon = Icons.Filled.Draw, description = "Genera el logotipo",
            enabled = game.isNotEmpty() && elements.isNotEmpty()) {
            viewModel.generateLogo(game,elements){
                imageURL  = it
            }
        }
        if (imageURL.isNotEmpty()){
            AsyncImage(model = imageURL, contentDescription = "",
                modifier = Modifier.fillMaxSize())
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(onClick = {
                    clipBoard.setText(AnnotatedString(imageURL))
                    Toast.makeText(context,"URL copiada",Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Filled.ContentCopy, contentDescription = "Copiar Url",
                        modifier = Modifier.size(ButtonDefaults.IconSize))
                }
                Text(text = "Copiar URL de la Imagen")
            }
        }
    }
}