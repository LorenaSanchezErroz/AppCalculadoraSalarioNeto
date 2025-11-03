package com.example.appcalculadorasalarioneto

/* data class para devolver un objeto con los resultados de los cálculos realizados
(genera automáticamente los métodos de la clase)
 */
data class ResultadoSalario(
    val salarioNeto: Double,
    val retencionTotal: Double,
    val complementoHijos: Double,
    val netoPorPaga: Double
)
/* clase que tendrá una función realizará los cálculos para saber el salario neto, las retenciones,
el complemento que se paga por hijos, el neto que se recibe por paga
 */
class Calculos {
    // función que con los argumentos pasados devolverá un objeto de tipo ResultadoSalario
    fun calcularSalarioNeto(
        salarioBruto : Double ,
        hijos : Int,
        edad : Int,
        estadoCivil : String,
        discapacidad : String,
        grupoProfesional : String,
        numeroPagas : Int) : ResultadoSalario {

        // Retención base ficticia según grupo profesional
        val retencionBase = when (grupoProfesional) {
            "Grupo 1: Ingenieros, licenciados y personal de alta dirección" -> 0.22
            "Grupo 2: Ingenieros técnicos, Peritos y Ayudantes titulados" -> 0.19
            "Grupo 3: Jefes Administrativos y de Taller" -> 0.17
            "Grupo 4: Ayudantes sin titulación" -> 0.15
            "Grupo 5: Oficiales administrativos" -> 0.14
            "Grupo 6: Subalternos" -> 0.13
            "Grupo 7: Auxiliares administrativos" -> 0.12
            "Grupo 8: Oficiales de primera y segunda" -> 0.11
            "Grupo 9: Oficiales de tercera y especialistas" -> 0.10
            "Grupo 10: Peones" -> 0.09
            else -> 0.05
        }

        // Ajuste por edad
        val ajusteEdad = when {
            edad > 65 -> -0.10
            edad < 18 -> -0.05
            else -> 0.0
        }

        // Ajuste por Estados
        val ajusteEstados = if ( estadoCivil == "viudo/a" || estadoCivil == "Otros estados") {-0.03}
        else {0.0}


        // Ajuste por discapacidad
        val ajusteDiscapacidad = when (discapacidad) {
            "Grado 1 (Leve): 5% - 24%" -> -0.05
            "Grado 2 (Moderada): 25% - 49% " -> -0.10
            "Grado 3 (Grave): 50% - 95%" -> -0.15
            "Grado 4 (Muy grave): 96% - 100%" -> -0.20
            else -> 0.0
        }

        //´Retención final que se restará al bruto
        // Asegurar que el porcentaje de retención no sea negativo
        val porcentajeRet = (retencionBase + ajusteEdad + ajusteEstados + ajusteDiscapacidad)
            .coerceAtLeast(0.0)


        val retencion = salarioBruto * porcentajeRet

        // Complemento ficticio por hijos que se sumará al bruto
        val complemento = hijos * 100.0

        //Salario neto
        val neto = salarioBruto + complemento - retencion

        // neto por paga
        val divisorPagas = if(numeroPagas > 0) numeroPagas else 1
        val netoPaga = neto/divisorPagas

        // Resultados finales
        return ResultadoSalario(
            salarioNeto = neto,
            retencionTotal = retencion,
            complementoHijos = complemento,
            netoPorPaga = netoPaga
        )
    }
}