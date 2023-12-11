package com.jetpackPractice.jetpackcomposeclonecoding

import android.graphics.Color
import android.graphics.RuntimeShader
import android.os.Build
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ShaderBrush
import com.jetpackPractice.jetpackcomposeclonecoding.ui.theme.White
import com.jetpackPractice.jetpackcomposeclonecoding.ui.theme.Yellow
import com.jetpackPractice.jetpackcomposeclonecoding.ui.theme.YellowVariant
import org.intellij.lang.annotations.Language

fun Modifier.yellowBackground() : Modifier = this.composed {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        val time by produceState(0f){
            while (true){
                withInfiniteAnimationFrameMillis {
                    value = it/1000f
                }
            }
        }
        Modifier.drawWithCache {
            val shader = RuntimeShader(SHADER)
            val shaderBrush = ShaderBrush(shader)
            shader.setFloatUniform("iResolution", size.width, size.height)

            shader.setColorUniform(
                "iColor", Color.valueOf(Yellow.red, Yellow.green, Yellow.blue, Yellow.alpha)
            )
            onDrawBehind {
                shader.setFloatUniform("iTime", time)
                drawRect(shaderBrush)
            }
        }
    }else{
        Modifier.drawWithCache {
            onDrawBehind {
                val gradientBrush = Brush.verticalGradient(listOf(Yellow, YellowVariant, White))
                drawRect(gradientBrush)
            }
        }
    }
}

// 복붙해온 부분! 해당 부분은 Android Graphics Shading Language(AGSL)로 그래픽과 관련된 언어입니다.
// 현재의 예상 진도를 벗어난 부분이기 때문에 넘어가겠습니다.
@Language("AGSL")
val SHADER = """
    uniform float2 iResolution;
    uniform float iTime;
    layout(color) uniform half4 iColor;
    
    float calculateColorMultiplier(float yCoord, float factor) {
        return step(yCoord, 1.0 + factor * 2.0) - step(yCoord, factor - 0.1);
    }

    float4 main(in float2 fragCoord) {
        // Config values
        const float speedMultiplier = 1.5;
        const float waveDensity = 1.0;
        const float loops = 8.0;
        const float energy = 0.6;
        
        // Calculated values
        float2 uv = fragCoord / iResolution.xy;
        float3 color = iColor.rgb;
        float timeOffset = iTime * speedMultiplier;
        float hAdjustment = uv.x * 4.3;
        float3 loopColor = vec3(1.0 - color.r, 1.0 - color.g, 1.0 - color.b) / loops;
        
        for (float i = 1.0; i <= loops; i += 1.0) {
            float loopFactor = i * 0.1;
            float sinInput = (timeOffset + hAdjustment) * energy;
            float curve = sin(sinInput) * (1.0 - loopFactor) * 0.05;
            float colorMultiplier = calculateColorMultiplier(uv.y, loopFactor);
            color += loopColor * colorMultiplier;
            
            // Offset for next loop
            uv.y += curve;
        }
        
        return float4(color, 1.0);
    }
    
    
    
""".trimIndent()