package com.example.logogenerator.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.logogenerator.ui.component.dataTextField
import com.example.logogenerator.ui.component.titleText


@Composable
fun DataColumn(game:String,
                      elements:String,
                      onGameChange: (String)->Unit,
                      onElementsChange: (String)-> Unit){
    Column (verticalArrangement = Arrangement.spacedBy(16.dp)){
        titleText(text = "1. Completa los datos")
        dataTextField("Videojuego de referencia" , game, onGameChange)
        dataTextField("Elemento de referencia", elements , onElementsChange )
    }
}