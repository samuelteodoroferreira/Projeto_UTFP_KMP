package utfpr.projetokmp_samuelteodoro.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val BrazilColorScheme = lightColorScheme(
    primary = BrazilGreen,
    onPrimary = BackgroundLight,
    secondary = BrazilYellow,
    onSecondary = TextPrimary,
    tertiary = BrazilBlue,
    background = BackgroundLight,
    surface = BackgroundLight,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun BrazilTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BrazilColorScheme,
        content = content
    )
} 