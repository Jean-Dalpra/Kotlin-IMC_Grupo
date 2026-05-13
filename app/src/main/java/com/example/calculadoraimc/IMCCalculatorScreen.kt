package com.example.calculadoraimc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IMCCalculatorScreen() {

    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }
    var classificacao by remember { mutableStateOf("") }

    fun calcularIMC() {
        val pesoValue = peso.toDoubleOrNull()
        val alturaValue = altura.toDoubleOrNull()

        if (pesoValue != null && alturaValue != null && alturaValue > 0) {

            val imc = pesoValue / (alturaValue * alturaValue)

            resultado = String.format("%.2f", imc)

            classificacao = when {
                imc < 18.5 -> "Abaixo do peso"
                imc < 25 -> "Peso normal"
                imc < 30 -> "Sobrepeso"
                imc < 35 -> "Obesidade grau I"
                imc < 40 -> "Obesidade grau II"
                else -> "Obesidade grau III"
            }

        } else {
            resultado = "Valores inválidos"
            classificacao = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8)),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Column(
                modifier = Modifier
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Calculadora de IMC",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E88E5)
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = peso,
                    onValueChange = { peso = it },
                    label = { Text("Peso (kg)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = altura,
                    onValueChange = { altura = it },
                    label = { Text("Altura (m)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { calcularIMC() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Calcular IMC",
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (resultado.isNotEmpty()) {

                    Text(
                        text = "IMC: $resultado",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = classificacao,
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}