package com.example.appcalculadorasalarioneto

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appcalculadorasalarioneto.ui.theme.AppCalculadoraSalarioNetoTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ComposableDestinationInComposeScope")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppCalculadoraSalarioNetoTheme {
                MyApp()
            }
        }
    }

    @Preview
    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "datos") {
            composable("datos") {
                FormularioDatos(navController = navController)
            }
            composable(
                route = "resultado/{edad}/{estadoCivil}/{hijos}/{discapacidad}/{grupoProfesional}" +
                        "/{numeroPagas}/{salarioBruto}/{retencionTotal}/{complementoHijos}" +
                        "/{salarioNeto}/{netoPorPaga}",
                arguments = listOf(
                    navArgument("edad") {type = NavType.IntType},
                    navArgument("estadoCivil") {type = NavType.StringType},
                    navArgument("hijos") {type = NavType.IntType},
                    navArgument("discapacidad") {type = NavType.StringType},
                    navArgument("grupoProfesional") {type = NavType.StringType},
                    navArgument("numeroPagas") {type = NavType.IntType},
                    // Los sigientes se definen como FloatType, que se usará para valores Double
                    navArgument("salarioBruto") {type = NavType.FloatType},
                    navArgument("retencionTotal") {type = NavType.FloatType},
                    navArgument("complementoHijos"){type = NavType.FloatType},
                    navArgument("salarioNeto"){type = NavType.FloatType},
                    navArgument("netoPorPaga"){type = NavType.FloatType}
                )
            ) { backStackEntry ->
                    val args = backStackEntry.arguments
                ResultadosFinales().MostrarResultados(
                    edad = args?.getInt("edad") ?: 0,
                    estadoCivil = args?.getString("estadoCivil") ?: "",
                    hijos = args?.getInt("hijos") ?: 0,
                    discapacidad = args?.getString("discapacidad") ?: "",
                    grupoProfesional = args?.getString("grupoProfesional") ?: "",
                    numeroPagas = args?.getInt("numeroPagas") ?: 0,
                    //obtengo Float (Formulario) y lo tengo que cambiar a Double (Resultados)
                    salarioBruto = args?.getFloat("salarioBruto")?.toDouble() ?: 0.0,
                    retencionTotal = args?.getFloat("retencionTotal")?.toDouble() ?: 0.0,
                    complementoHijos = args?.getFloat("complementoHijos")?.toDouble() ?: 0.0,
                    salarioNeto = args?.getFloat("salarioNeto")?.toDouble() ?: 0.0,
                    netoPorPaga = args?.getFloat("netoPorPaga")?.toDouble() ?: 0.0
                )
            }
        }
    }
}
