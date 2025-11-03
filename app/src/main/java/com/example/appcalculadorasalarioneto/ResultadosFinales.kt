package com.example.appcalculadorasalarioneto

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ResultadosFinales {

    // Función para mostrar datos
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MostrarResultados(
        salarioBruto : Double,
        salarioNeto : Double,
        retencionTotal: Double,
        complementoHijos: Double,
        netoPorPaga: Double,
        hijos : Int,
        edad : Int,
        estadoCivil : String,
        discapacidad : String,
        grupoProfesional : String,
        numeroPagas : Int
    ){
        val netoPorPaga = if (numeroPagas > 0) netoPorPaga else salarioNeto

        Scaffold(
            // Barra superior con el nombre de la aplicación
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Calculadora de salario neto",
                            fontSize = 29.sp,
                            modifier = Modifier.padding(5.dp,15.dp,5.dp,0.dp)
                        )
                    },
                    modifier = Modifier
                        .height(100.dp)
                )
            },
        ){ innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp),
                verticalArrangement = Arrangement.Center
            ){
                Text("Resumen de tus datos",
                    style = MaterialTheme.typography.headlineMedium)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(4.dp)
                ){
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        InfoRow( "Edad: $edad años")
                        InfoRow( "Estado Civil: $estadoCivil")
                        InfoRow( "Número de hijos: $hijos")
                        InfoRow( "Discapacidad: $discapacidad")
                        InfoRow( "Grupo Profesional: $grupoProfesional")
                        InfoRow("Número de pagas: $numeroPagas")
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                Text("Resultados del cálculo",
                    style = MaterialTheme.typography.headlineMedium)

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    InfoRow("Salrio Bruto Anual: %.2f €".format(salarioBruto))
                    InfoRow("Retención IRPF: %.2f €".format(retencionTotal))
                    InfoRow("Complementos por hijo: %.2f €".format(complementoHijos))
                    InfoRow("Salrio Neto Anual: %.2f €".format(salarioNeto))
                    InfoRow("Salrio Neto por Paga: %.2f €".format(netoPorPaga))
                }

            }
        }
    }

    @Composable
    fun InfoRow(text: String) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text(text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.absolutePadding(8.dp,5.dp, 0.dp, 5.dp ))
        }
    }
}


