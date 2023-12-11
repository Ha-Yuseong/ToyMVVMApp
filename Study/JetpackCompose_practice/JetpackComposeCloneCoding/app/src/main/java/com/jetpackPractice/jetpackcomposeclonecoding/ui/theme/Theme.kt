package com.jetpackPractice.jetpackcomposeclonecoding.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


// 아래의 private val들은 각각 Dark, white 테마 설정일 때 구성되는 기본 색상들을 설정할 수 있다.

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Yellow,
    secondary = MintGreen,
    tertiary = Coral,
    surface = White

    /* Other default colors to override 내가 오버라이드 가능한 다른 선택지들을 밑에서 보여주는 중
    background = Color(0xFFFFFBFE) 말그대로 배경색을 정함,
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// 아래는 테마 선택인데 아주 낮은 버전이 되면 다크 테마나 화이트 테마로 나뉘는 일이 없을 수 있다.

@Composable
fun JetpackComposeCloneCodingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current

    // 아래는 현재 편집기에서 편집이 되는지 확인해서 맞다면 sideEffect 아래 문구를 수행하는것
    // SideEffect 아래의 코드는 아마도 편집기에서 Dark모드를 켰는지 확인해서 보여주는 코드인 것 같습니다...
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}