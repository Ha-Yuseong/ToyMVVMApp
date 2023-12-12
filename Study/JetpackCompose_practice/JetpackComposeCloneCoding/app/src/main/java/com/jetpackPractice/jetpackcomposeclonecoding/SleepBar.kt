package com.jetpackPractice.jetpackcomposeclonecoding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.lerp
import androidx.compose.ui.unit.dp
import com.jetpackPractice.jetpackcomposeclonecoding.ui.theme.LegendHeadingStyle

/*
*
* 맨 아래부터 위로 스크롤을 올리면서 보면 됩니다.
* 원본 코드들은 모두 맨 아래에 베이스가 되는 코드들이 있고 위로 올라갈수록
* 아래에 만든 코드들을 사용한 코드들이 있는 형태를 취하고 있었습니다.
*
*/

// 아래 Composable function으로 SleepBar가 어떤 형태로 나오는지 볼 수 있습니다.
@Preview
@Composable
fun SleepBarPreview() {
    SleepBar(sleepData = sleepData.sleepDayData.first())
}

// @OptIn(ExperimentalAnimationApi::class) 는 실험적인 기능(안정되지 않은 버전의 API 같은)을 수행하는데 동의한다 라는 뜻이라고 합니다.
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SleepBar(
    sleepData: SleepDayData,
    modifier: Modifier = Modifier,
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val transition = updateTransition(targetState = isExpanded, label = "expanded")

    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                isExpanded = !isExpanded
            }
    ) {
        SleepRoundedBar(
            sleepData,
            transition
        )

        // AnimatedVisibility는 실험적인 API라 사용을 위해서는 OptIn 어노테이션이 필요합니다.
        transition.AnimatedVisibility(
            enter = fadeIn(animationSpec = tween(animationDuration)) + expandVertically(
                animationSpec = tween(animationDuration)
            ),
            exit = fadeOut(animationSpec = tween(animationDuration)) + shrinkVertically(
                animationSpec = tween(animationDuration)
            ),
            content = {
                DetailLegend()
            },
            visible = { it }
        )
    }
}

@Composable
private fun SleepRoundedBar(
    sleepData: SleepDayData,
    transition: Transition<Boolean>,
) {
    // rememberTextMeasurer는 텍스트의 크기나 길이를 측정할 때 사용하여 결과를 통해 크기 및 레이아웃을 조절할 때 유용하다고 합니다.
    val textMeasurer = rememberTextMeasurer()

    val height by transition.animateDp(label = "height", transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness =
            Spring.StiffnessLow
        )
    }) { targetExpanded ->
        if (targetExpanded) 100.dp else 24.dp
    }
    val animationProgress by transition.animateFloat(label = "progress", transitionSpec = {
        spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness =
            Spring.StiffnessLow
        )
    }) { target ->
        if (target) 1f else 0f
    }

    Spacer(
        modifier = Modifier
            .drawWithCache {
                val width = this.size.width
                val cornerRadiusStartPx = 2.dp.toPx()
                val collapsedCornerRadiusPx = 10.dp.toPx()
                val animatedCornerRadius = CornerRadius(
                    lerp(cornerRadiusStartPx, collapsedCornerRadiusPx, (1 - animationProgress))
                )

                val lineThicknessPx = lineThickness.toPx()
                val roundedRectPath = Path()
                roundedRectPath.addRoundRect(
                    RoundRect(
                        rect = Rect(
                            Offset(x = 0f, y = -lineThicknessPx / 2f),
                            Size(
                                this.size.width + lineThicknessPx * 2,
                                this.size.height + lineThicknessPx
                            )
                        ),
                        cornerRadius = animatedCornerRadius
                    )
                )
                val roundedCornerStroke = Stroke(
                    lineThicknessPx,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round,
                    pathEffect = PathEffect.cornerPathEffect(
                        cornerRadiusStartPx * animationProgress
                    )
                )
                val barHeightPx = barHeight.toPx()

                val sleepGraphPath = generateSleepPath(
                    this.size,
                    sleepData, width, barHeightPx, animationProgress,
                    lineThickness.toPx() / 2f
                )
                val gradientBrush =
                    Brush.verticalGradient(
                        colorStops = sleepGradientBarColorStops.toTypedArray(),
                        startY = 0f,
                        endY = SleepType.values().size * barHeightPx
                    )
                val textResult = textMeasurer.measure(AnnotatedString(sleepData.sleepScoreEmoji))

                onDrawBehind {
                    drawSleepBar(
                        roundedRectPath,
                        sleepGraphPath,
                        gradientBrush,
                        roundedCornerStroke,
                        animationProgress,
                        textResult,
                        cornerRadiusStartPx
                    )
                }
            }
            .height(height)
            .fillMaxWidth()
    )
}

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawSleepBar(
    roundedRectPath: Path,
    sleepGraphPath: Path,
    gradientBrush: Brush,
    roundedCornerStroke: Stroke,
    animationProgress: Float,
    textResult: TextLayoutResult,
    cornerRadiusStartPx: Float,
) {
    clipPath(roundedRectPath) {
        // brush는 그려지는 도형의 내부를 채우는 역할을 수행합니다. 그라데이션, 색상 등의 패턴으로 도형 내부를 채웁니다.
        drawPath(sleepGraphPath, brush = gradientBrush)
        drawPath(
            sleepGraphPath,
            style = roundedCornerStroke,
            brush = gradientBrush
        )
    }

    translate(left = -animationProgress * (textResult.size.width + textPadding.toPx())) {
        drawText(
            textResult,
            topLeft = Offset(textPadding.toPx(), cornerRadiusStartPx)
        )
    }
}

