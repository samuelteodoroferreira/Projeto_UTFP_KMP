package utfpr.projetokmp_samuelteodoro

import androidx.compose.runtime.Composable
import utfpr.projetokmp_samuelteodoro.ui.CepScreen
import utfpr.projetokmp_samuelteodoro.ui.theme.BrazilTheme

/**
 * Aplicativo de consulta de CEP
 * @author Samuel Teodoro Ferreira
 */
@Composable
fun App() {
    BrazilTheme {
        CepScreen()
    }
}