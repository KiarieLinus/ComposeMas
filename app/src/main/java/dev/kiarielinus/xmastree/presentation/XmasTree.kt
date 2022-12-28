package dev.kiarielinus.xmastree.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import dev.kiarielinus.xmastree.ui.theme.Gold
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun XmasTree() {
    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = 100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val infiniteTransition = rememberInfiniteTransition()
        val angleRotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2400, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = Gold,
            modifier = Modifier
                .size(48.dp)
                .graphicsLayer {
                    rotationY = -angleRotation
                }
                .padding(bottom = 8.dp)
        )
        for (i in 2 .. 10) {
            TreeLayer((i*0.5f), angleRotation)
        }
    }
}
@Composable
fun TreeLayer(multiplier: Float, angleRotation: Float) {
    val circleColors: List<Color> = listOf(
        Color.Green, Color.Red, Color.Yellow, Color.Blue
    )
    BoxWithConstraints(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .graphicsLayer {
                rotationX = 60f
                cameraDistance = 10f * density
            }
    ) {
        Canvas(
            Modifier
                .align(Alignment.Center)
        ) {
            rotate(angleRotation) {
                val r = 60f * multiplier

                fun Float.getOffset() : Offset{
                    val angle = this * (PI/180)
                    val x = r * cos(angle).toFloat()
                    val y = r * sin(angle).toFloat()
                    return Offset(x,y)
                }

                drawCircle(
                    brush = Brush.sweepGradient(circleColors),
                    radius = r,
                    style = Stroke(4f * multiplier)
                )
                //North
                drawCircle(
                    color = Color.Green,
                    center = (90f - 30*multiplier).getOffset(),
                    radius = 6f * multiplier
                )
                //South
                drawCircle(
                    color = Color.Yellow,
                    center =(270f - 30*multiplier).getOffset(),
                    radius = 6f * multiplier
                )
                //West
                drawCircle(
                    color = Color.Blue,
                    center = (180f - 30*multiplier).getOffset(),
                    radius = 6f * multiplier
                )
                //East
                drawCircle(
                    color = Color.Red,
                    center = (0f - 30*multiplier).getOffset(),
                    radius = 6f * multiplier
                )
            }
        }
    }
}