private fun generateSleepPath(
    canvasSize: Size,
    sleepData: SleepDayData,
    width: Float,
    barHeightPx: Float,
    heightAnimation: Float,
    lineThicknessPx: Float,
): Path {
    // Path란??
    // 직선이나 곡선 및 다양한 그래픽 명령을 사용하여 그림을 그릴 수 있게 해주는 클래스라고 합니다.
    val path = Path()

    var previousPeriod: SleepPeriod? = null

    // moveTo는 그림이 시작할 위치를 이동하는 메소드
    path.moveTo(0f, 0f)

    // sleepData가 있을 때 마다 수행해준다.
    sleepData.sleepPeriods.forEach { period ->
        val percentageOfTotal = sleepData.fractionOfTotalTime(period)
        val periodWidth = percentageOfTotal * width
        val startOffsetPercentage = sleepData.minutesAfterSleepStart(period) /
                sleepData.totalTimeInBed.toMinutes().toFloat()
        val halfBarHeight = canvasSize.height / SleepType.values().size / 2f
        // 위의 변수들은 임시로 만든 수면 데이터 (FakeSleepData)에서 값을 가져와서
        // 그래프 적으로 잤던 잠을 잔 값의 크기만큼 보여주기 위해 값을 계산하는 코드인듯하다..

        val offset = if (previousPeriod == null) {
            0f
        } else {
            halfBarHeight
        }

        // lerp는 라이브러리로 가져왔는데
        // return (1 - fraction) * start + fraction * stop
        // 해당 결과를 리턴해서 어느 정도 크기인지 퍼센트를 산출하는듯
        val offsetY = lerp(
            0f,
            period.type.heightSleepType() * canvasSize.height,
            heightAnimation
        )

        // step 1 - draw a line from previous sleep period to current (원문 주석)
        // 예전 자던 주기에서 요즘 잠을 자는 주기로 라인을 그리고..
        if (previousPeriod != null) {
            path.lineTo(
                x = startOffsetPercentage * width + lineThicknessPx,
                y = offsetY + offset
            )
        }

        // step 2 - add the current sleep period as rectangle to path (원문 주석)
        // 현재 자는 주기를 path를 통해 사각형으로 추가해준다.
        path.addRect(
            rect = Rect(
                // Rect에서도 offset은 시작점 위치를 이동시킨다고 합니다!
                offset = Offset(x = startOffsetPercentage * width + lineThicknessPx, y = offsetY),
                // .copy는 androidx.compose.ui.geometry.Size 클래스의 인스턴스를 복사해서 새로운 인스턴스를 생성하는 메소드라고 합니다.
                size = canvasSize.copy(width = periodWidth, height = barHeightPx)
            )
        )
        // step 3 - move to the middle of the current sleep period
        // 현재 수면시간의 중간으로 옮기기
        path.moveTo(
            x = startOffsetPercentage * width + periodWidth + lineThicknessPx,
            y = offsetY + halfBarHeight
        )

        previousPeriod = period
    }
    return path
}

@Preview
@Composable
private fun DetailLegend() {
    Row(
        modifier = Modifier.padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SleepType.values().forEach {
            LegendItem(it)
        }
    }
}

@Composable
private fun LegendItem(sleepType: SleepType) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(sleepType.color)
        )
        Text(
            // stringResource는 string.xml에서 정의한 string값을 가져올 때 사용합니다.
            stringResource(id = sleepType.title),
            style = LegendHeadingStyle,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

// 자주 사용할 그림 기초 값들을 정의해주고 있습니다.
private val lineThickness = 2.dp
private val barHeight = 24.dp
private const val animationDuration = 500
private val textPadding = 4.dp

// sleepGradientBarColorStops 수면 상태에 따라 색상 변화를 나타냅니다.
private val sleepGradientBarColorStops: List<Pair<Float, Color>> = SleepType.values().map {
    // Pair<Float, Color>를 가지는 리스트에 들어갈 값을 SleepType.value()로 넣어준다. Enum Class에 .values()는 모든 요소를 배열로 리턴함
    // .map은 그 배열의 요소 하나하나에 람다 함수를 적용할 수 있게 해준다. 그래서 pair의 float값과 색상 값을 넣어주고 있습니다.
    Pair(
        when (it) {
            SleepType.Awake -> 0f
            SleepType.REM -> 0.33f
            SleepType.Light -> 0.66f
            SleepType.Deep -> 1f
        },
        it.color
    )
}

// SleepType에 새로운 함수를 추가해주고 있습니다. 이 메소드는 같은 모듈내에서는 누구나 사용 가능합니다.
private fun SleepType.heightSleepType(): Float {
    return when (this) {
        SleepType.Awake -> 0f
        SleepType.REM -> 0.25f
        SleepType.Light -> 0.5f
        SleepType.Deep -> 0.75f
    }
}