// Define o pacote do aplicativo
package com.example.imcpro

// Importa o componente base de atividade do Android
import android.os.Bundle

// Importa a classe principal para atividades do Compose
import androidx.activity.ComponentActivity

// Importa a função que define o conteúdo da tela
import androidx.activity.compose.setContent

// Importa componentes visuais básicos do Compose
import androidx.compose.foundation.*

// Importa ferramentas de layout e alinhamento
import androidx.compose.foundation.layout.*

// Importa formas para arredondar cantos de botões e cards
import androidx.compose.foundation.shape.RoundedCornerShape

// Importa teclado numérico para os campos de entrada
import androidx.compose.foundation.text.KeyboardOptions

// Importa componentes de design moderno (Material 3)
import androidx.compose.material3.*

// Importa gerenciamento de estado e variáveis reativas
import androidx.compose.runtime.*

// Importa modificadores de interface
import androidx.compose.ui.Alignment

// Importa ferramentas de estilização de cores e texto
import androidx.compose.ui.Modifier

// Importa a cor branca padrão
import androidx.compose.ui.graphics.Color

// Importa configuração de negrito para fontes
import androidx.compose.ui.text.font.FontWeight

// Importa tipos de entrada de teclado (números)
import androidx.compose.ui.text.input.KeyboardType

// Importa unidade de medida para espaçamento
import androidx.compose.ui.unit.dp

// Importa unidade de medida para tamanho de texto
import androidx.compose.ui.unit.sp

// Classe principal que carrega o app no Android
class MainActivity : ComponentActivity() {

    // Função que cria a interface ao iniciar o app
    override fun onCreate(savedInstanceState: Bundle?) {

        // Chama a função original do sistema
        super.onCreate(savedInstanceState)

        // Define que a tela será construída com Compose
        setContent {

            // Chama a função principal da nossa calculadora
            CalculadoraIMCPro()
        }
    }
}

