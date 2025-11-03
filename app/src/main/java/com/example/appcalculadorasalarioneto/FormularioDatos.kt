package com.example.appcalculadorasalarioneto

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun FormularioDatos(navController: NavHostController) {
    RecopilarDatos(navController)
}
// Función para recopilar datos
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecopilarDatos(navController: NavHostController) {
    /* Variables para saber la edad, el estado civil, el número de hijos,
    el grado de discapacidad, el salario bruto y el número de pagas.
    Deben de ser String para el TextField*/
    var edad by remember { mutableStateOf("") }
    var estadoCivil by remember { mutableStateOf("Soltero/a") }
    var hijos by remember { mutableStateOf("") }
    var discapacidad by remember {mutableStateOf("Ninguna")}
    var grupoProfesional by remember { mutableStateOf("Elige grupo")}
    var salarioBruto by remember { mutableStateOf("")}
    var numeroPagas by remember { mutableStateOf("") }

    // Lista de los diferentes estados
    val listaEstadoCivil = listOf(
        "Soltero/a",
        "Casado/a",
        "Viudo/a",
        "Divorciado/a",
        "Separado/a (legalmente)",
        "Unión de hecho/civil",
        "Otros estados")
    //Lista de grados de discapacidad
    val listaDiscapacidad = listOf(
        "Ninguna",
        "Grado 1 (Leve): 5% - 24%",
        "Grado 2 (Moderada): 25% - 49% ",
        "Grado 3 (Grave): 50% - 95%",
        "Grado 4 (Muy grave): 96% - 100%")
    // Lista de grupos profesionales
    val listaGrupoProfesional = listOf(
        "Grupo 1: Ingenieros, licenciados y personal de alta dirección",
        "Grupo 2: Ingenieros técnicos, peritos y ayudantes titulados",
        "Grupo 3: Jefes administrativos y de taller",
        "Grupo 4: Ayudantes sin titulación",
        "Grupo 5: Oficiales administrativos",
        "Grupo 6: Subalternos",
        "Grupo 7: Auxiliares administrativos",
        "Grupo 8: Oficiales de primera y segunda",
        "Grupo 9: Oficiales de tercera y especialistas",
        "Grupo 10: Peones",
        "Grupo 11: Trabajadores menores de 18 años")

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

                    )
                },
                modifier = Modifier.height(100.dp)

            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 35.dp),
            verticalArrangement = Arrangement.Center

        ) {
            Text("Introduce tus datos",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 25.sp)
            Spacer(Modifier.height(20.dp))
            //Campos de entrada
            //Edad
            TextField( value = edad,
                onValueChange = {edad = it},
                label = {Text("Edad")},
                modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(18.dp))

            //Estado Civil
            Desplegable(
                "Estado Civil",
                estadoCivil,
                listaEstadoCivil)
            {estadoCivil = it}
            Spacer(Modifier.height(18.dp))

            //número de hijos
            TextField(
                value = hijos,
                onValueChange = {hijos = it},
                label = {Text("Número de hijos")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(18.dp))

            //Discapacidad
            Desplegable(
                "Grado de Discapacidad",
                discapacidad,
                listaDiscapacidad)
            {discapacidad = it}
            Spacer(Modifier.height(18.dp))

            //Grupo profesional
            Desplegable(
                "Grupo profesional",
                grupoProfesional,
                listaGrupoProfesional)
            {grupoProfesional = it}
            Spacer(Modifier.height(18.dp))

            //Salario Bruto
            TextField(
                value = salarioBruto,
                onValueChange = {salarioBruto = it},
                label = {Text("Salario Bruto Anual (€)")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(18.dp))

            //Numero de pagas
            TextField(
                value = numeroPagas,
                onValueChange = {numeroPagas = it},
                label = {Text("Número de pagas")},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(25.dp))

            BotonCalcular(onClick = {
                // Convertir los valores String al tipo necesario para calcular
                val edadInt = edad.toIntOrNull() ?: 0
                val hijosInt = hijos.toIntOrNull() ?: 0
                val numeroPagasInt = numeroPagas.toIntOrNull() ?: 14 // Por defecto
                val salarioBrutoDouble = salarioBruto.toDoubleOrNull() ?: 0.0

                // Para ejecutar el cálculo
                val calculadora = Calculos()
                val resultado = calculadora.calcularSalarioNeto(
                    edad = edadInt,
                    hijos = hijosInt,
                    numeroPagas = numeroPagasInt,
                    salarioBruto = salarioBrutoDouble,
                    discapacidad = discapacidad,
                    grupoProfesional = grupoProfesional,
                    estadoCivil = estadoCivil
                )

                // Construir ruta de navegación con los datos de entrada y los resultados
                // Hay que pasar a Float los datos Double para poder navegar

                val ruta = "resultado/$edadInt/$estadoCivil/$hijosInt/$discapacidad/$grupoProfesional" +
                        "/$numeroPagasInt/${salarioBrutoDouble.toFloat()}/${resultado.retencionTotal.toFloat()}" +
                        "/${resultado.complementoHijos.toFloat()}/${resultado.salarioNeto.toFloat()}" +
                        "/${resultado.netoPorPaga.toFloat()}"

                navController.navigate(ruta)
            })
        }
    }
}

//Comoposable reutilizable para menús desplegables
@Composable
fun Desplegable(
    label: String,
    selected: String,
    opciones: List<String>,
    onSelected:(String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Text(label)
        OutlinedButton(onClick = {expanded = true},
            modifier = Modifier.fillMaxWidth()) {
            Text(selected)
        }
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false}) {
        opciones.forEach { opcion ->
            DropdownMenuItem(
                text = { Text(opcion)},
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSelected(opcion)
                    expanded = false}
            )
        }
    }
}

@Composable
fun BotonCalcular(onClick: () -> Unit) {
    Button(onClick = { onClick() },
        modifier = Modifier.fillMaxWidth()) {
        Text("Calcular")
    }
}




