package utfpr.projetokmp_samuelteodoro.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import utfpr.projetokmp_samuelteodoro.shared.viewmodel.CepViewModel
import utfpr.projetokmp_samuelteodoro.ui.components.BrazilFlag
import utfpr.projetokmp_samuelteodoro.ui.theme.*

/**
 * Tela principal de busca de CEP
 * @author Samuel Teodoro Ferreira
 */
@Composable
fun CepScreen(
    modifier: Modifier = Modifier,
    viewModel: CepViewModel = remember { CepViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.background(BackgroundLight)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Bandeira do Brasil
            BrazilFlag(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Text(
                text = "Consulta CEP",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = BrazilGreen
            )

            OutlinedTextField(
                value = uiState.cep,
                onValueChange = viewModel::onCepChanged,
                label = { Text("Digite o CEP") },
                enabled = uiState.isInputEnabled,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrazilGreen,
                    unfocusedBorderColor = BrazilGreenLight,
                    focusedLabelColor = BrazilGreen,
                    unfocusedLabelColor = BrazilGreenLight
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ElevatedButton(
                    onClick = { scope.launch { viewModel.buscarCep() } },
                    enabled = uiState.isSearchEnabled && !uiState.isLoading,
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = BrazilGreen,
                        contentColor = BackgroundLight,
                        disabledContainerColor = BrazilGreenLight.copy(alpha = 0.6f)
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 8.dp,
                        disabledElevation = 0.dp
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color = BackgroundLight,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text("Buscar")
                    }
                }

                ElevatedButton(
                    onClick = { viewModel.limparBusca() },
                    enabled = uiState.cepInfo != null,
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = BrazilYellow,
                        contentColor = BrazilBlue,
                        disabledContainerColor = BrazilYellowLight.copy(alpha = 0.6f)
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 8.dp,
                        disabledElevation = 0.dp
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Limpar")
                }
            }

            AnimatedVisibility(
                visible = uiState.cepInfo != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                uiState.cepInfo?.let { info ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = BrazilYellowLight.copy(alpha = 0.1f)
                        ),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = SolidColor(BrazilYellow)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            InfoRow("CEP", info.cep)
                            InfoRow("Logradouro", info.logradouro)
                            InfoRow("Bairro", info.bairro)
                            InfoRow("Cidade", info.localidade)
                            InfoRow("UF", info.uf)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = BrazilGreenDark
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary
        )
    }
}

@Preview(showBackground = true, name = "Tela de Consulta CEP")
@Composable
fun CepScreenPreview() {
    BrazilTheme {
        CepScreen()
    }
} 