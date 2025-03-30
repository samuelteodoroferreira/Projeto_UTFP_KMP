package utfpr.projetokmp_samuelteodoro.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import utfpr.projetokmp_samuelteodoro.ui.theme.BrazilBlue
import utfpr.projetokmp_samuelteodoro.ui.theme.BrazilGreen
import utfpr.projetokmp_samuelteodoro.ui.theme.BrazilYellow

@Composable
fun BrazilFlag(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.size(120.dp, 84.dp)
    ) {
        // Retângulo verde de fundo
        drawRect(
            color = BrazilGreen,
            size = size
        )

        // Losango amarelo
        val path = Path().apply {
            moveTo(size.width * 0.5f, 0f)
            lineTo(0f, size.height * 0.5f)
            lineTo(size.width * 0.5f, size.height)
            lineTo(size.width, size.height * 0.5f)
            close()
        }
        drawPath(
            path = path,
            color = BrazilYellow
        )

        // Círculo azul
        drawCircle(
            color = BrazilBlue,
            radius = size.height * 0.22f,
            center = Offset(size.width * 0.5f, size.height * 0.5f)
        )
    }
} 