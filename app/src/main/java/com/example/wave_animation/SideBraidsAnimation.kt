import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

@Composable
fun SideBraidsAnimation() {
    val infiniteTransition = rememberInfiniteTransition()
    val leftWaveOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val rightWaveOffset = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Wave(
            modifier = Modifier
                .fillMaxHeight()
                .width(20.dp)
                .align(Alignment.CenterStart),
            animatedOffset = leftWaveOffset.value,
            waveCount = 3,
            isLeftSide = true
        )
        Wave(
            modifier = Modifier
                .fillMaxHeight()
                .width(20.dp)
                .align(Alignment.CenterEnd),
            animatedOffset = rightWaveOffset.value,
            waveCount = 3,
            isLeftSide = false
        )
    }
}

@Composable
fun Wave(modifier: Modifier, animatedOffset: Float, waveCount: Int, isLeftSide: Boolean) {
    Canvas(modifier = modifier) {
        val waveHeight = size.height / 6
        val path = Path()
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Magenta, Color.Cyan, Color.Magenta)
        )

        for (j in 0 until waveCount) {
            path.reset()
            val offset = j * 20f // adjust this to control the spread of the waves
            path.moveTo(0f, -waveHeight + waveHeight * animatedOffset * 6)

            // Straight part
            path.lineTo(0f, waveHeight * (animatedOffset + 0.5f))

            for (i in 0 until 6) {
                path.quadraticBezierTo(
                    offset + size.width * if (i % 2 == 0) 0.5f else -0.5f,
                    waveHeight * (i + 0.5f) + waveHeight * animatedOffset * 6,
                    0f,
                    waveHeight * (i + 1f) + waveHeight * animatedOffset * 6
                )
            }

            // Ensure it goes straight to the end
            path.lineTo(0f, size.height + waveHeight * animatedOffset * 6)

            drawPath(
                path = path,
                brush = gradient,
                style = Stroke(width = 5f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSideBraidsAnimation() {
    SideBraidsAnimation()
}