// Função que contém toda a interface do aplicativo
@Composable
fun CalculadoraIMCPro() {

    // Variável que guarda o texto do peso
    var peso by remember { mutableStateOf("") }

    // Variável que guarda o texto da altura
    var altura by remember { mutableStateOf("") }

    // Variável que guarda o número do IMC calculado
    var resultadoIMC by remember { mutableStateOf("--") }

    // Variável que guarda a categoria (ex: Peso Normal)
    var categoria by remember { mutableStateOf("Aguardando dados...") }

    // Variável que define a cor do texto do resultado
    var corResultado by remember { mutableStateOf(Color.White) }

    // Superfície principal com fundo escuro total
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0F0F0F)
    ) {

        // Coluna principal com rolagem caso a tela seja pequena
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Título principal do aplicativo em destaque
            Text(
                "IMC PRO MOBILE",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 20.dp, top = 35.dp)
            )

            // Card escuro que agrupa os campos de entrada
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1A1A1A)
                ),
                border = BorderStroke(1.dp, Color(0xFF333333))
            ) {

                // Espaçamento interno do card de formulário
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Campo de texto para o Peso (KG)
                    OutlinedTextField(
                        value = peso,

                        onValueChange = {

                            // Permite apenas números, vírgula e ponto
                            if (it.matches(Regex("^\\d*([.,]?\\d*)?$"))) {
                                peso = it
                            }
                        },

                        singleLine = true,

                        label = {
                            Text(
                                "PESO (KG)",
                                color = Color.Gray
                            )
                        },

                        modifier = Modifier.fillMaxWidth(),

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF00FFC3)
                        )
                    )

                    // Campo de texto para a Altura
                    OutlinedTextField(
                        value = altura,

                        onValueChange = {

                            // Permite apenas números, vírgula e ponto
                            if (it.matches(Regex("^\\d*([.,]?\\d*)?$"))) {
                                altura = it
                            }
                        },

                        singleLine = true,

                        label = {
                            Text(
                                "ALTURA (Ex: 180 ou 1,80)",
                                color = Color.Gray
                            )
                        },

                        modifier = Modifier.fillMaxWidth(),

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFF00FFC3)
                        )
                    )

                    // Linha para organizar os botões lado a lado
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        // Botão de limpar campos
                        Button(
                            onClick = {
                                peso = ""
                                altura = ""
                                resultadoIMC = "--"
                                categoria = "Aguardando..."
                                corResultado = Color.White
                            },

                            modifier = Modifier.weight(1f),

                            shape = RoundedCornerShape(8.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF333333)
                            )
                        ) {

                            // Texto do botão limpar
                            Text("LIMPAR")
                        }

                        // Botão de calcular IMC
                        Button(

                            onClick = {

                                // Converte peso para número
                                val p = peso
                                    .replace(",", ".")
                                    .toDoubleOrNull()

                                // Converte altura para número
                                var a = altura
                                    .replace(",", ".")
                                    .toDoubleOrNull()

                                // Se altura for maior que 3,
                                // entende que foi digitada em centímetros
                                if (a != null && a > 3) {
                                    a /= 100
                                }

                                // Verifica se os valores são válidos
                                if (p != null && a != null && a > 0) {

                                    // Realiza o cálculo matemático do IMC
                                    val imc = p / (a * a)

                                    // Formata o valor com uma casa decimal
                                    resultadoIMC = "%.1f".format(imc)

                                    // Define o diagnóstico e a cor
                                    when {

                                        // Lógica para baixo peso
                                        imc < 18.5 -> {
                                            categoria = "BAIXO PESO"
                                            corResultado = Color.Cyan
                                        }

                                        // Lógica para peso ideal
                                        imc < 25.0 -> {
                                            categoria = "NORMAL"
                                            corResultado = Color.Green
                                        }

                                        // Lógica para sobrepeso
                                        imc < 30.0 -> {
                                            categoria = "SOBREPESO"
                                            corResultado = Color.Yellow
                                        }

                                        // Lógica para obesidade
                                        else -> {
                                            categoria = "OBESIDADE"
                                            corResultado = Color.Red
                                        }
                                    }

                                } else {

                                    // Define mensagem de erro
                                    categoria = "DADOS INVÁLIDOS"
                                }
                            },

                            modifier = Modifier.weight(1f),

                            shape = RoundedCornerShape(8.dp),

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00FFC3),
                                contentColor = Color.Black
                            )
                        ) {

                            // Texto do botão calcular
                            Text(
                                "CALCULAR",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Espaço vertical entre seções
            Spacer(modifier = Modifier.height(20.dp))

            // Card que exibe o resultado centralizado
            Card(
                modifier = Modifier.fillMaxWidth(),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF222222)
                ),

                border = BorderStroke(
                    1.dp,
                    Color(0xFF00FFC3)
                )
            ) {

                // Alinhamento dos textos de resultado
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Exibe o número do IMC calculado
                    Text(
                        resultadoIMC,
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FFC3)
                    )

                    // Exibe a descrição da categoria
                    Text(
                        categoria,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = corResultado
                    )
                }
            }

            // Espaço antes da tabela
            Spacer(modifier = Modifier.height(24.dp))

            // Seção de referência
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // Título da tabela
                Text(
                    "TABELA DE REFERÊNCIA",
                    color = Color.Gray,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                // Linhas da tabela
                LinhaTabela("Menor que 18.5", "BAIXO PESO", Color.Cyan)
                LinhaTabela("18.5 a 24.9", "NORMAL", Color.Green)
                LinhaTabela("25.0 a 29.9", "SOBREPESO", Color.Yellow)
                LinhaTabela("30.0 ou mais", "OBESIDADE", Color.Red)
            }
        }
    }
}

// Função auxiliar para criar as linhas da tabela
@Composable
fun LinhaTabela(
    valor: String,
    label: String,
    cor: Color
) {

    // Organiza o valor e a descrição em uma linha
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF1A1A1A),
                RoundedCornerShape(4.dp)
            )
            .padding(10.dp),

        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // Texto do valor
        Text(
            valor,
            color = Color.LightGray,
            fontSize = 14.sp
        )

        // Texto da categoria
        Text(
            label,
            color = cor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}